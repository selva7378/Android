package com.example.intentapp

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.intentapp.applicationclass.RunningApp
import com.example.intentapp.services.TimerService
import com.example.intentapp.ui.theme.IntentAppTheme

class TimerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Timer(
                        modifier = Modifier.padding(innerPadding)

                    )
                }
            }
        }
    }
}

@Composable
fun Timer(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row {
            ElevatedButton(onClick = {
                Intent(context.applicationContext, TimerService::class.java).also{
                    it.action = TimerService.Actions.START.toString()
                    context.startService(it)
                }
            }) {
                Text(text = "START")
            }
            ElevatedButton(onClick = {
                Intent(context.applicationContext, TimerService::class.java).also{
                    it.action = TimerService.Actions.STOP.toString()
                    context.startService(it)
                }
            }) {
                Text(text = "STOP")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntentAppTheme {
        Timer()
    }
}