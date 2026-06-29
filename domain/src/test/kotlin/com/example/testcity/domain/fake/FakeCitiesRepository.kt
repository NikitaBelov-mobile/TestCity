package com.example.testcity.domain.fake

import androidx.paging.PagingData
import com.example.testcity.domain.model.City
import com.example.testcity.domain.repository.CitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCitiesRepository : CitiesRepository {
    val pagingQueries = mutableListOf<String>()
    var refreshQuery: String? = null

    override fun pagingCities(query: String): Flow<PagingData<City>> {
        pagingQueries.add(query)
        return flowOf(PagingData.empty())
    }

    override suspend fun refresh(query: String): Result<Unit> {
        refreshQuery = query
        return Result.success(Unit)
    }
}
