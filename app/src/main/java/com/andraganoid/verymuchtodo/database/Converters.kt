package com.andraganoid.verymuchtodo.database

import androidx.room.TypeConverter
import com.andraganoid.verymuchtodo.kmodel.*
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromUser(user: User): String = gson.toJson(user)

    @TypeConverter
    fun toUser(user: String) = gson.fromJson(user, User::class.java)

    @TypeConverter
    fun fromChat(chat: Chat): String = gson.toJson(chat)

    @TypeConverter
    fun toChat(chat: String) = gson.fromJson(chat, Chat::class.java)

    @TypeConverter
    fun fromMembers(members: List<String>): String = gson.toJson(members)

    @TypeConverter
    fun toMembers(members: String): List<String> = gson.fromJson(members, object : TypeToken<List<String>>() {}.type)

    @TypeConverter
    fun fromMessages(messages: List<Message>): String = gson.toJson(messages)

    @TypeConverter
    fun toMessages(messages: String): List<Message> = gson.fromJson(messages, object : TypeToken<List<Message>>() {}.type)

    @TypeConverter
    fun fromStack(stack: Stack): String = gson.toJson(stack)

    @TypeConverter
    fun toStack(stack: String) = gson.fromJson(stack, Stack::class.java)

    @TypeConverter
    fun fromTodos(todos: List<Todo>): String = gson.toJson(todos)

    @TypeConverter
    fun toTodos(todos: String): List<Todo> = gson.fromJson(todos, object : TypeToken<List<Todo>>() {}.type)


}