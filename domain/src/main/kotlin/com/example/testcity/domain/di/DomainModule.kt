package com.example.testcity.domain.di

import com.example.testcity.domain.usecase.CacheCitiesBatchUseCase
import com.example.testcity.domain.usecase.GetCityDetailsUseCase
import com.example.testcity.domain.usecase.ObserveCitiesUseCase
import com.example.testcity.domain.usecase.ObserveNetworkStatusUseCase
import com.example.testcity.domain.usecase.RefreshCitiesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { ObserveNetworkStatusUseCase(get()) }
    factory { ObserveCitiesUseCase(get()) }
    factory { RefreshCitiesUseCase(get()) }
    factory { GetCityDetailsUseCase(get()) }
    factory { CacheCitiesBatchUseCase(get()) }
}
