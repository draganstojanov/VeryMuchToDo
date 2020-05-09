package com.andraganoid.verymuchtodo.util

import android.content.Context
import android.content.SharedPreferences
import com.andraganoid.verymuchtodo.kmodel.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Preferences(private val context: Context) {

    private val PREF_NAME = "com.andraganoid.verymuchtodo.SHARED_PREFERENCES"
    private val PREF_USER = "pref_user"
//    private val PREF_LEVELS = "pref_levels"
//    private val PREF_GAME_PLAY_SETTINGS = "pref_gsme_play_settings"


    val sharedPreferences: SharedPreferences
        get() = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    fun saveMyUser(user: User) {
        sharedPreferences.edit().putString(PREF_USER, Gson().toJson(user)).apply()
        myUser=user
    }

    fun getMyUser(): User {
        val prefSettings = sharedPreferences.getString(PREF_USER, null)
        return if(prefSettings!=null) Gson().fromJson(prefSettings, object : TypeToken<User>() {}.type) else User()
    }


//    fun saveTypes(types: ArrayList<Type>) {
//        val prefType = Gson().toJson(types)
//        sharedPreferences.edit().putString(PREF_TYPES, prefType).apply()
//    }
//
//    fun getTypes(): ArrayList<Type> {
//        val prefType = sharedPreferences.getString(PREF_TYPES, null)
//        return if (prefType != null) Gson().fromJson(prefType, object : TypeToken<ArrayList<Type>>() {}.type) else arrayListOf()
//    }
//
//    fun saveLevels(levels: HashMap<String, List<Level>>) {
//        val preflevel = Gson().toJson(levels)
//        sharedPreferences.edit().putString(PREF_LEVELS, preflevel).apply()
//    }
//
//    fun getLevels(): HashMap<String, List<Level>> {
//        val preflevel = sharedPreferences.getString(PREF_LEVELS, null)
//        return if (preflevel != null) Gson().fromJson(preflevel, object : TypeToken<HashMap<String, List<Level>>>() {}.type) else hashMapOf()
//    }
//
//    fun saveGamePlaySettings(gamePlaySettings: GamePlaySettings) {
//        val prefSettings = Gson().toJson(gamePlaySettings)
//        sharedPreferences.edit().putString(PREF_GAME_PLAY_SETTINGS, prefSettings).apply()
//    }
//
//    fun getGamePlaySettings(): GamePlaySettings {
//        val prefSettings = sharedPreferences.getString(PREF_GAME_PLAY_SETTINGS, null)
//        return Gson().fromJson(prefSettings, object : TypeToken<GamePlaySettings>() {}.type) ?: GamePlaySettings()
//    }

}