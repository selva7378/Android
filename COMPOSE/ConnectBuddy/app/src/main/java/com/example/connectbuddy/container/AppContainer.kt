package com.example.connectbuddy.container

import android.content.Context
import android.net.Network
import com.example.connectbuddy.network.ContactApiService
import com.example.connectbuddy.repository.ContactApiRepository
import com.example.connectbuddy.repository.NetworkContactsApiRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val contactApiRepository: ContactApiRepository
}


class ContactAppContainer(private val context: Context) : AppContainer {

    private val baseUrl =
        "https://randomuser.me/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ContactApiService by lazy {
        retrofit.create(ContactApiService::class.java)
    }

    override val contactApiRepository: ContactApiRepository by lazy {
        NetworkContactsApiRepository(retrofitService)
    }
}