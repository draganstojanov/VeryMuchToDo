package com.andraganoid.verymuchtodo.repository

import com.andraganoid.verymuchtodo.database.dao.ChatDao
import com.andraganoid.verymuchtodo.database.dao.MessageDao
import com.andraganoid.verymuchtodo.database.dao.UserDao
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.kmodel.User
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val userDao: UserDao, private val messageDao: MessageDao, private val chatDao: ChatDao) {

    fun allUsers(): Flow<List<User>> = userDao.allUsers()

  suspend  fun allUsersClean(): List<User> = userDao.allUsersClean()

  //  fun allMessages(): Flow<List<Message>> = messageDao.allMessages()

    fun allChats(): Flow<List<Chat>> = chatDao.allChats()


}

