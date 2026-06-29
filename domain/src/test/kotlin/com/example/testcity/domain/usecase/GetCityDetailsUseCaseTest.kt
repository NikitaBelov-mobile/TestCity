package com.example.testcity.domain.usecase

import com.example.testcity.domain.fake.FakeCityDetailsProvider
import com.example.testcity.domain.model.AppError
import com.example.testcity.domain.model.AppErrorException
import com.example.testcity.domain.model.City
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetCityDetailsUseCaseTest {

    private val provider = FakeCityDetailsProvider()
    private val useCase = GetCityDetailsUseCase(provider)

    @Test
    fun `returns city when cached`() = runTest {
        val city = City(id = 1, name = "Moscow", countryCode = "RU", population = 12_000_000)
        provider.putAll(listOf(city))

        val result = useCase(1)

        assertTrue(result.isSuccess)
        assertEquals(city, result.getOrNull())
    }

    @Test
    fun `returns unknown error when city missing`() = runTest {
        val result = useCase(99)

        assertTrue(result.isFailure)
        val error = (result.exceptionOrNull() as AppErrorException).appError
        assertEquals(AppError.CityNotFound, error)
    }
}
