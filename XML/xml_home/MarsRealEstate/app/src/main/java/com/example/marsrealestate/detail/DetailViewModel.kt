/*
 *  Copyright 2018, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.marsrealestate.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.marsrealestate.R
import com.example.marsrealestate.network.MarsRealEstateItem

/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
class DetailViewModel(
    marsProperty: MarsRealEstateItem, app: Application
) : AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<MarsRealEstateItem>()

    val selectedProperty: LiveData<MarsRealEstateItem>
        get() = _selectedProperty

    // Using the LiveData.map extension function directly
    val displayPropertyType: LiveData<String> = selectedProperty.map { marsProperty ->
        app.applicationContext.getString(
            R.string.display_type,
            app.applicationContext.getString(
                when(marsProperty.isRental) { // 'it' is now 'marsProperty' for clarity, but 'it' also works
                    true -> R.string.type_rent
                    false -> R.string.type_sale
                }))
    }

    val displayPropertyPrice: LiveData<String> = selectedProperty.map { marsProperty ->
        app.applicationContext.getString(
            when (marsProperty.isRental) {
                true -> R.string.display_price_monthly_rental
                false -> R.string.display_price
            }, marsProperty.price.toDouble())
    }

    init {
        _selectedProperty.value = marsProperty
    }
}