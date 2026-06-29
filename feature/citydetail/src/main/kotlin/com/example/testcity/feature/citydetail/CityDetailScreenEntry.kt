package com.example.testcity.feature.citydetail

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.testcity.feature.citydetail.mvi.CityDetailSideEffect
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun CityDetailScreenEntry(
    cityId: Long,
    onBack: () -> Unit,
) {
    val viewModel: CityDetailViewModel = koinViewModel { parametersOf(cityId) }
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.bind()
    }

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {
                is CityDetailSideEffect.OpenBrowser -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(effect.url))
                    context.startActivity(intent)
                }
            }
        }
    }

    CityDetailScreen(
        state = state,
        onIntent = viewModel::onIntent,
        onBack = onBack,
    )
}
