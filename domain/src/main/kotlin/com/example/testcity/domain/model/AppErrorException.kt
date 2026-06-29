package com.example.testcity.domain.model

class AppErrorException(
    val appError: AppError,
) : Exception(appError.toString())
