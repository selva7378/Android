package com.example.intentapp.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.intentapp.R

class TimerService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(){
        val notification =  NotificationCompat.Builder(this, "running channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("timer is active")
            .setContentText("Elapsed time: 00:00")
            .build()
        startForeground(1, notification)
    }

    enum class Actions{
        START, STOP
    }
}