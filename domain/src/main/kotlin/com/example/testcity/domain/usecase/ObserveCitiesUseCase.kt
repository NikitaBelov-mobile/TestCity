package com.example.testcity.domain.usecase

import androidx.paging.PagingData
import com.example.testcity.domain.SEARCH_DEBOUNCE_MS
import com.example.testcity.domain.model.City
import com.example.testcity.domain.repository.CitiesRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(FlowPreview::class)
class ObserveCitiesUseCase(
    private val citiesRepository: CitiesRepository,
) {
    operator fun invoke(query: Flow<String>): Flow<PagingData<City>> =
        query
            .debounce(SEARCH_DEBOUNCE_MS)
            .distinctUntilChanged()
            .flatMapLatest { citiesRepository.pagingCities(it) }
}
