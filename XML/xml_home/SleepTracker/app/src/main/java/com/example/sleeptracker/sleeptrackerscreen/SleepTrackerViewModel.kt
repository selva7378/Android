/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sleeptracker.sleeptrackerscreen

import android.app.Application
import android.provider.SyncStateContract.Helpers.insert
import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.sleeptracker.database.SleepDatabaseDao
import com.example.sleeptracker.database.SleepNight
import kotlinx.coroutines.launch

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private val tonight = MutableLiveData<SleepNight?>()
    val nights = database.getAllNights()

    // Create a NEW LiveData that transforms the list into DataItems
    val nightsWithHeader: LiveData<List<DataItem>> = nights.map { sleepNights ->
        // This transformation logic is now in the ViewModel
        listOf(DataItem.Header) + sleepNights.map { DataItem.SleepNightItem(it) }
    }

    val startButtonVisible = tonight.map {
        null == it
    }



    val stopButtonVisible = tonight.map {
        null != it
    }

    val clearButtonVisible = nights.map {
        it?.isNotEmpty()
    }

    private var _showSnackbarEvent = MutableLiveData<Boolean>(false)

    val showSnackBarEvent: LiveData<Boolean> = _showSnackbarEvent

    private val _navigateToSleepDataQuality = MutableLiveData<Long?>()
    val navigateToSleepDataQuality
        get() = _navigateToSleepDataQuality

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    val nightsString = nights.map { nights ->
        formatNights(nights, application.resources)
    }

    private val _navigateToSleepQuality = MutableLiveData<SleepNight?>()
    val navigateToSleepQuality: LiveData<SleepNight?> = _navigateToSleepQuality

    init {
        initializeTonight()
    }


    fun doneNavigating(){
        _navigateToSleepQuality.value = null
    }
    private fun initializeTonight(): SleepNight? {
        viewModelScope.launch {
            var night = database.getTonight()
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            tonight.value = night
        }
        return tonight.value
    }

    fun onStartTracking() {
        var newNight = SleepNight()
        viewModelScope.launch {
            database.insert(newNight)
            tonight.value = initializeTonight()
        }
    }


    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            database.update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    fun onSleepNightClicked(nightId: Long) {
        _navigateToSleepDataQuality.value = nightId
    }
    fun onSleepDataQualityNavigated() {
        _navigateToSleepDataQuality.value = null
    }

    fun onClear() {
        viewModelScope.launch {
            database.clear()
            tonight.value = null
            _showSnackbarEvent.value = true
        }
    }
}

