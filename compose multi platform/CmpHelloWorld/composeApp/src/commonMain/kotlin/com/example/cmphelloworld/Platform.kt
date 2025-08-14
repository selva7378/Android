package com.example.cmphelloworld

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform