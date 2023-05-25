package com.carefer.core.utils.network

class NetworkUtils {

    companion object {
        fun isNetworkConnected(state: ConnectivityProvider.NetworkState): Boolean {
            return (state as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
        }
    }
}