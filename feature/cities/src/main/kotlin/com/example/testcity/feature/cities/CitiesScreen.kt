package com.example.testcity.feature.cities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.testcity.core.ui.R
import com.example.testcity.core.ui.components.CityListItem
import com.example.testcity.core.ui.components.EmptyContent
import com.example.testcity.core.ui.components.ErrorContent
import com.example.testcity.core.ui.components.LoadingContent
import com.example.testcity.core.ui.components.LoadingFooter
import com.example.testcity.core.ui.components.NoNetworkContent
import com.example.testcity.core.ui.components.PagingAppendError
import com.example.testcity.core.ui.components.RefreshErrorBanner
import com.example.testcity.core.ui.components.SearchTextField
import com.example.testcity.core.ui.model.UiError
import com.example.testcity.core.ui.model.isNoNetwork
import com.example.testcity.core.ui.model.resolve
import com.example.testcity.core.ui.theme.TestCityColors
import com.example.testcity.core.ui.theme.TestCityDimens
import com.example.testcity.core.ui.mapper.ErrorUiMapper
import com.example.testcity.domain.model.City
import com.example.testcity.domain.model.NetworkStatus
import com.example.testcity.feature.cities.mapper.toUiModel
import com.example.testcity.feature.cities.mvi.CitiesIntent
import com.example.testcity.feature.cities.mvi.CitiesUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesScreen(
    state: CitiesUiState,
    cities: LazyPagingItems<City>,
    onIntent: (CitiesIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val derived = rememberCitiesScreenState(state, cities)

    if (derived.showNoNetworkFullScreen) {
        NoNetworkContent(
            onRetry = { retryPaging(cities, onIntent) },
            modifier = modifier,
        )
        return
    }

    if (derived.isInitialLoadError && derived.pagingRefreshError != null && !derived.isNoNetworkError) {
        val error = derived.pagingRefreshError
        ErrorContent(
            message = error.message.resolve(),
            onRetry = if (error.isRetryable) {
                { retryForError(error, cities, onIntent) }
            } else {
                null
            },
            modifier = modifier,
        )
        return
    }

    CitiesMainScaffold(
        state = state,
        cities = cities,
        derived = derived,
        onIntent = onIntent,
        modifier = modifier,
    )
}

@Composable
private fun rememberCitiesScreenState(
    state: CitiesUiState,
    cities: LazyPagingItems<City>,
): CitiesScreenState {
    val hasItems = cities.itemCount > 0
    val refreshState = cities.loadState.refresh
    val pagingRefreshError = remember(refreshState, hasItems) {
        (refreshState as? LoadState.Error)?.error?.let { ErrorUiMapper.fromThrowable(it) }
    }
    val isNoNetworkError = pagingRefreshError?.isNoNetwork() == true
    val inlineRefreshError = state.refreshError ?: pagingRefreshError?.takeIf { hasItems && !it.isNoNetwork() }
    val showNoNetworkFullScreen = when {
        isNoNetworkError && refreshState is LoadState.Error -> true
        state.networkStatus == NetworkStatus.Lost &&
            !hasItems &&
            refreshState !is LoadState.NotLoading -> true
        else -> false
    }
    return CitiesScreenState(
        hasItems = hasItems,
        refreshState = refreshState,
        pagingRefreshError = pagingRefreshError,
        isNoNetworkError = isNoNetworkError,
        inlineRefreshError = inlineRefreshError,
        showNoNetworkFullScreen = showNoNetworkFullScreen,
        isInitialLoadError = refreshState is LoadState.Error && !hasItems,
    )
}

private data class CitiesScreenState(
    val hasItems: Boolean,
    val refreshState: LoadState,
    val pagingRefreshError: UiError?,
    val isNoNetworkError: Boolean,
    val inlineRefreshError: UiError?,
    val showNoNetworkFullScreen: Boolean,
    val isInitialLoadError: Boolean,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CitiesMainScaffold(
    state: CitiesUiState,
    cities: LazyPagingItems<City>,
    derived: CitiesScreenState,
    onIntent: (CitiesIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullRefreshState = rememberPullToRefreshState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = TestCityColors.Background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.cities_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = TestCityColors.TextPrimary,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = TestCityColors.Surface,
                ),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(TestCityColors.Background),
        ) {
            SearchTextField(
                value = state.query,
                onValueChange = { onIntent(CitiesIntent.QueryChanged(it)) },
                modifier = Modifier.padding(
                    horizontal = TestCityDimens.spacing16,
                    vertical = TestCityDimens.spacing12,
                ),
            )

            derived.inlineRefreshError?.let { error ->
                RefreshErrorBanner(
                    message = error.message.resolve(),
                    onRetry = { retryForError(error, cities, onIntent) },
                )
            }

            PullToRefreshBox(
                isRefreshing = derived.refreshState is LoadState.Loading && derived.hasItems,
                onRefresh = { retryPaging(cities, onIntent) },
                state = pullRefreshState,
                modifier = Modifier.fillMaxSize(),
            ) {
                CitiesBody(
                    cities = cities,
                    onIntent = onIntent,
                )
            }
        }
    }
}

@Composable
private fun CitiesBody(
    cities: LazyPagingItems<City>,
    onIntent: (CitiesIntent) -> Unit,
) {
    val hasItems = cities.itemCount > 0
    val refreshState = cities.loadState.refresh

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            refreshState is LoadState.Loading && !hasItems -> LoadingContent()
            refreshState is LoadState.NotLoading && !hasItems -> EmptyContent()
            else -> CitiesList(
                cities = cities,
                onCityClicked = { onIntent(CitiesIntent.CityClicked(it)) },
            )
        }
    }
}

private fun retryPaging(
    cities: LazyPagingItems<City>,
    onIntent: (CitiesIntent) -> Unit,
) {
    cities.retry()
    onIntent(CitiesIntent.RetryRefresh)
}

private fun retryForError(
    error: UiError,
    cities: LazyPagingItems<City>,
    onIntent: (CitiesIntent) -> Unit,
) {
    if (error.isNoNetwork() || error.isRetryable) {
        onIntent(CitiesIntent.RetryRefresh)
    } else {
        cities.retry()
    }
}

@Composable
private fun CitiesList(
    cities: LazyPagingItems<City>,
    onCityClicked: (Long) -> Unit,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(
            count = cities.itemCount,
            key = cities.itemKey { it.id },
        ) { index ->
            cities[index]?.let { city ->
                val uiModel = city.toUiModel()
                CityListItem(
                    name = uiModel.name,
                    country = uiModel.country,
                    onClick = { onCityClicked(uiModel.id) },
                )
            }
        }

        when (cities.loadState.append) {
            is LoadState.Loading -> {
                item {
                    LoadingFooter(modifier = Modifier.padding(vertical = TestCityDimens.spacing16))
                }
            }
            is LoadState.Error -> {
                item {
                    PagingAppendError(onRetry = { cities.retry() })
                }
            }
            else -> Unit
        }
    }
}
