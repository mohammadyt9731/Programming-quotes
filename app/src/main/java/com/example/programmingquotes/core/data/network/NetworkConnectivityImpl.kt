package com.example.programmingquotes.core.data.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject

class NetworkConnectivityImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : NetworkConnectivity {

    override fun isNetworkConnected(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false

            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            @Suppress("DEPRECATION")
            return connectivityManager.activeNetworkInfo.let { activeNetworkInfo ->
                activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
            }
        }
    }
}
