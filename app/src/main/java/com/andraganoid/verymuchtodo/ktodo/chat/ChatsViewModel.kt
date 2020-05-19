package com.andraganoid.verymuchtodo.ktodo.chat

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.kmodel.Document
import com.andraganoid.verymuchtodo.kmodel.Message
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.repository.DatabaseRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.util.myUser
import kotlinx.coroutines.launch

class ChatsViewModel(private val dbRepository: DatabaseRepository, private val firestoreRepository: FirestoreRepository) : ViewModel() {

    internal val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    val _newChat = MutableLiveData<Chat>(null)
    val newChat: LiveData<Chat>
        get() = _newChat

    var allUsers: List<User> = listOf()
    var userMap = hashMapOf<String, String?>()
    var currentChat: Chat? = null
    val messageText = ObservableField<String>()


    suspend fun allChats(): LiveData<List<Chat>> {

        _loaderVisibility.postValue(false)
        allUsers = dbRepository.allUsersClean()
        allUsers.forEach { user ->
            userMap.put(user.uid, user.imageUrl
            )
        }
        return dbRepository.allChats().asLiveData()
    }

    fun sendMessage() {
        if (messageText.get()!!.isNotEmpty()) {
            _loaderVisibility.value = true
            val timestamp = System.currentTimeMillis()

            val message = Message(
                    text = messageText.get().toString(),
                    timestamp = timestamp,
                    from = myUser,
                    id = "${myUser.uid}-$timestamp")

            currentChat?.apply {
                val msgs = messages as ArrayList
                msgs.add(message)
                messages = msgs
                lastRead = timestamp
                lastEdit = timestamp
            }
            viewModelScope.launch {
                firestoreRepository.addDocument(Document(currentChat!!))
            }
        }
    }

}



