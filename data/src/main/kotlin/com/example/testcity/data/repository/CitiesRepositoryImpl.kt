package com.example.testcity.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testcity.core.common.DispatcherProvider
import com.example.testcity.core.network.NetworkConfig
import com.example.testcity.data.local.CitiesLocalDataSource
import com.example.testcity.data.paging.CitiesPagingSource
import com.example.testcity.data.remote.CitiesRemoteDataSource
import com.example.testcity.domain.model.City
import com.example.testcity.domain.repository.CitiesRepository
import com.example.testcity.domain.usecase.CacheCitiesBatchUseCase
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.atomic.AtomicReference

class CitiesRepositoryImpl(
    private val remote: CitiesRemoteDataSource,
    private val cacheCitiesBatchUseCase: CacheCitiesBatchUseCase,
    private val localDataSource: CitiesLocalDataSource,
    private val networkConfig: NetworkConfig,
    private val dispatchers: DispatcherProvider,
) : CitiesRepository {

    private val activePagingSource = AtomicReference<CitiesPagingSource?>(null)

    override fun pagingCities(query: String): Flow<PagingData<City>> {
        return Pager(
            config = PagingConfig(
                pageSize = networkConfig.defaultPageSize,
                initialLoadSize = networkConfig.defaultPageSize,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                CitiesPagingSource(
                    remote = remote,
                    cacheBatch = { cities ->
                        cacheCitiesBatchUseCase(cities)
                        localDataSource.saveCities(cities)
                    },
                    query = { query },
                    pageSize = networkConfig.defaultPageSize,
                    dispatchers = dispatchers,
                ).also { activePagingSource.set(it) }
            },
        ).flow
    }

    override suspend fun refresh(query: String): Result<Unit> {
        activePagingSource.get()?.invalidate()
        return Result.success(Unit)
    }
}
