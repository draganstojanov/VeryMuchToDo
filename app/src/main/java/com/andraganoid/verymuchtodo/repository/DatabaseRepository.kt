package com.andraganoid.verymuchtodo.repository

import androidx.lifecycle.LiveData
import com.andraganoid.verymuchtodo.database.dao.UserDao
import com.andraganoid.verymuchtodo.kmodel.User
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val userDao: UserDao) {


    fun allUsers(): Flow<List<User>> = userDao.allUsers()


}

