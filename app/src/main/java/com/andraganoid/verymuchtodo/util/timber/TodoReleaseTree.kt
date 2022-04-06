package com.andraganoid.verymuchtodo.util.timber

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class TodoReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR && t != null) {
            Firebase.crashlytics.recordException(t)
        }
    }
}