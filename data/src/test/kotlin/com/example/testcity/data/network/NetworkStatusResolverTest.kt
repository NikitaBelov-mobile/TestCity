package com.example.testcity.data.network

import com.example.testcity.domain.model.NetworkStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkStatusResolverTest {

    @Test
    fun `returns lost when no active network`() {
        assertEquals(
            NetworkStatus.Lost,
            AndroidNetworkStatusProvider.resolveNetworkStatus(
                activeNetwork = null,
                capabilities = null,
            ),
        )
    }
}
