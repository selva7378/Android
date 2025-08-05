// In your DevByteApplication.kt file

package com.example.devbytes // Adjust your package name as needed

import android.app.Application
import android.os.Build
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import com.example.devbytes.work.RefreshDataWork
import dagger.hilt.android.HiltAndroidApp

import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber // Assuming you are using Timber for logging
import kotlin.jvm.java

@HiltAndroidApp // Crucial for Hilt setup
class DevByteApplication : Application(), Configuration.Provider { // Implement Configuration.Provider

    // This is the idiomatic way for Hilt-integrated WorkManager.
    // Hilt's 'hilt-work' dependency automatically provides the HiltWorkerFactory
    // that WorkManager will use to instantiate your @HiltWorker classes.
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG) // Recommended for debugging
            // You don't explicitly set .setWorkerFactory() here because Hilt handles it
            .build()


    private val applicationScope = CoroutineScope(Dispatchers.Default)

    /**
     * onCreate is called before the first screen is shown to the user.
     *
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree()) // Initialize Timber for logging
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()
        val repeatingRequest = PeriodicWorkRequest.Builder(
            RefreshDataWork::class.java, // Reference your HiltWorker
            1, // Repeat interval
            TimeUnit.DAYS // Time unit for repeat interval
        )
            // Add constraints if needed, e.g., require network
             .setConstraints(constraints)
            .build()

        // Enqueue the unique periodic work request
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWork.WORK_NAME, // A unique name for this work (defined in your Worker's companion object)
            ExistingPeriodicWorkPolicy.KEEP, // Policy for existing work: keep if it's already enqueued
            repeatingRequest
        )
    }
}