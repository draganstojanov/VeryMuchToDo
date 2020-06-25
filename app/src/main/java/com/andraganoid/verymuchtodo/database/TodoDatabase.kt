package com.andraganoid.verymuchtodo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andraganoid.verymuchtodo.database.dao.ChatDao
import com.andraganoid.verymuchtodo.database.dao.StackDao
import com.andraganoid.verymuchtodo.database.dao.UserDao
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.kmodel.Stack
import com.andraganoid.verymuchtodo.kmodel.User


@Database(entities = [User::class, Chat::class, Stack::class], version = 10, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun chatDao(): ChatDao

    abstract fun stackDao(): StackDao


}