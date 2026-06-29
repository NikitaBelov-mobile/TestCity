package com.example.testcity.domain.usecase

import com.example.testcity.domain.fake.FakeNetworkStatusProvider
import com.example.testcity.domain.model.NetworkStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ObserveNetworkStatusUseCaseTest {

    @Test
    fun `invoke exposes provider status flow`() = runTest {
        val provider = FakeNetworkStatusProvider(NetworkStatus.Lost)
        val useCase = ObserveNetworkStatusUseCase(provider)

        assertEquals(NetworkStatus.Lost, useCase().first())
    }
}
