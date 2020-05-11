package com.andraganoid.verymuchtodo.kmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
data class Message(
        val text: String = "",
        val timestamp: Long = 0,
        var user: User = User(),
        @PrimaryKey
        val id: String = "") {
    var isMyMsg: Boolean = false
}

