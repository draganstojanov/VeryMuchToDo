package com.andraganoid.verymuchtodo.database

import androidx.room.TypeConverter
import com.andraganoid.verymuchtodo.kmodel.User
import com.google.gson.Gson

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromUser(user: User):String=gson.toJson(user)

    @TypeConverter
    fun toUser(user:String)=gson.fromJson(user, User::class.java)

//    @TypeConverter
//    fun fromFieldsList(fList: ArrayList<Field>): String = gson.toJson(fList)
//
//    @TypeConverter
//    fun toFieldsList(fList: String): ArrayList<Field> = gson.fromJson(fList, object : TypeToken<ArrayList<Field>>() {}.type)
//
//    @TypeConverter
//    fun fromScore(score: Score): String = gson.toJson(score)
//
//    @TypeConverter
//    fun toScore(score: String) = gson.fromJson(score, Score::class.java)
//
//    @TypeConverter
//    fun fromGamePlaySettings(gamePlaySettings: GamePlaySettings): String = gson.toJson(gamePlaySettings)
//
//    @TypeConverter
//    fun toGamePlaySettings(gamePlaySettings: String) = gson.fromJson(gamePlaySettings, GamePlaySettings::class.java)


}