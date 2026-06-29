package com.example.testcity.data.repository

import com.example.testcity.domain.model.City
import com.example.testcity.domain.repository.CityDetailsProvider
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryCityDetailsProvider : CityDetailsProvider {
    private val mutex = Mutex()
    private val cache = mutableMapOf<Long, City>()

    override suspend fun putAll(cities: List<City>) {
        mutex.withLock {
            cities.forEach { cache[it.id] = it }
        }
    }

    override suspend fun getById(id: Long): City? = mutex.withLock { cache[id] }
}
