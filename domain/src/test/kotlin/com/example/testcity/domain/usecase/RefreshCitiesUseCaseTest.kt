package com.example.testcity.domain.usecase

import com.example.testcity.domain.fake.FakeCitiesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RefreshCitiesUseCaseTest {

    @Test
    fun `invoke delegates to repository refresh`() = runTest {
        val repository = FakeCitiesRepository()
        val useCase = RefreshCitiesUseCase(repository)

        val result = useCase("Moscow")

        assertTrue(result.isSuccess)
        assertEquals("Moscow", repository.refreshQuery)
    }
}
