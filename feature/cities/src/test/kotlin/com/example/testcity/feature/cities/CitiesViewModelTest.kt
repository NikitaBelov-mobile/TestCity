package com.example.testcity.feature.cities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.example.testcity.domain.model.City
import com.example.testcity.domain.model.NetworkStatus
import com.example.testcity.domain.repository.CitiesRepository
import com.example.testcity.domain.repository.NetworkStatusProvider
import com.example.testcity.domain.usecase.ObserveCitiesUseCase
import com.example.testcity.domain.usecase.ObserveNetworkStatusUseCase
import com.example.testcity.domain.usecase.RefreshCitiesUseCase
import com.example.testcity.feature.cities.mvi.CitiesIntent
import com.example.testcity.feature.cities.mvi.CitiesSideEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.orbitmvi.orbit.test.test

class CitiesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val citiesRepository = object : CitiesRepository {
        override fun pagingCities(query: String): Flow<PagingData<City>> =
            flowOf(PagingData.empty())

        override suspend fun refresh(query: String): Result<Unit> = Result.success(Unit)
    }

    private val networkStatusProvider = object : NetworkStatusProvider {
        override val status: Flow<NetworkStatus> = flowOf(NetworkStatus.Available)
    }

    private fun createViewModel(): CitiesViewModel = CitiesViewModel(
        observeCitiesUseCase = ObserveCitiesUseCase(citiesRepository),
        observeNetworkStatusUseCase = ObserveNetworkStatusUseCase(networkStatusProvider),
        refreshCitiesUseCase = RefreshCitiesUseCase(citiesRepository),
    )

    @Test
    fun `query changed updates state`() = runTest(UnconfinedTestDispatcher()) {
        val viewModel = createViewModel()
        viewModel.test(this) {
            expectInitialState()
            viewModel.onIntent(CitiesIntent.QueryChanged("Moscow"))
            expectState { copy(query = "Moscow", refreshError = null) }
        }
    }

    @Test
    fun `city clicked emits navigation side effect`() = runTest(UnconfinedTestDispatcher()) {
        val viewModel = createViewModel()
        viewModel.test(this) {
            expectInitialState()
            viewModel.onIntent(CitiesIntent.CityClicked(42))
            expectSideEffect(CitiesSideEffect.NavigateToDetail(42))
        }
    }
}
