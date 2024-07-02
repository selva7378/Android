package com.example.connectbuddy

import android.app.Application
import com.example.connectbuddy.container.AppContainer
import com.example.connectbuddy.container.ContactAppContainer


class ContactApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = ContactAppContainer(this)
    }
}

