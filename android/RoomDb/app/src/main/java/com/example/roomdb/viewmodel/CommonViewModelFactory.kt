package com.example.roomdb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdb.database.EmployeeDatabaseDao

class CommonViewModelFactory(private val dataSource: EmployeeDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommonViewModel::class.java)) {
            return CommonViewModel(dataSource, application) as? T // Use safe cast operator as?
                ?: throw IllegalArgumentException("Unknown ViewModel class")
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

