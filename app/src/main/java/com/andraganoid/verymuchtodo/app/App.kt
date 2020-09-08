package com.andraganoid.verymuchtodo.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import com.andraganoid.verymuchtodo.BuildConfig
import com.andraganoid.verymuchtodo.di.Modules
import com.andraganoid.verymuchtodo.util.TodoDebugTree
import com.andraganoid.verymuchtodo.util.TodoReleaseTree
import com.andraganoid.verymuchtodo.util._networkStateFlow
import kotlinx.coroutines.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class App : Application(), CoroutineScope {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(Modules.appModule)
        }
        conn()
        timberInit()
    }

    private fun timberInit() {
        if (BuildConfig.DEBUG) {
            Timber.plant(TodoDebugTree())
        } else {
            Timber.plant(TodoReleaseTree())
        }
    }


    private fun conn() {
        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // network available
                Log.d("CCONN", "AVAILABLE")
                launch {
                    //  networkStateChannel.send(true)
                    _networkStateFlow.value = true
                }

                //  _networkStatus.postValue(true)

            }

            override fun onLost(network: Network) {
                // network unavailable
                Log.d("CCONN", "UNAVAILABLE")
                launch {
                    //  networkStateChannel.send(false)
                    _networkStateFlow.value = false
                }


                // _networkStatus.postValue(false)

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