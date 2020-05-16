package com.andraganoid.verymuchtodo.kmodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "message_table")
data class Message(
        val text: String = "",
        val timestamp: Long = 0,
        var from: User = User(),
        @PrimaryKey
        val id: String = "") : Serializable {
    var isMyMsg: Boolean = false
}

