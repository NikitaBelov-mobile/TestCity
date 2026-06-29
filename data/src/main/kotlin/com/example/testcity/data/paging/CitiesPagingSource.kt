package com.example.testcity.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testcity.core.common.DispatcherProvider
import com.example.testcity.data.mapper.ThrowableMapper
import com.example.testcity.data.remote.CitiesRemoteDataSource
import com.example.testcity.domain.model.AppErrorException
import com.example.testcity.domain.model.City
import kotlinx.coroutines.withContext

class CitiesPagingSource(
    private val remote: CitiesRemoteDataSource,
    private val cacheBatch: suspend (List<City>) -> Unit,
    private val query: () -> String,
    private val pageSize: Int,
    private val dispatchers: DispatcherProvider,
    private val throwableMapper: ThrowableMapper = ThrowableMapper(),
) : PagingSource<Int, City>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> =
        withContext(dispatchers.io) {
            val page = params.key ?: 1
            try {
                val response = remote.fetchCities(
                    query = query(),
                    page = page,
                    limit = pageSize,
                )
                cacheBatch(response.items)
                LoadResult.Page(
                    data = response.items,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (response.hasMore) page + 1 else null,
                )
            } catch (exception: Exception) {
                LoadResult.Error(AppErrorException(throwableMapper.map(exception)))
            }
        }

    override fun getRefreshKey(state: PagingState<Int, City>): Int? =
        state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
}
