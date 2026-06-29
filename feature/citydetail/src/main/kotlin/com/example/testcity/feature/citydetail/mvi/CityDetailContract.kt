package com.example.testcity.feature.citydetail.mvi

import com.example.testcity.core.ui.model.CityUiModel
import com.example.testcity.core.ui.model.UiError
import com.example.testcity.domain.model.NetworkStatus

data class CityDetailUiState(
    val isLoading: Boolean = false,
    val city: CityUiModel? = null,
    val error: UiError? = null,
    val networkStatus: NetworkStatus = NetworkStatus.Available,
)

sealed interface CityDetailIntent {
    data object Retry : CityDetailIntent
    data object SearchInBrowser : CityDetailIntent
}

sealed interface CityDetailSideEffect {
    data class OpenBrowser(val url: String) : CityDetailSideEffect
}
