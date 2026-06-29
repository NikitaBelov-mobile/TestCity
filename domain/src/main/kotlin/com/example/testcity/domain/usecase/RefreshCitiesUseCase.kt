package com.example.testcity.domain.usecase

import com.example.testcity.domain.repository.CitiesRepository

class RefreshCitiesUseCase(
    private val citiesRepository: CitiesRepository,
) {
    suspend operator fun invoke(query: String): Result<Unit> = citiesRepository.refresh(query)
}
