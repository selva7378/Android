package com.example.roomrecyclerretro

import com.example.roomrecyclerretro.networklayer.CountriesItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("all?fields=name,flags")
    fun getData(): Call<List<CountriesItem?>>
}