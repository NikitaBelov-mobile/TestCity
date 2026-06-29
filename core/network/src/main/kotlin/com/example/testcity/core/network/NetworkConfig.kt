package com.example.testcity.core.network

data class NetworkConfig(
    val baseUrl: String,
    val defaultPageSize: Int,
    val enableLogging: Boolean = false,
    val requestTimeoutMillis: Long = 30_000,
    val connectTimeoutMillis: Long = 15_000,
    val socketTimeoutMillis: Long = 30_000,
)
