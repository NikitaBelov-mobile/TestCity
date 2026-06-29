package com.example.testcity.domain.model

sealed interface NetworkStatus {
    data object Available : NetworkStatus
    data object Lost : NetworkStatus
}
