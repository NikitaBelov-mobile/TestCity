package com.example.testcity.domain.repository

import androidx.paging.PagingData
import com.example.testcity.domain.model.City
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun pagingCities(query: String): Flow<PagingData<City>>
    suspend fun refresh(query: String): Result<Unit>
}
