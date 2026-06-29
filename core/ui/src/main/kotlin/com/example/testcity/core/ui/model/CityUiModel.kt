package com.example.testcity.core.ui.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.testcity.core.ui.R

data class CityUiModel(
    val id: Long,
    val name: String,
    val country: String,
    val population: Long,
)

data class UiError(
    val message: UiText,
    val isRetryable: Boolean,
)

sealed interface UiText {
    data class Resource(@StringRes val id: Int) : UiText
    data class ResourceInt(
        @StringRes val id: Int,
        val value: Int,
    ) : UiText
}

fun UiError.isNoNetwork(): Boolean =
    message is UiText.Resource && message.id == R.string.error_no_network

@Composable
fun UiText.resolve(): String = when (this) {
    is UiText.Resource -> stringResource(id)
    is UiText.ResourceInt -> stringResource(id, value)
}
