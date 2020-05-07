package com.andraganoid.verymuchtodo.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import com.andraganoid.verymuchtodo.di.Modules
import com.andraganoid.verymuchtodo.util.networkStateChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import kotlin.coroutines.CoroutineContext

class App : Application(),CoroutineScope {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(Modules.appModule)
        }
        conn()
    }


    fun conn() {

        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network?) {
                // network available
                Log.d("CCONN", "AVAILABLE")
                launch {networkStateChannel.send(true)  }
                //   networkStateChannel.close()
             //  networkStateChannel.broadcast(capacity = Channel.CONFLATED)
            }

            override fun onLost(network: Network?) {
                // network unavailable
                Log.d("CCONN", "UNAVAILABLE")
              launch {networkStateChannel.send(false)  }
               // networkStateChannel.close()
            }
        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }


    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Default


}