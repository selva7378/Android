package com.example.intentapp.services

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.intentapp.R

class TimerService : Service() {
    private val notificationId = 1
    private val updateIntervalMs: Long = 1000 // 1 second

    private var startTime: Long = 0
    private val handler = Handler()
    private val updateRunnable = object : Runnable {
        override fun run() {
            updateNotification()
            handler.postDelayed(this, updateIntervalMs)
        }
    }

    override fun onCreate() {
        super.onCreate()
        startTime = System.currentTimeMillis()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        handler.post(updateRunnable)
        return START_STICKY
    }

    private fun startForegroundService() {
        val notification = createNotification("00:00:00")
        startForeground(notificationId, notification)
    }

    private fun createNotification(time: String): Notification {
        return NotificationCompat.Builder(this, "YOUR_CHANNEL_ID")
            .setContentTitle("Stopwatch Running")
            .setContentText(time)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun updateNotification() {
        val elapsedMillis = System.currentTimeMillis() - startTime
        val elapsedTime = formatElapsedTime(elapsedMillis)

        val notification = createNotification(elapsedTime)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

    private fun formatElapsedTime(elapsedMillis: Long): String {
        val seconds = (elapsedMillis / 1000) % 60
        val minutes = (elapsedMillis / (1000 * 60)) % 60
        val hours = (elapsedMillis / (1000 * 60 * 60)) % 24
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateRunnable)
    }
}

//override fun onBind(intent: Intent?): IBinder? {
//    return null
//}
//
//override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//    when(intent?.action){
//        Actions.START.toString() -> start()
//        Actions.STOP.toString() -> stopSelf()
//    }
//    return super.onStartCommand(intent, flags, startId)
//}
//
//private fun start(){
//    val notification =  NotificationCompat.Builder(this, "running channel")
//        .setSmallIcon(R.drawable.ic_launcher_foreground)
//        .setContentTitle("timer is active")
//        .setContentText("Elapsed time: 00:00")
//        .build()
//    startForeground(1, notification)
//}
//
//enum class Actions{
//    START, STOP
//}