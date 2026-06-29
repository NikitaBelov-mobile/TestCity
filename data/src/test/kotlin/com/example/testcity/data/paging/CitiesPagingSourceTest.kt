package com.example.testcity.data.paging

import androidx.paging.PagingSource
import com.example.testcity.core.common.DefaultDispatcherProvider
import com.example.testcity.core.network.NetworkConfig
import com.example.testcity.data.local.NoOpCitiesLocalDataSource
import com.example.testcity.data.remote.CitiesRemoteDataSource
import com.example.testcity.data.repository.InMemoryCityDetailsProvider
import com.example.testcity.domain.model.City
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CitiesPagingSourceTest {

    private val cityJson = """
        {
          "items": [
            {
              "id": 1,
              "name": "Moscow",
              "country": "RU",
              "lat": 55.75,
              "lon": 37.61,
              "pop": 12000000
            }
          ],
          "limit": 20,
          "page": 1,
          "total": 1
        }
    """.trimIndent()

    @Test
    fun `load caches cities through use case and local stub`() = runTest {
        val provider = InMemoryCityDetailsProvider()
        var localSaved: List<City>? = null
        val localDataSource = object : com.example.testcity.data.local.CitiesLocalDataSource {
            override suspend fun saveCities(cities: List<City>) {
                localSaved = cities
            }

            override suspend fun getCityById(id: Long): City? = null
        }
        val source = createPagingSource(
            cacheBatch = { cities ->
                CacheCitiesBatchUseCase(provider)(cities)
                localDataSource.saveCities(cities)
            },
        )

        val result = source.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false,
            ),
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        assertEquals(
            City(id = 1, name = "Moscow", countryCode = "RU", population = 12_000_000),
            provider.getById(1),
        )
        assertEquals(1, localSaved?.size)
    }

    private fun createPagingSource(
        cacheBatch: suspend (List<City>) -> Unit,
    ): CitiesPagingSource {
        val engine = MockEngine {
            respond(
                content = cityJson,
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
        return CitiesPagingSource(
            remote = CitiesRemoteDataSource(httpClient, networkConfig),
            cacheBatch = cacheBatch,
            query = { "" },
            pageSize = 20,
            dispatchers = DefaultDispatcherProvider(),
        )
    }
}
