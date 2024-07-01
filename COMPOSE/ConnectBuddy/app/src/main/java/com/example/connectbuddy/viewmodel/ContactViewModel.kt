package com.example.connectbuddy.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectbuddy.network.dataclass.Contact
import com.example.connectbuddy.repository.ContactApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private val  TAG = "ViewModel"

sealed interface ContactUiState {
    object Success : ContactUiState
    object Error : ContactUiState
    object Loading : ContactUiState
}

class ContactViewModel(
    private val contactApiRepository: ContactApiRepository
): ViewModel() {

    var contactUiState: ContactUiState by mutableStateOf(ContactUiState.Loading)
        private set

    private var _contactData: MutableStateFlow<Contact> = MutableStateFlow(Contact())
    val contactData: StateFlow<Contact> = _contactData

    init {
        getNewsFromApi()
        Log.i(TAG, "result list size ${_contactData.value.results.size}")
    }

    fun getNewsFromApi() {
        Log.i("ViewModel", "getfromApi executed")
        viewModelScope.launch {
            try {
                val fetchedData = contactApiRepository.getContacts()
                _contactData.value = fetchedData
                contactUiState = ContactUiState.Success
                Log.i(TAG, "result list size ${_contactData.value.results.size}")
                Log.e("ViewModel", "api successfully excecuted")

            } catch (e: IOException) {
                contactUiState = ContactUiState.Error
                Log.e("ViewModel", "Network error due to IOException", e)

            } catch (e: HttpException) {
                Log.e("ViewModel", "HTTP error due to HttpException", e)
                contactUiState = ContactUiState.Error

            } catch (e: Exception) {
                Log.e("ViewModel", "Unexpected error", e)
                contactUiState = ContactUiState.Error

            }
        }
    }


}