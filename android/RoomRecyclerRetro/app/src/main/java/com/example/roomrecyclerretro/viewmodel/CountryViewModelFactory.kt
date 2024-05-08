package com.example.roomrecyclerretro.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomrecyclerretro.database.CountriesDatabaseDao

class CountryViewModelFactory(private val dataSource: CountriesDatabaseDao,
                              private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            return CountryViewModel(dataSource, application) as? T // Use safe cast operator as?
                ?: throw IllegalArgumentException("Unknown ViewModel class")
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}