package com.example.testcity.data.local

import com.example.testcity.domain.model.City

interface CitiesLocalDataSource {
    suspend fun saveCities(cities: List<City>)
    suspend fun getCityById(id: Long): City?
}

class NoOpCitiesLocalDataSource : CitiesLocalDataSource {
    override suspend fun saveCities(cities: List<City>) = Unit
    override suspend fun getCityById(id: Long): City? = null
}
