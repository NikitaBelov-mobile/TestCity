package com.example.testcity.feature.cities.di

import com.example.testcity.feature.cities.CitiesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val citiesFeatureModule = module {
    viewModel {
        CitiesViewModel(
            observeCitiesUseCase = get(),
            observeNetworkStatusUseCase = get(),
            refreshCitiesUseCase = get(),
        )
    }
}
