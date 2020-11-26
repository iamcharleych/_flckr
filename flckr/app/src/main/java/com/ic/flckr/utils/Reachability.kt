package com.ic.flckr.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Reachability : ConnectivityManager.NetworkCallback() {

    private lateinit var context: Context

    private val liveDataCallback = object : ActiveLiveDataCallback {
        override fun onLiveDataActive() {
            registerBroadCastReceiver()
        }

        override fun onLiveDataInactive() {
            unregisterBroadCastReceiver()
        }
    }

    val connectivityEventChannel: LiveData<Boolean>
        get() = connectivityLiveData

    private val connectivityLiveData = ActiveLiveData(liveDataCallback)

    fun init(context: Context) {
        this.context = context
    }

    fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.activeNetwork?.let { net ->
                val caps = cm.getNetworkCapabilities(net) ?: return false
                return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            }
        } else {
            return cm.activeNetworkInfo?.isConnectedOrConnecting ?: false
        }

        return false
    }

    fun isWiFiConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.activeNetwork?.let { net ->
                val caps = cm.getNetworkCapabilities(net) ?: return false
                return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        } else {
            return cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnected ?: false
        }

        return false
    }

    override fun onAvailable(network: Network) {
        connectivityLiveData.postValue(true)
    }

    private fun registerBroadCastReceiver() {
        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerNetworkCallback(request, this)
    }

    private fun unregisterBroadCastReceiver() {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(this)
    }

    private class ActiveLiveData(private val callback: ActiveLiveDataCallback): MutableLiveData<Boolean>() {

        override fun onInactive() {
            callback.onLiveDataInactive()
        }

        override fun onActive() {
            callback.onLiveDataActive()
        }
    }

    private interface ActiveLiveDataCallback {
        fun onLiveDataActive()
        fun onLiveDataInactive()
    }
}
