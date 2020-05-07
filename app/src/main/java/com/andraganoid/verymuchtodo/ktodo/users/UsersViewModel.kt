package com.andraganoid.verymuchtodo.ktodo.users

import androidx.lifecycle.*
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.repository.DatabaseRepository
import kotlinx.coroutines.launch

class UsersViewModel(private val dbRepository: DatabaseRepository) : ViewModel() {

//
//    val _allUsers = MutableLiveData<List<User>>()
//    val allUsers:LiveData<List<User>>
//        get() =_allUsers

  val allUsers: LiveData<List<User>> = dbRepository.allUsers().asLiveData()

}
