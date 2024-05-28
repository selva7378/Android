package com.example.news.api.weatherapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {
    @GET("weather/realtime")
    fun getWeather(@Query("location") location: String, @Query("apikey") apiKey: String) : Call<Weather?>
}