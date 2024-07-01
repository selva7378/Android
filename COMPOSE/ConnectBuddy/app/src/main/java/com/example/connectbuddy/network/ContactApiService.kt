package com.example.connectbuddy.network

import com.example.connectbuddy.network.dataclass.Contact
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactApiService {
    @GET("?results=25&inc=gender,name,picture,phone,cell,id,email")
    suspend fun getData(): Contact
}



