package com.andraganoid.verymuchtodo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andraganoid.verymuchtodo.database.dao.MessageDao
import com.andraganoid.verymuchtodo.database.dao.UserDao
import com.andraganoid.verymuchtodo.kmodel.Message
import com.andraganoid.verymuchtodo.kmodel.User


@Database(entities = [User::class, Message::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun messageDao(): MessageDao


}