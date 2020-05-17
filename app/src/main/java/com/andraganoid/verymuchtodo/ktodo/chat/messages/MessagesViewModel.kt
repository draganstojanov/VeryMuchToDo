package com.andraganoid.verymuchtodo.ktodo.chat.messages

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.kmodel.Document
import com.andraganoid.verymuchtodo.kmodel.Message
import com.andraganoid.verymuchtodo.repository.DatabaseRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.util.myUser
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MessagesViewModel(private val dbRepository: DatabaseRepository, private val firestoreRepository: FirestoreRepository) : ViewModel() {


    var currentChat: Chat? = null

    private val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    val allChats: LiveData<List<Chat>> = dbRepository.allChats().asLiveData()

    private val _allMessages = MutableLiveData<List<Message>>()
    val allMessages: LiveData<List<Message>>
        get() = _allMessages

    fun getAllMessages(chats: List<Chat>) {
        _loaderVisibility.value = false

        viewModelScope.launch {
            val mList = currentChat?.messages
            val users = dbRepository.allUsers().first()

            mList?.forEach { message ->
                message.from = users.filter { user -> user.uid.equals(message.from.uid) }[0]
                message.isMyMsg = myUser.uid.equals(message.from.uid)
            }
            _allMessages.value = mList
        }
    }


    val messageText = ObservableField<String>()


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
            firestoreRepository.addDocument(Document(currentChat!!))

        }
    }
}


