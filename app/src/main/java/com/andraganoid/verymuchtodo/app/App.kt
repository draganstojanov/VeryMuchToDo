package com.andraganoid.verymuchtodo.app

import android.app.Application
import com.andraganoid.verymuchtodo.BuildConfig
import com.andraganoid.verymuchtodo.di.Modules
import com.andraganoid.verymuchtodo.util.TodoDebugTree
import com.andraganoid.verymuchtodo.util.TodoReleaseTree
import kotlinx.coroutines.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@ExperimentalCoroutinesApi
class App : Application()
 //   , CoroutineScope
{

    override fun onCreate() {
        super.onCreate()
    //    FirebaseApp.initializeApp(applicationContext)
        startKoin {
            androidContext(this@App)
            modules(Modules.appModule)
        }
     //   conn()
        timberInit()
    }

    private fun timberInit() {
        if (BuildConfig.DEBUG) {
            Timber.plant(TodoDebugTree())
        } else {
            Timber.plant(TodoReleaseTree())
        }
    }


//    private fun conn() {
//        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
//            override fun onAvailable(network: Network) {
//                launch {
//                    _networkStateFlow.value = true
//                }
//            }
//
//            override fun onLost(network: Network) {
//                launch {
//                    _networkStateFlow.value = false
//                }
//            }
//
//            override fun onUnavailable() {
//                super.onUnavailable()
//            }
//        }
//
//        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            connectivityManager.registerDefaultNetworkCallback(networkCallback)
//        } else {
//            val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
//            connectivityManager.registerNetworkCallback(request, networkCallback)
//        }
//
//
//    }
//
//    override val coroutineContext: CoroutineContext
//        get() = Job() + Dispatchers.Default


}