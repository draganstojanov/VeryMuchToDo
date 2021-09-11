package com.andraganoid.verymuchtodo.util

import android.content.Context

class Prefs
    (private val context: Context) {

    companion object {
        private const val PREF_NAME = "com.andraganoid.memoryfields.SHARED_PREFERENCES"
        private const val PREF_TYPES = "prefTypes"
        private const val PREF_BOARDS = "prefBoards"
        private const val PREF_LATEST_HI_SCORE = "prefLatestHiScore"
        private const val PREF_THEME = "prefTheme"
    }

//    private val sharedPreferences: SharedPreferences
//        get() = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//
//    fun saveTypes(types: ArrayList<Type>) {
//        val prefType = gson.toJson(types)
//        sharedPreferences.edit().putString(PREF_TYPES, prefType).apply()
//    }
//
//    fun getTypes(): ArrayList<Type> {
//        val prefType = sharedPreferences.getString(PREF_TYPES, null)
//        return if (prefType != null) gson.fromJson(prefType, object : TypeToken<ArrayList<Type>>() {}.type) else arrayListOf()
//    }
//
//    fun saveBoards(boards: ArrayList<Board>) {
//        val prefBoards = gson.toJson(boards)
//        sharedPreferences.edit().putString(PREF_BOARDS, prefBoards).apply()
//    }
//
//    fun getBoards(): ArrayList<Board> {
//        val prefBoards = sharedPreferences.getString(PREF_BOARDS, null)
//        return if (prefBoards != null) gson.fromJson(prefBoards, object : TypeToken<ArrayList<Board>>() {}.type) else arrayListOf()
//    }
//
//    fun saveLatestHighScore(hs: Status?) {
//        val hsType = gson.toJson(hs)
//        sharedPreferences.edit().putString(PREF_LATEST_HI_SCORE, hsType).apply()
//    }
//
//    fun getLatestHighScore(): Status? {
//        val hsType = sharedPreferences.getString(PREF_LATEST_HI_SCORE, null)
//        // return if (hsType != null) gson.fromJson(hsType, object : Score() {}.type) else null//todo return null???
//        //  return if (hsType != null)   gson.fromJson(hsType, Score::class.java) else null//todo return null???
//        return gson.fromJson(hsType, Status::class.java)//todo return null???
//    }
//
//    fun saveTheme(theme: Theme) {
//        val themeType = gson.toJson(theme)
//        sharedPreferences.edit().putString(PREF_THEME, themeType).apply()
//    }
//
//    fun getTheme(): Theme {
//        val themeType = sharedPreferences.getString(PREF_THEME, null)
//        return gson.fromJson(themeType, Theme::class.java) ?: Theme()
//    }


}