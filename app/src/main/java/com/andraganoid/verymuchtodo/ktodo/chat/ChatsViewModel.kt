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


    private val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    val _newChat = MutableLiveData<Chat>(null)
    val newChat: LiveData<Chat>
        get() = _newChat

    suspend fun allUsers(): List<User> = dbRepository.allUsersClean()

    var currentChat: Chat? = null
    val messageText = ObservableField<String>()

    val allChats: LiveData<List<Chat>> = dbRepository.allChats().asLiveData()

    suspend fun updateCurrentChat(): Boolean {
        val um = getUserMap()
        currentChat?.messages?.forEach { message: Message ->
            message.isMyMsg = myUser.uid == message.from.uid
            message.from.imageUrl = um.get(message.from.uid)
        }
        _loaderVisibility.value = false
        messageText.set("")
        currentChat?.lastRead = System.currentTimeMillis()
        return true
    }

    suspend fun getUserMap(): HashMap<String, String?> {
        val userMap = hashMapOf<String, String?>()
        allUsers().forEach { user -> userMap.put(user.uid, user.imageUrl) }
        return userMap
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



