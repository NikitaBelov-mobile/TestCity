package com.example.testcity.domain.fake

import com.example.testcity.domain.model.City
import com.example.testcity.domain.repository.CityDetailsProvider

class FakeCityDetailsProvider : CityDetailsProvider {
    private val cache = mutableMapOf<Long, City>()

    override suspend fun putAll(cities: List<City>) {
        cities.forEach { cache[it.id] = it }
    }

    override suspend fun getById(id: Long): City? = cache[id]
}
