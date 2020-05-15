package com.andraganoid.verymuchtodo.kmodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "chat_table")
data class Chat(
        @PrimaryKey
        val id: String = "",
        val members: List<String> = arrayListOf(),
        var messages: List<Message> = arrayListOf(),
        var name: String = "",
        var iconUrl: String? = null,
        var lastRead: Long = 0
) : Serializable