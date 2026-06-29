package com.example.testcity.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CitiesPageDto(
    val items: List<CityDto>,
    val limit: Int,
    val page: Int,
    val total: Int,
)

@Serializable
data class CityDto(
    val id: Long,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val pop: Long,
)
