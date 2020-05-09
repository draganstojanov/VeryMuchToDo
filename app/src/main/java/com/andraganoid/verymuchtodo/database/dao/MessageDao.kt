package com.andraganoid.verymuchtodo.database.dao

import androidx.room.*
import com.andraganoid.verymuchtodo.kmodel.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMessage(message: List<Message>)

    @Delete
    suspend fun deleteMessage(message: List<Message>)

    @Update
    suspend fun updateMessage(message: List<Message>)

    @Query("SELECT * FROM message_table")
    fun allMessages(): Flow<List<Message>>

}