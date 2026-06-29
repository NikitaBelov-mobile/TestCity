package com.example.testcity.domain.usecase

import com.example.testcity.domain.model.City
import com.example.testcity.domain.repository.CityDetailsProvider

class CacheCitiesBatchUseCase(
    private val cityDetailsProvider: CityDetailsProvider,
) {
    suspend operator fun invoke(cities: List<City>) {
        cityDetailsProvider.putAll(cities)
    }
}
