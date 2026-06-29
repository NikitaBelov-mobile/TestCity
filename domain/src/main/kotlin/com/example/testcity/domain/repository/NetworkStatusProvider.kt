package com.example.testcity.domain.repository

import com.example.testcity.domain.model.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface NetworkStatusProvider {
    val status: Flow<NetworkStatus>
}
