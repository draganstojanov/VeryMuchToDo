package com.andraganoid.verymuchtodo

import android.app.Application
import com.andraganoid.verymuchtodo.util.di.Modules.appModule
import com.andraganoid.verymuchtodo.old.util.timber.TodoDebugTree
import com.andraganoid.verymuchtodo.old.util.timber.TodoReleaseTree
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

@ExperimentalCoroutinesApi
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
            androidLogger()

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