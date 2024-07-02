package com.example.connectbuddy

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.connectbuddy.viewmodel.ContactViewModel


object AndroidViewModelProvider {

    val Factory = viewModelFactory {

        initializer {
            ContactViewModel(
                contactApplication().container.contactApiRepository
            )
        }
    }
}



fun CreationExtras.contactApplication(): ContactApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ContactApplication)