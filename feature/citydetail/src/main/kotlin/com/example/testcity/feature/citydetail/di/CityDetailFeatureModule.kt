package com.example.testcity.feature.citydetail.di

import com.example.testcity.feature.citydetail.CityDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val cityDetailFeatureModule = module {
    viewModel { (cityId: Long) ->
        CityDetailViewModel(
            cityId = cityId,
            getCityDetailsUseCase = get(),
            observeNetworkStatusUseCase = get(),
        )
    }
}
