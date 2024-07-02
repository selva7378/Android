package com.example.connectbuddy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectbuddy.ui.theme.ConnectBuddyTheme
import com.example.connectbuddy.viewmodel.MainViewModel
import com.example.connectbuddy.viewmodel.MainViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConnectBuddyTheme {
                val viewModel: MainViewModel = viewModel(factory = MainViewModelFactory(application, this))
                val isBookPosture by viewModel.isBookPosture.collectAsState()
                Log.i("Main Activity", "${isBookPosture}")
                ContactBuddy(
                    isBookPosture,
                    modifier = Modifier.padding()
                )
            }
        }
    }
}

