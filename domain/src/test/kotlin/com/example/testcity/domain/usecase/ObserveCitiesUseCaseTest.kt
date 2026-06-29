package com.example.testcity.domain.usecase

import com.example.testcity.domain.SEARCH_DEBOUNCE_MS
import com.example.testcity.domain.fake.FakeCitiesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveCitiesUseCaseTest {

    @Test
    fun `debounces query before requesting paging data`() = runTest {
        val repository = FakeCitiesRepository()
        val useCase = ObserveCitiesUseCase(repository)
        val query = MutableStateFlow("moscow")

        val job = launch {
            useCase(query).collect { }
        }

        runCurrent()
        advanceTimeBy(SEARCH_DEBOUNCE_MS)
        runCurrent()
        job.cancel()

        assertEquals(listOf("moscow"), repository.pagingQueries)
    }

    @Test
    fun `debounces rapid query changes`() = runTest {
        val repository = FakeCitiesRepository()
        val useCase = ObserveCitiesUseCase(repository)
        val query = MutableStateFlow("")

        val job = launch {
            useCase(query).collect { }
        }
        runCurrent()

        query.value = "mo"
        advanceTimeBy(SEARCH_DEBOUNCE_MS / 2)
        runCurrent()
        query.value = "mos"
        advanceTimeBy(SEARCH_DEBOUNCE_MS / 2)
        runCurrent()
        query.value = "moscow"
        advanceTimeBy(SEARCH_DEBOUNCE_MS)
        runCurrent()
        job.cancel()

        assertEquals(listOf("moscow"), repository.pagingQueries)
    }
}
