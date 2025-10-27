package com.gkk.darkhorseapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform