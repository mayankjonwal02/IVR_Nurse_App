package com.example.ivr_nurse_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform