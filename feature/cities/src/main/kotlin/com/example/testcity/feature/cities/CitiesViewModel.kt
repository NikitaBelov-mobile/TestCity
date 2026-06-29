package com.example.testcity.feature.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testcity.core.ui.mapper.ErrorUiMapper
import com.example.testcity.domain.model.City
import com.example.testcity.domain.usecase.ObserveCitiesUseCase
import com.example.testcity.domain.usecase.ObserveNetworkStatusUseCase
import com.example.testcity.domain.usecase.RefreshCitiesUseCase
import com.example.testcity.feature.cities.mvi.CitiesIntent
import com.example.testcity.feature.cities.mvi.CitiesSideEffect
import com.example.testcity.feature.cities.mvi.CitiesUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class CitiesViewModel(
    observeCitiesUseCase: ObserveCitiesUseCase,
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
    private val refreshCitiesUseCase: RefreshCitiesUseCase,
) : ViewModel(), ContainerHost<CitiesUiState, CitiesSideEffect> {

    override val container = container<CitiesUiState, CitiesSideEffect>(CitiesUiState())

    private val queryFlow = MutableStateFlow("")

    val citiesPagingData: Flow<PagingData<City>> =
        observeCitiesUseCase(queryFlow).cachedIn(viewModelScope)

    private var isBound = false

    fun bind() {
        if (isBound) return
        isBound = true
        observeNetworkStatus()
    }

    fun onIntent(intent: CitiesIntent) {
        when (intent) {
            is CitiesIntent.QueryChanged -> onQueryChanged(intent.value)
            CitiesIntent.RetryRefresh -> onRetryRefresh()
            is CitiesIntent.CityClicked -> onCityClicked(intent.cityId)
        }
    }

    private fun observeNetworkStatus() = intent {
        observeNetworkStatusUseCase().collect { status ->
            reduce { state.copy(networkStatus = status) }
        }
    }

    private fun onQueryChanged(value: String) = intent {
        reduce { state.copy(query = value, refreshError = null) }
        queryFlow.value = value
    }

    private fun onRetryRefresh() = intent {
        reduce { state.copy(refreshError = null) }
        refreshCitiesUseCase(state.query).onFailure { error ->
            reduce {
                state.copy(refreshError = ErrorUiMapper.fromThrowable(error))
            }
        }
    }

    private fun onCityClicked(cityId: Long) = intent {
        postSideEffect(CitiesSideEffect.NavigateToDetail(cityId))
    }
}
