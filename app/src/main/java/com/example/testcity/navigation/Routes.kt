package com.example.testcity.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoute

@Serializable
data object CitiesRoute : NavRoute

@Serializable
data class CityDetailRoute(
    val cityId: Long,
) : NavRoute
