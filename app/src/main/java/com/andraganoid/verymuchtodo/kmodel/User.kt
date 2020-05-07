package com.andraganoid.verymuchtodo.kmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
        @PrimaryKey
        val uid: String = "",
        var name: String? = "",
        var email: String? = "",
        var imageUrl: String? = null,
        val accessLevel: Int? = 0)