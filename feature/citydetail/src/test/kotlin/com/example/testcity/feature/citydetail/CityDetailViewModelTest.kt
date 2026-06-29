package com.example.testcity.feature.citydetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testcity.core.ui.mapper.CityUiMapper
import com.example.testcity.domain.model.City
import com.example.testcity.domain.model.NetworkStatus
import com.example.testcity.domain.repository.CityDetailsProvider
import com.example.testcity.domain.repository.NetworkStatusProvider
import com.example.testcity.domain.usecase.GetCityDetailsUseCase
import com.example.testcity.domain.usecase.ObserveNetworkStatusUseCase
import com.example.testcity.feature.citydetail.mvi.CityDetailIntent
import com.example.testcity.feature.citydetail.mvi.CityDetailSideEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.orbitmvi.orbit.test.test

class CityDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val city = City(id = 1, name = "Moscow", countryCode = "RU", population = 12_000_000)

    private val cityUiModel = CityUiMapper.map(
        id = city.id,
        name = city.name,
        countryCode = city.countryCode,
        population = city.population,
    )

    private val cityDetailsProvider = object : CityDetailsProvider {
        override suspend fun putAll(cities: List<City>) = Unit
        override suspend fun getById(id: Long): City? = if (id == 1L) city else null
    }

    private val networkStatusProvider = object : NetworkStatusProvider {
        override val status: Flow<NetworkStatus> = flowOf(NetworkStatus.Available)
    }

    @Test
    fun `loads city from cache`() = runTest(UnconfinedTestDispatcher()) {
        val viewModel = CityDetailViewModel(
            cityId = 1,
            getCityDetailsUseCase = GetCityDetailsUseCase(cityDetailsProvider),
            observeNetworkStatusUseCase = ObserveNetworkStatusUseCase(networkStatusProvider),
        )

        viewModel.test(this) {
            expectInitialState()
            viewModel.bind()
            expectState {
                copy(isLoading = false, city = cityUiModel, error = null)
            }
        }
    }

    @Test
    fun `search in browser emits side effect`() = runTest(UnconfinedTestDispatcher()) {
        val viewModel = CityDetailViewModel(
            cityId = 1,
            getCityDetailsUseCase = GetCityDetailsUseCase(cityDetailsProvider),
            observeNetworkStatusUseCase = ObserveNetworkStatusUseCase(networkStatusProvider),
        )

        viewModel.test(this) {
            expectInitialState()
            viewModel.bind()
            expectState { copy(isLoading = false, city = cityUiModel, error = null) }
            viewModel.onIntent(CityDetailIntent.SearchInBrowser)
            expectSideEffect(CityDetailSideEffect.OpenBrowser("https://www.google.com/search?q=Moscow"))
        }
    }
}
