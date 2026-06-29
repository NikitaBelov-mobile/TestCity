package com.example.testcity.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.example.testcity.domain.model.NetworkStatus
import com.example.testcity.domain.repository.NetworkStatusProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class AndroidNetworkStatusProvider(
    context: Context,
) : NetworkStatusProvider {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val status: Flow<NetworkStatus> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(resolveStatus())
            }

            override fun onLost(network: Network) {
                trySend(resolveStatus())
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities,
            ) {
                trySend(resolveStatus())
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        trySend(resolveStatus())
        connectivityManager.registerNetworkCallback(request, callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()

    internal fun resolveStatus(): NetworkStatus = resolveNetworkStatus(
        activeNetwork = connectivityManager.activeNetwork,
        capabilities = connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)
        },
    )

    internal companion object {
        fun resolveNetworkStatus(
            activeNetwork: Network?,
            capabilities: NetworkCapabilities?,
        ): NetworkStatus = when {
            activeNetwork == null || capabilities == null -> NetworkStatus.Lost
            !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> NetworkStatus.Lost
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) -> NetworkStatus.Lost
            else -> NetworkStatus.Available
        }
    }
}
