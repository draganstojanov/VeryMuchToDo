package com.andraganoid.verymuchtodo.repository

import com.andraganoid.verymuchtodo.database.dao.MessageDao
import com.andraganoid.verymuchtodo.database.dao.UserDao
import com.andraganoid.verymuchtodo.kmodel.Message
import com.andraganoid.verymuchtodo.kmodel.User
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val userDao: UserDao, private val messageDao: MessageDao) {

    fun allUsers(): Flow<List<User>> = userDao.allUsers()

   // suspend fun allUsersClean(): List<User> = userDao.allUsersClean()

    fun allMessages(): Flow<List<Message>> = messageDao.allMessages()


}

