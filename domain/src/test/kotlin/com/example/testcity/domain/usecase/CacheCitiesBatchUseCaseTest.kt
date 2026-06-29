package com.example.testcity.domain.usecase

import com.example.testcity.domain.fake.FakeCityDetailsProvider
import com.example.testcity.domain.model.City
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CacheCitiesBatchUseCaseTest {

    private val city = City(id = 1, name = "Moscow", countryCode = "RU", population = 12_000_000)

    @Test
    fun `invoke stores cities in provider`() = runTest {
        val provider = FakeCityDetailsProvider()
        val useCase = CacheCitiesBatchUseCase(provider)

        useCase(listOf(city))

        assertEquals(city, provider.getById(1))
    }
}
