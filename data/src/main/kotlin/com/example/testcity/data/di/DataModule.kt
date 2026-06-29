package com.example.testcity.data.di

import com.example.testcity.core.network.HttpClientFactory
import com.example.testcity.data.local.CitiesLocalDataSource
import com.example.testcity.data.local.NoOpCitiesLocalDataSource
import com.example.testcity.data.mapper.ThrowableMapper
import com.example.testcity.data.network.AndroidNetworkStatusProvider
import com.example.testcity.data.remote.CitiesRemoteDataSource
import com.example.testcity.data.repository.CitiesRepositoryImpl
import com.example.testcity.data.repository.InMemoryCityDetailsProvider
import com.example.testcity.domain.repository.CitiesRepository
import com.example.testcity.domain.repository.CityDetailsProvider
import com.example.testcity.domain.repository.NetworkStatusProvider
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<HttpClient> { HttpClientFactory(get()).create() }
    single { ThrowableMapper() }
    single { CitiesRemoteDataSource(get(), get()) }
    single<CityDetailsProvider> { InMemoryCityDetailsProvider() }
    single<CitiesLocalDataSource> { NoOpCitiesLocalDataSource() }
    single<CitiesRepository> {
        CitiesRepositoryImpl(
            remote = get(),
            cacheCitiesBatchUseCase = get(),
            localDataSource = get(),
            networkConfig = get(),
            dispatchers = get(),
        )
    }
    single<NetworkStatusProvider> { AndroidNetworkStatusProvider(androidContext()) }
}
