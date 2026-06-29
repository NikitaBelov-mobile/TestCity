package com.example.testcity.data.mapper

import com.example.testcity.data.remote.dto.CitiesPageDto
import com.example.testcity.data.remote.dto.CityDto
import com.example.testcity.domain.model.CitiesPage
import com.example.testcity.domain.model.City

fun CityDto.toDomain(): City = City(
    id = id,
    name = name,
    countryCode = country,
    population = pop,
)

fun CitiesPageDto.toDomain(): CitiesPage = CitiesPage(
    items = items.map { it.toDomain() },
    page = page,
    limit = limit,
    total = total,
)
