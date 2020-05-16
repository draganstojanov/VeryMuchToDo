package com.andraganoid.verymuchtodo.ktodo.chat.chats

import androidx.lifecycle.*
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.repository.DatabaseRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChatsViewModel(private val dbRepository: DatabaseRepository, private val firestoreRepository: FirestoreRepository) : ViewModel() {


    val allChats = dbRepository.allChats().asLiveData()
    var allChatsList: List<Chat>? = listOf()

//    fun allChats(): LiveData<List<Chat>> {
//        val chats = dbRepository.allChats().asLiveData()
//        allChats = chats.value
//
//        Log.d("cchhaa",allChats.toString())
//
//        return chats
//    }

    private val _allUsers = MutableLiveData<List<User>>()
    val allUsers: LiveData<List<User>>
        get() = _allUsers

    fun allUsers() {
        viewModelScope.launch { _allUsers.value = dbRepository.allUsers().first() }
    }

     val _newChat = MutableLiveData<Chat>(null)
    val newChat: LiveData<Chat>
        get() = _newChat


}



