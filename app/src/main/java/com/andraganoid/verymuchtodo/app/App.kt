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
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(Modules.appModule)
        }
        timberInit()
    }

    private fun timberInit() {
        if (BuildConfig.DEBUG) {
            Timber.plant(TodoDebugTree())
        } else {
            Timber.plant(TodoReleaseTree())
        }
    }

}