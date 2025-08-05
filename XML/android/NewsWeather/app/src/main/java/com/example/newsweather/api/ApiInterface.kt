package com.example.newsweather.api

import com.example.newsweather.database.roomdb.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {
//    @GET("/")
//    fun getData(@Query("fields") fields: String): Call<NewsApi?>

    @GET
    fun getData(@Url url: String): Call<NewsApi?>
}