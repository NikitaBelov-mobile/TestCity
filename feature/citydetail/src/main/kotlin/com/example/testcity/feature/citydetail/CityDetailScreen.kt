package com.example.testcity.feature.citydetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testcity.core.ui.R
import com.example.testcity.core.ui.components.ErrorContent
import com.example.testcity.core.ui.components.PrimaryButton
import com.example.testcity.core.ui.mapper.CityUiMapper
import com.example.testcity.core.ui.model.CityUiModel
import com.example.testcity.core.ui.model.resolve
import com.example.testcity.core.ui.theme.TestCityColors
import com.example.testcity.core.ui.theme.TestCityDimens
import com.example.testcity.feature.citydetail.mvi.CityDetailIntent
import com.example.testcity.feature.citydetail.mvi.CityDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailScreen(
    state: CityDetailUiState,
    onIntent: (CityDetailIntent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.city_detail_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = TestCityColors.TextPrimary,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = TestCityColors.Surface,
                ),
            )
        },
        containerColor = TestCityColors.Background,
        bottomBar = {
            if (state.city != null && !state.isLoading && state.error == null) {
                PrimaryButton(
                    text = stringResource(R.string.search_city_info),
                    onClick = { onIntent(CityDetailIntent.SearchInBrowser) },
                    modifier = Modifier.padding(
                        horizontal = TestCityDimens.spacing16,
                        vertical = TestCityDimens.spacing16,
                    ),
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when {
                state.isLoading -> CityDetailSkeleton()
                state.error != null -> {
                    if (state.error.isRetryable) {
                        ErrorContent(
                            message = state.error.message.resolve(),
                            onRetry = { onIntent(CityDetailIntent.Retry) },
                        )
                    } else {
                        CityNotFoundContent(
                            message = state.error.message.resolve(),
                            onBack = onBack,
                        )
                    }
                }
                state.city != null -> {
                    CityDetailContent(city = state.city)
                }
            }
        }
    }
}

@Composable
private fun CityDetailSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = TestCityDimens.spacing16,
                vertical = TestCityDimens.spacing16,
            ),
        verticalArrangement = Arrangement.spacedBy(TestCityDimens.spacing16),
    ) {
        repeat(3) {
            Column(verticalArrangement = Arrangement.spacedBy(TestCityDimens.spacing4)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(16.dp)
                        .background(TestCityColors.Skeleton, RoundedCornerShape(4.dp)),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(24.dp)
                        .background(TestCityColors.Skeleton, RoundedCornerShape(4.dp)),
                )
            }
        }
    }
}

@Composable
private fun CityDetailContent(
    city: CityUiModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TestCityColors.Background)
            .padding(horizontal = TestCityDimens.spacing16)
            .padding(top = TestCityDimens.spacing16),
        verticalArrangement = Arrangement.spacedBy(TestCityDimens.spacing16),
    ) {
        DetailField(
            label = stringResource(R.string.city_label),
            value = city.name,
        )
        DetailField(
            label = stringResource(R.string.country_label),
            value = city.country,
        )
        DetailField(
            label = stringResource(R.string.population_label),
            value = stringResource(
                R.string.population_format,
                CityUiMapper.formatPopulation(city.population),
            ),
        )
    }
}

@Composable
private fun CityNotFoundContent(
    message: String,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(TestCityDimens.spacing24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = message,
            color = TestCityColors.Error,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(TestCityDimens.spacing16))
        PrimaryButton(
            text = stringResource(R.string.back),
            onClick = onBack,
        )
    }
}

@Composable
private fun DetailField(
    label: String,
    value: String,
) {
    Column {
        Text(
            text = label,
            color = TestCityColors.TextSecondary,
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            text = value,
            color = TestCityColors.TextPrimary,
            modifier = Modifier.padding(top = TestCityDimens.spacing4),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
