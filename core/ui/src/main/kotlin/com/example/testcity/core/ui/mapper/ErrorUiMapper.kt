package com.example.testcity.core.ui.mapper

import com.example.testcity.core.ui.R
import com.example.testcity.core.ui.model.UiError
import com.example.testcity.core.ui.model.UiText
import com.example.testcity.domain.model.AppError
import com.example.testcity.domain.model.AppErrorException
import java.io.IOException
import java.net.SocketTimeoutException

object ErrorUiMapper {
    fun fromThrowable(throwable: Throwable): UiError {
        val appError = (throwable as? AppErrorException)?.appError ?: mapThrowable(throwable)
        return fromAppError(appError)
    }

    fun fromAppError(error: AppError): UiError = when (error) {
        AppError.NoNetwork -> UiError(UiText.Resource(R.string.error_no_network), isRetryable = true)
        AppError.Timeout -> UiError(UiText.Resource(R.string.error_timeout), isRetryable = true)
        AppError.CityNotFound -> UiError(UiText.Resource(R.string.city_not_found), isRetryable = false)
        is AppError.Server -> {
            val retryable = error.code >= 500
            UiError(
                message = if (retryable) {
                    UiText.Resource(R.string.error_server)
                } else {
                    UiText.ResourceInt(R.string.error_client_format, error.code)
                },
                isRetryable = retryable,
            )
        }
        is AppError.Unknown -> UiError(
            message = UiText.Resource(R.string.error_generic),
            isRetryable = false,
        )
    }

    private fun mapThrowable(throwable: Throwable): AppError = when (throwable) {
        is AppErrorException -> throwable.appError
        is SocketTimeoutException -> AppError.Timeout
        is IOException -> AppError.NoNetwork
        else -> AppError.Unknown(throwable.message)
    }
}
