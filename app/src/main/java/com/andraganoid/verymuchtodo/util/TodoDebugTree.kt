package com.andraganoid.verymuchtodo.util

import timber.log.Timber

open class TodoDebugTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String? {
        return super.createStackElementTag(element) + "LINE: " + element.lineNumber + " "
    }

//    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//        if (priority == Log.ERROR && t != null) {
//            // FirebaseCrashlytics.getInstance().log(tag+"->"+message)
//            FirebaseCrashlytics.getInstance().recordException(t)
//        }
//        Log.println(priority, tag, message)
//    }
}
