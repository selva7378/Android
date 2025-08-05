package com.example.newsweather.api

data class NewsApi(
    val category: String,
    val `data`: List<Data>,
    val success: String
)