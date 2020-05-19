package com.andraganoid.verymuchtodo.repository

import android.util.Log
import com.andraganoid.verymuchtodo.database.dao.ChatDao
import com.andraganoid.verymuchtodo.database.dao.UserDao
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.kmodel.User
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val userDao: UserDao, private val chatDao: ChatDao) {

    fun allUsers(): Flow<List<User>> = userDao.allUsers()

    suspend fun allUsersClean(): List<User> = userDao.allUsersClean()

 fun allChats(): Flow<List<Chat>> {
        Log.d("XXXchatDao-1", chatDao.toString())
        return chatDao.allChats()
    }

    suspend fun updateTest(chat: List<Chat>):String {
        chatDao.updateChat(chat)
        return  "SENT"
    }

}

