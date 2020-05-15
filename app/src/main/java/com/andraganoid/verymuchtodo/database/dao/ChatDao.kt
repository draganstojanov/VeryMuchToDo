package com.andraganoid.verymuchtodo.database.dao

import androidx.room.*
import com.andraganoid.verymuchtodo.kmodel.Chat
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveChat(chat: List<Chat>)

    @Delete
    suspend fun deleteChat(chat: List<Chat>)

    @Update
    suspend fun updateChat(chat: List<Chat>)

    @Query("SELECT * FROM chat_table")
    fun allChats(): Flow<List<Chat>>
}