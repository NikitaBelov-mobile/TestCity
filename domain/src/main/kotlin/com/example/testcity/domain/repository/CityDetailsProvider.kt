package com.example.testcity.domain.repository

import com.example.testcity.domain.model.City

interface CityDetailsProvider {
    suspend fun putAll(cities: List<City>)
    suspend fun getById(id: Long): City?
}
