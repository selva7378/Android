package com.example.intentapp.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast

class AirPlaneModeReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED){
            val isAirplaneModeOn = Settings.Global.getInt(
                context?.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON
            ) != 0
            if (isAirplaneModeOn) {
                Toast.makeText(context, "Airplane mode is turned on", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Airplane mode is turned of", Toast.LENGTH_SHORT).show()
            }

        }
    }
}