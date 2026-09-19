package work.stojanov.todo.app

import android.app.Application
import work.stojanov.todo.BuildConfig
import work.stojanov.todo.di.Modules
import work.stojanov.todo.util.timber.TodoDebugTree
import work.stojanov.todo.util.timber.TodoReleaseTree
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