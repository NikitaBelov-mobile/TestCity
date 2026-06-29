package com.example.testcity.feature.cities

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.testcity.feature.cities.mvi.CitiesSideEffect
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun CitiesScreenEntry(
    onNavigateToDetail: (Long) -> Unit,
) {
    val viewModel: CitiesViewModel = koinViewModel()

    LaunchedEffect(viewModel) {
        viewModel.bind()
    }

    val state by viewModel.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {
                is CitiesSideEffect.NavigateToDetail -> onNavigateToDetail(effect.cityId)
            }
        }
    }

    val pagingItems = viewModel.citiesPagingData.collectAsLazyPagingItems()

    CitiesScreen(
        state = state,
        cities = pagingItems,
        onIntent = viewModel::onIntent,
    )
}
