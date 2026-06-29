package com.example.testcity.feature.cities.mvi

import com.example.testcity.core.ui.model.UiError
import com.example.testcity.domain.model.NetworkStatus

data class CitiesUiState(
    val query: String = "",
    val networkStatus: NetworkStatus = NetworkStatus.Available,
    val refreshError: UiError? = null,
)

sealed interface CitiesIntent {
    data class QueryChanged(val value: String) : CitiesIntent
    data object RetryRefresh : CitiesIntent
    data class CityClicked(val cityId: Long) : CitiesIntent
}

sealed interface CitiesSideEffect {
    data class NavigateToDetail(val cityId: Long) : CitiesSideEffect
}
