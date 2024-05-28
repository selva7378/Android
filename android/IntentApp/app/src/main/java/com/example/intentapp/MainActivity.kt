package com.example.intentapp

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.intentapp.broadcastreceiver.AirPlaneModeReceiver
import com.example.intentapp.broadcastreceiver.AppToAppReceiver
import com.example.intentapp.ui.theme.IntentAppTheme

class MainActivity : ComponentActivity() {

    private val airPlaneModeReceiver = AirPlaneModeReceiver()
    private val appToAppReceiver = AppToAppReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
        registerReceiver(
            airPlaneModeReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )
        registerReceiver(
            appToAppReceiver,
            IntentFilter("TEST_ACTION")
        )
        enableEdgeToEdge()
        setContent {
            IntentAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IntentToActivity( modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airPlaneModeReceiver)
        unregisterReceiver(appToAppReceiver)
    }
}



@Composable
fun IntentToActivity(modifier: Modifier = Modifier){
    val context = LocalContext.current
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            ElevatedButton(onClick = {
                Intent(context, SecondActivity::class.java).also {
                    try{
                        context.startActivity(it)
                    }catch (e: ActivityNotFoundException){
                        e.printStackTrace()
                    }
                }
            }) {
                Text(text = "Go to Second Activity(INTENT)")
            }
            ElevatedButton(onClick = {
                context.sendBroadcast(Intent("TEST_ACTION"))

            }) {
                Text(text = "BROAD CAST RECEIVER")
            }
            ElevatedButton(onClick = {
                Intent(context, TimerActivity::class.java).also {
                    try{
                        context.startActivity(it)
                    }catch (e: ActivityNotFoundException){
                        e.printStackTrace()
                    }
                }
            }) {
                Text(text = "FOREGROUND SERVICE")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun IntentToActivityPreview(){
        IntentToActivity()
}