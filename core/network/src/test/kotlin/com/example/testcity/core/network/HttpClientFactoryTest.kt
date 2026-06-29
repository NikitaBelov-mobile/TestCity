package com.example.testcity.core.network

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import org.junit.Assert.assertEquals
import org.junit.Test
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond

class HttpClientFactoryTest {

    @Test
    fun `client parses cities page json`() = runTest {
        val mockEngine = MockEngine { request ->
            assertEquals("/api/cities", request.url.encodedPath)
            assertEquals("1", request.url.parameters["page"])
            assertEquals("20", request.url.parameters["limit"])
            respond(
                content = CITIES_JSON,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val client = HttpClientFactory(
            NetworkConfig(baseUrl = "http://test.example.com/", defaultPageSize = 20),
        ).create(mockEngine)

        val response = client.get("api/cities") {
            parameter("page", 1)
            parameter("limit", 20)
        }.body<CitiesPageResponse>()

        assertEquals(1, response.page)
        assertEquals(20, response.limit)
        assertEquals(177738, response.total)
        assertEquals(1, response.items.size)
        assertEquals("Shanghai", response.items.first().name)
    }

    @Serializable
    private data class CitiesPageResponse(
        val items: List<CityItemResponse>,
        val limit: Int,
        val page: Int,
        val total: Int,
    )

    @Serializable
    private data class CityItemResponse(
        val id: Long,
        val name: String,
        val country: String,
        val lat: Double,
        val lon: Double,
        val pop: Long,
    )

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
