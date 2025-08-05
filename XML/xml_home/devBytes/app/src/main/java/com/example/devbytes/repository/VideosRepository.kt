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
 *
 */

package com.example.devbytes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.devbytes.database.VideosDatabase
import com.example.devbytes.database.asDomainModel
import com.example.devbytes.domain.Video
import com.example.devbytes.network.Network
import com.example.devbytes.network.asDatabaseModel
import com.example.devbytes.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideosRepository @Inject constructor(private val database: VideosDatabase) {

    /**
     * A playlist of videos that can be shown on the screen.
     */
    val videos: LiveData<List<Video>> =
        database.videosDao.getVideos().map {
            it.asDomainModel()
        }

    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playList = Network.devbytes.getPlaylist()
            database.videosDao.insertAll(*playList.asDatabaseModel())
        }
    }
}
