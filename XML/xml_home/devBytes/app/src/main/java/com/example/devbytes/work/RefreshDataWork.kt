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

package com.example.devbytes.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.devbytes.repository.VideosRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import timber.log.Timber

@HiltWorker // <--- This annotation is crucial for Hilt to manage this Worker
class RefreshDataWork @AssistedInject constructor( // <--- Use @AssistedInject
    @Assisted appContext: Context, // <--- Hilt provides this automatically
    @Assisted workerParams: WorkerParameters, // <--- Hilt provides this automatically
    // Now, inject your dependencies
    private val videosRepository: VideosRepository // <--- Inject your repository
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val WORK_NAME = "RefreshVideosWorker"
    }

    override suspend fun doWork(): Result { // <--- Remember to return Result directly
        Timber.i( "Starting video refresh work...")

        return try {
            videosRepository.refreshVideos()
            Timber.i("Videos refreshed successfully.")
            Result.success()
        } catch (e: HttpException) {
            Timber.i(e, "HTTP error refreshing videos: ${e.message}")
            Result.retry() // Retry for network-related errors
        } catch (e: Exception) {
            Timber.i("Unexpected error refreshing videos: ${e.message}")
            Result.failure() // Permanent failure for other exceptions
        }
    }
}
