package com.example.retrorecycler.datalayer

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("images")
    fun getData(): Call<CountryFlag>
}