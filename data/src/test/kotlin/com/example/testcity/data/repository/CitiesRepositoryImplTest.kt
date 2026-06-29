package com.example.testcity.data.repository

import com.example.testcity.core.common.DefaultDispatcherProvider
import com.example.testcity.core.network.NetworkConfig
import com.example.testcity.data.local.NoOpCitiesLocalDataSource
import com.example.testcity.data.remote.CitiesRemoteDataSource
import com.example.testcity.domain.usecase.CacheCitiesBatchUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class CitiesRepositoryImplTest {

    @Test
    fun `refresh returns success`() = runTest {
        val repository = createRepository()

        val result = repository.refresh("Moscow")

        assertTrue(result.isSuccess)
    }

    private fun createRepository(): CitiesRepositoryImpl {
        val engine = MockEngine {
            respond(
                content = """{"items":[],"limit":20,"page":1,"total":0}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val httpClient = HttpClient(engine) {
            install(ContentNegotiation) {
                json()
            }
        }
        val networkConfig = NetworkConfig(
            baseUrl = "http://test/",
            defaultPageSize = 20,
            enableLogging = false,
        )
        return CitiesRepositoryImpl(
            remote = CitiesRemoteDataSource(httpClient, networkConfig),
            cacheCitiesBatchUseCase = CacheCitiesBatchUseCase(InMemoryCityDetailsProvider()),
            localDataSource = NoOpCitiesLocalDataSource(),
            networkConfig = networkConfig,
            dispatchers = DefaultDispatcherProvider(),
        )
    }
}
