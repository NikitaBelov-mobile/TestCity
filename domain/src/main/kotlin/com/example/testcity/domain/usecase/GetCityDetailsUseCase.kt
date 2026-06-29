package com.example.testcity.domain.usecase

import com.example.testcity.domain.model.AppError
import com.example.testcity.domain.model.AppErrorException
import com.example.testcity.domain.model.City
import com.example.testcity.domain.repository.CityDetailsProvider

class GetCityDetailsUseCase(
    private val cityDetailsProvider: CityDetailsProvider,
) {
    suspend operator fun invoke(cityId: Long): Result<City> {
        val city = cityDetailsProvider.getById(cityId)
        return if (city != null) {
            Result.success(city)
        } else {
            Result.failure(AppErrorException(AppError.CityNotFound))
        }
    }
}
