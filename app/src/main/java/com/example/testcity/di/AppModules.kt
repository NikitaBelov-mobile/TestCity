package com.example.testcity.di

import com.example.testcity.BuildConfig
import com.example.testcity.core.common.DefaultDispatcherProvider
import com.example.testcity.core.common.DispatcherProvider
import com.example.testcity.core.network.NetworkConfig
import com.example.testcity.data.di.dataModule
import com.example.testcity.domain.di.domainModule
import com.example.testcity.feature.cities.di.citiesFeatureModule
import com.example.testcity.feature.citydetail.di.cityDetailFeatureModule
import org.koin.dsl.module

val coreModule = module {
    single<DispatcherProvider> { DefaultDispatcherProvider() }
}

val networkModule = module {
    single {
        NetworkConfig(
            baseUrl = BuildConfig.API_BASE_URL,
            defaultPageSize = BuildConfig.DEFAULT_PAGE_SIZE,
            enableLogging = BuildConfig.DEBUG,
        )
    }
}

val appModules = listOf(
    coreModule,
    networkModule,
    dataModule,
    domainModule,
    citiesFeatureModule,
    cityDetailFeatureModule,
)
