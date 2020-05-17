package com.andraganoid.verymuchtodo.ktodo.chat.chats

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.repository.DatabaseRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository

class ChatsViewModel(private val dbRepository: DatabaseRepository, private val firestoreRepository: FirestoreRepository) : ViewModel() {

    val _newChat = MutableLiveData<Chat>(null)
    val newChat: LiveData<Chat>
        get() = _newChat

    var allUsers: List<User> = listOf()

    suspend fun allChats(): LiveData<List<Chat>> {
        allUsers = dbRepository.allUsersClean()
        return dbRepository.allChats().asLiveData()
    }

    var currentChat: Chat? = null

    private val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    val messageText = ObservableField<String>()

}



