package com.example.devbytes.viewmodels

import androidx.lifecycle.*
import com.example.devbytes.repository.VideosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevByteViewModel @Inject constructor(
    private val videosRepository: VideosRepository
) : ViewModel() {
    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        viewModelScope.launch {
            videosRepository.refreshVideos()
        }
    }

    val playlist = videosRepository.videos

}
