package com.andraganoid.verymuchtodo.util

import android.content.Context
import android.content.SharedPreferences

class Prefs
    (private val context: Context) {

    companion object {
        private const val PREF = "com.andraganoid.memoryfields.SHARED_PREFERENCES"
        private const val PREF_USERNAME = "prefUsername"
        private const val PREF_AUTOCOMPLETE_ITEMS = "prefAutocompleteItems"
    }

    private val sharedPreferences: SharedPreferences
        get() = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    fun saveUserName(userName: String) {
        sharedPreferences.edit().putString(PREF_USERNAME, userName).apply()
    }

    fun getUserName(): String? = sharedPreferences.getString(PREF_USERNAME, null)

    fun saveAutocompleteItemList(autocompleteList: MutableList<String>?) {
        sharedPreferences.edit().putStringSet(PREF_AUTOCOMPLETE_ITEMS, autocompleteList?.toMutableSet()).apply()
    }

    fun getAutocompleteIemList(): MutableList<String>? = (sharedPreferences.getStringSet(PREF_AUTOCOMPLETE_ITEMS, mutableSetOf())?.toMutableList())

}