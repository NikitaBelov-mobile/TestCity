package com.example.testcity.data.mapper

import com.example.testcity.domain.model.AppError
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException
import java.net.SocketTimeoutException

class ThrowableMapper {
    fun map(throwable: Throwable): AppError = when (throwable) {
        is IOException -> when (throwable) {
            is SocketTimeoutException -> AppError.Timeout
            else -> AppError.NoNetwork
        }
        is HttpRequestTimeoutException -> AppError.Timeout
        is ClientRequestException -> AppError.Server(throwable.response.status.value)
        is ServerResponseException -> AppError.Server(throwable.response.status.value)
        else -> AppError.Unknown(throwable.message)
    }
}
