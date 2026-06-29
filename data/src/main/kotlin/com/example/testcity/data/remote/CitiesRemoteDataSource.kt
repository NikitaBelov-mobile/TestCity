package com.example.testcity.data.remote

import com.example.testcity.core.network.NetworkConfig
import com.example.testcity.data.mapper.toDomain
import com.example.testcity.data.remote.dto.CitiesPageDto
import com.example.testcity.domain.model.CitiesPage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CitiesRemoteDataSource(
    private val httpClient: HttpClient,
    private val networkConfig: NetworkConfig,
) {
    suspend fun fetchCities(
        query: String,
        page: Int,
        limit: Int = networkConfig.defaultPageSize,
    ): CitiesPage {
        return httpClient.get("api/cities") {
            if (query.isNotBlank()) {
                parameter("query", query)
            }
            parameter("page", page)
            parameter("limit", limit)
        }.body<CitiesPageDto>().toDomain()
    }
}
