package com.example.testcity.feature.citydetail

import androidx.lifecycle.ViewModel
import com.example.testcity.domain.usecase.GetCityDetailsUseCase
import com.example.testcity.domain.usecase.ObserveNetworkStatusUseCase
import com.example.testcity.feature.citydetail.mapper.BrowserUrlBuilder
import com.example.testcity.core.ui.mapper.ErrorUiMapper
import com.example.testcity.feature.citydetail.mapper.toUiModel
import com.example.testcity.feature.citydetail.mvi.CityDetailIntent
import com.example.testcity.feature.citydetail.mvi.CityDetailSideEffect
import com.example.testcity.feature.citydetail.mvi.CityDetailUiState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class CityDetailViewModel(
    private val cityId: Long,
    private val getCityDetailsUseCase: GetCityDetailsUseCase,
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
) : ViewModel(), ContainerHost<CityDetailUiState, CityDetailSideEffect> {

    override val container = container<CityDetailUiState, CityDetailSideEffect>(CityDetailUiState())

    private var isBound = false

    fun bind() {
        if (isBound) return
        isBound = true
        loadCity()
        observeNetworkStatus()
    }

    fun onIntent(intent: CityDetailIntent) {
        when (intent) {
            CityDetailIntent.Retry -> loadCity()
            CityDetailIntent.SearchInBrowser -> onSearchInBrowser()
        }
    }

    private fun loadCity() = intent {
        reduce { state.copy(error = null) }
        getCityDetailsUseCase(cityId).fold(
            onSuccess = { city ->
                reduce {
                    state.copy(
                        isLoading = false,
                        city = city.toUiModel(),
                        error = null,
                    )
                }
            },
            onFailure = { error ->
                reduce {
                    state.copy(
                        isLoading = false,
                        city = null,
                        error = ErrorUiMapper.fromThrowable(error),
                    )
                }
            },
        )
    }

    private fun observeNetworkStatus() = intent {
        observeNetworkStatusUseCase().collect { status ->
            reduce { state.copy(networkStatus = status) }
        }
    }

    private fun onSearchInBrowser() = intent {
        val cityName = state.city?.name ?: return@intent
        postSideEffect(
            CityDetailSideEffect.OpenBrowser(
                BrowserUrlBuilder.googleSearchUrl(cityName),
            ),
        )
    }
}
