package com.andraganoid.verymuchtodo.app

import android.app.Application
import com.andraganoid.verymuchtodo.BuildConfig
import com.andraganoid.verymuchtodo.di.Modules
import com.andraganoid.verymuchtodo.util.timber.TodoDebugTree
import com.andraganoid.verymuchtodo.util.timber.TodoReleaseTree
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(BuildConfig.CRASHLYTICS)
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