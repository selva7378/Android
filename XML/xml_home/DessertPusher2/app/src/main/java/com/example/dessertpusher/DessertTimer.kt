package com.example.dessertpusher

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

/**
 * This is a class representing a timer that you can start or stop. The secondsCount outputs a count of
 * how many seconds since it started, every one second.
 *
 * -----
 *
 * Handler and Runnable are beyond the scope of this lesson. This is in part because they deal with
 * threading, which is a complex topic that will be covered in a later lesson.
 *
 * If you want to learn more now, you can take a look on the Android Developer documentation on
 * threading:
 *
 * https://developer.android.com/guide/components/processes-and-threads
 *
 */


/**
 * This is a class representing a timer that you can start or stop. The secondsCount outputs a
 * count of how many seconds have passed since it started, every one second.
 *
 * It implements DefaultLifecycleObserver to automatically start and stop the timer in
 * response to the lifecycle of the owner (like an Activity or Fragment).
 */
class DessertTimer(lifecycle: Lifecycle) : DefaultLifecycleObserver {

    // The number of seconds counted since the timer started
    var secondsCount = 0

    /**
     * [Handler] is a class meant to process a queue of messages (known as [android.os.Message]s)
     * or actions (known as [Runnable]s). Specifying Looper.getMainLooper() ensures that the
     * handler runs on the main UI thread.
     */
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    init {
        // Add this observer to the lifecycle of the owner
        lifecycle.addObserver(this)
    }

    /**
     * This method is called when the LifecycleOwner (e.g., Activity) enters the ON_START state.
     * We start our timer here.
     */
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        // Create the runnable action, which logs the seconds and increments the counter
        runnable = Runnable {
            secondsCount++
            Timber.i("Timer is at: $secondsCount")
            // postDelayed re-adds the action to the handler's queue to run again in 1 second.
            handler.postDelayed(runnable, 1000)
        }

        // Initially start the timer
        handler.postDelayed(runnable, 1000)
    }

    /**
     * This method is called when the LifecycleOwner (e.g., Activity) enters the ON_STOP state.
     * We stop our timer here to prevent it from running in the background.
     */
    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        // Removes all pending posts of the runnable from the handler's queue,
        // effectively stopping the timer.
        handler.removeCallbacks(runnable)
    }
}