package com.example.podcastapp.other.connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class InternetConnectionObserver(private val context: Context) {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun observeInternetConnection(): Flow<Boolean> = callbackFlow {
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                launch {
                    send(isInternetAvailable())
                }
            }
        }

        context.registerReceiver(
            broadcastReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        trySend(isInternetAvailable())

        awaitClose { context.unregisterReceiver(broadcastReceiver) }
    }

    suspend fun isInternetAvailable(): Boolean = withContext(Dispatchers.IO) {
        try {
            val sock = Socket()
            sock.use {
                sock.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            }
            true
        } catch (e: IOException) {
            false
        }

//        val networkInfo = connectivityManager.activeNetworkInfo
//        return networkInfo != null && networkInfo.isConnected
    }
}