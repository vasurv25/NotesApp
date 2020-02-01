package com.example.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.notesapp.network.NoConnectivityException

/**
 * utilities for network
 */

private const val TAG: String = "NetworkUtils"

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        if (capabilities.run { hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) }
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return true
        }
    } else {
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
    return false
}

/**
 * get retrofit no network error
 */
fun getRetrofitError(throwable: Throwable): String? {
    return (throwable as? NoConnectivityException)?.localizedMessage
}