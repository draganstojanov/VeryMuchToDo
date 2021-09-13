package com.andraganoid.verymuchtodo.util

import timber.log.Timber

open class TodoDebugTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String? {
        return super.createStackElementTag(element) + "LINE: " + element.lineNumber + " "
    }

}
