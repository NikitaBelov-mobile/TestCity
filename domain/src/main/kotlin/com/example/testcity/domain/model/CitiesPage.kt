package com.example.testcity.domain.model

data class CitiesPage(
    val items: List<City>,
    val page: Int,
    val limit: Int,
    val total: Int,
) {
    val hasMore: Boolean get() = page * limit < total
}
