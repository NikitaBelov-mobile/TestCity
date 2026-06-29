package com.example.testcity.domain.model

sealed interface AppError {
    data object NoNetwork : AppError
    data object Timeout : AppError
    data object CityNotFound : AppError
    data class Server(val code: Int) : AppError
    data class Unknown(val message: String?) : AppError
}
