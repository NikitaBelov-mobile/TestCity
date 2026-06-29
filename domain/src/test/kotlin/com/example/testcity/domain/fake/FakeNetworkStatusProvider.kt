package com.example.testcity.domain.fake

import com.example.testcity.domain.model.NetworkStatus
import com.example.testcity.domain.repository.NetworkStatusProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeNetworkStatusProvider(
    initial: NetworkStatus = NetworkStatus.Available,
) : NetworkStatusProvider {
    private val statusFlow = MutableStateFlow(initial)

    override val status: Flow<NetworkStatus> = statusFlow

    fun emit(status: NetworkStatus) {
        statusFlow.value = status
    }
}
