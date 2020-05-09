package com.andraganoid.verymuchtodo.ktodo.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.repository.DatabaseRepository

class UsersViewModel(private val dbRepository: DatabaseRepository) : ViewModel() {
    val allUsers: LiveData<List<User>> = dbRepository.allUsers().asLiveData()
}
