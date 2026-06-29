package com.example.testcity.domain.usecase

import com.example.testcity.domain.model.NetworkStatus
import com.example.testcity.domain.repository.NetworkStatusProvider
import kotlinx.coroutines.flow.Flow

class ObserveNetworkStatusUseCase(
    private val networkStatusProvider: NetworkStatusProvider,
) {
    operator fun invoke(): Flow<NetworkStatus> = networkStatusProvider.status
}
