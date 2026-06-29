package com.example.testcity.feature.cities.mapper

import com.example.testcity.core.ui.mapper.CityUiMapper
import com.example.testcity.core.ui.model.CityUiModel
import com.example.testcity.domain.model.City

fun City.toUiModel(): CityUiModel = CityUiMapper.map(
    id = id,
    name = name,
    countryCode = countryCode,
    population = population,
)
