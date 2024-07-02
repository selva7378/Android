package com.example.connectbuddy.viewmodel

import android.app.Application
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application, private val activity: ComponentActivity) : AndroidViewModel(application) {
    private val _isBookPosture = MutableStateFlow(false)
    var isBookPosture: StateFlow<Boolean> = _isBookPosture

    private val windowInfoTracker = WindowInfoTracker.getOrCreate(activity)

    init {
        viewModelScope.launch {
            windowInfoTracker.windowLayoutInfo(activity).collect { layoutInfo ->
                val isBookMode = layoutInfo.displayFeatures
                    .filterIsInstance<FoldingFeature>()
                    ?.any { it.state == FoldingFeature.State.HALF_OPENED } == true
                _isBookPosture.value = isBookMode
                Log.i("mainviewmodel", "$isBookMode")
            }
        }
    }
}



class MainViewModelFactory(private val application: Application, private val activity: ComponentActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(application, activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//val context = LocalContext.current
//val windowLayoutInfo = remember { mutableStateOf<WindowLayoutInfo?>(null) }
//val lifecycleOwner = LocalLifecycleOwner.current
//// Perform a side-effect to observe window layout info
//LaunchedEffect(Unit) {
//    val windowInfoTracker = WindowInfoTracker.getOrCreate(context)
//
//    lifecycleOwner.lifecycleScope.launch {
//        windowInfoTracker.windowLayoutInfo(context as Activity).collect { layoutInfo ->
//            windowLayoutInfo.value = layoutInfo
//        }
//    }
//}
//
//val halfOpenState = remember {
//    derivedStateOf {
//        windowLayoutInfo.value?.displayFeatures
//            ?.filterIsInstance<FoldingFeature>()
//            ?.any { it.state == FoldingFeature.State.HALF_OPENED } == true
//    }
//}