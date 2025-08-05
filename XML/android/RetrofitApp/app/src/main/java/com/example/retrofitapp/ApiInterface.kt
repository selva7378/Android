package com.example.retrofitapp

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("capital")
    fun getData(): Call<CountryCapitalDataClass>
}