package com.example.testcity.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val networkConfig: NetworkConfig,
) {
    fun create(engine: HttpClientEngine = OkHttp.create()): HttpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                },
            )
        }
        install(HttpTimeout) {
            requestTimeoutMillis = networkConfig.requestTimeoutMillis
            connectTimeoutMillis = networkConfig.connectTimeoutMillis
            socketTimeoutMillis = networkConfig.socketTimeoutMillis
        }
        if (networkConfig.enableLogging) {
            install(Logging) {
                level = LogLevel.BODY
            }
        }
        defaultRequest {
            url(networkConfig.baseUrl)
        }
    }
}
