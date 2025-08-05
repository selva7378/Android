package com.example.news.api.newsapi

data class NewsApi(
    val category: String,
    val `data`: List<Data>,
    val success: String
)