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

package com.example.marsrealestate.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class MarsApiFilter(val value: String) {
    SHOW_RENT("rent"),
    SHOW_BUY("buy"),
    SHOW_ALL("all")
}

val json = Json {
    ignoreUnknownKeys = true // avoids crashes on unknown fields
}

private const val BASE_URL = "https://mars.udacity.com/"

private val contentType = "application/json".toMediaType()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory(contentType))
    .baseUrl(BASE_URL)
    .build()


interface MarsApiService {
    @GET("realestate")
    suspend fun getProperties(@Query("filter") type: String): List<MarsRealEstateItem>
}

object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}
