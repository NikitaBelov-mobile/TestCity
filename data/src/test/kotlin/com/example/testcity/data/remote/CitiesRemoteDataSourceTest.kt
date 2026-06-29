package com.example.testcity.data.remote

import com.example.testcity.core.network.HttpClientFactory
import com.example.testcity.core.network.NetworkConfig
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CitiesRemoteDataSourceTest {

    @Test
    fun `fetchCities parses response and maps to domain`() = runTest {
        val mockEngine = MockEngine { request ->
            assertEquals("shanghai", request.url.parameters["query"])
            assertEquals("2", request.url.parameters["page"])
            respond(
                content = CITIES_JSON,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val httpClient = HttpClientFactory(
            NetworkConfig(baseUrl = "http://test.example.com/", defaultPageSize = 20),
        ).create(mockEngine)

        val dataSource = CitiesRemoteDataSource(httpClient, NetworkConfig("http://test/", 20))
        val page = dataSource.fetchCities(query = "shanghai", page = 2)

        assertEquals(1, page.page)
        assertEquals("Shanghai", page.items.first().name)
        assertEquals("CN", page.items.first().countryCode)
        assertEquals(24_874_500, page.items.first().population)
    }

    private companion object {
        val CITIES_JSON = """
            {
              "items": [
                {
                  "id": 1796236,
                  "name": "Shanghai",
                  "country": "CN",
                  "lat": 31.22222,
                  "lon": 121.45806,
                  "pop": 24874500
                }
              ],
              "limit": 20,
              "page": 1,
              "total": 177738
            }
        """.trimIndent()
    }
}
