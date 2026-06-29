package com.example.testcity.core.ui.mapper

import com.example.testcity.core.common.CountryNameMapper
import com.example.testcity.core.ui.model.CityUiModel
import java.text.NumberFormat
import java.util.Locale

object CityUiMapper {
    private val populationFormat = NumberFormat.getInstance(Locale("ru", "RU"))

    fun map(
        id: Long,
        name: String,
        countryCode: String,
        population: Long,
    ): CityUiModel = CityUiModel(
        id = id,
        name = name,
        country = CountryNameMapper.map(countryCode),
        population = population,
    )

    fun formatPopulation(population: Long): String =
        populationFormat.format(population).replace('\u00A0', ' ')
}
