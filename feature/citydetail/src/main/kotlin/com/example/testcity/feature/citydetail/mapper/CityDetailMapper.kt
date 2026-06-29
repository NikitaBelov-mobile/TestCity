package com.example.testcity.feature.citydetail.mapper

import com.example.testcity.core.ui.mapper.CityUiMapper
import com.example.testcity.core.ui.model.CityUiModel
import com.example.testcity.domain.model.City
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun City.toUiModel(): CityUiModel = CityUiMapper.map(
    id = id,
    name = name,
    countryCode = countryCode,
    population = population,
)

object BrowserUrlBuilder {
    fun googleSearchUrl(query: String): String {
        val encoded = URLEncoder.encode(query, StandardCharsets.UTF_8.toString())
        return "https://www.google.com/search?q=$encoded"
    }
}
