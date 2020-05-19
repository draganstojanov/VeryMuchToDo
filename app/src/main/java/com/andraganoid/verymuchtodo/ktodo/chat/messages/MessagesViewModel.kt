package com.andraganoid.verymuchtodo.ktodo.chat.messages

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.kmodel.Document
import com.andraganoid.verymuchtodo.kmodel.Message
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.repository.DatabaseRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.util.myUser

class MessagesViewModel(private val dbRepository: DatabaseRepository, private val firestoreRepository: FirestoreRepository) : ViewModel() {

//
//    var currentChat: Chat? = null
//
//    private val _loaderVisibility = MutableLiveData<Boolean>()
//    val loaderVisibility: LiveData<Boolean>
//        get() = _loaderVisibility
//
//    val allChats: LiveData<List<Chat>> = dbRepository.allChats().asLiveData()
//
//    private val _allMessages = MutableLiveData<List<Message>>()
//    val allMessages: LiveData<List<Message>>
//        get() = _allMessages
//
//    fun getAllMessages(chats: List<Chat>) {
//        _loaderVisibility.value = false
//
//        viewModelScope.launch {
//            val mList = currentChat?.messages
//            val users = dbRepository.allUsers().first()
//
//            mList?.forEach { message ->
//                message.from = users.filter { user -> user.uid.equals(message.from.uid) }[0]
//                message.isMyMsg = myUser.uid.equals(message.from.uid)
//            }
//            _allMessages.value = mList
//        }
//    }
//
//
//    val messageText = ObservableField<String>()
//
//
//    fun sendMessage() {
//        if (messageText.get()!!.isNotEmpty()) {
//            _loaderVisibility.value = true
//            val timestamp = System.currentTimeMillis()
//
//            val message = Message(
//                    text = messageText.get().toString(),
//                    timestamp = timestamp,
//                    from = myUser,
//                    id = "${myUser.uid}-$timestamp")
//
//            currentChat?.apply {
//                val msgs = messages as ArrayList
//                msgs.add(message)
//                messages = msgs
//                lastRead = timestamp
//                lastEdit = timestamp
//            }
//            firestoreRepository.addDocument(Document(currentChat!!))
//
//        }
//    }


    val _newChat = MutableLiveData<Chat>(null)
    val newChat: LiveData<Chat>
        get() = _newChat

    var allUsers: List<User> = listOf()
    var userMap = hashMapOf<String, String?>()




    suspend fun allChats(): LiveData<List<Chat>> {
        Log.d("LLOADER","201")
        _loaderVisibility.postValue(false)
        allUsers = dbRepository.allUsersClean()
        Log.d("LLOADER","202")
        allUsers.forEach { user ->
            userMap.put(user.uid, user.imageUrl
            )
        }
        Log.d("LLOADER","203")
        return dbRepository.allChats().asLiveData()
    }

    var currentChat: Chat? = null

    private val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    val messageText = ObservableField<String>()

    fun sendMessage() {

        if (messageText.get()!!.isNotEmpty()) {

            Log.d("LLOADER","1")

            _loaderVisibility.value = true
            val timestamp = System.currentTimeMillis()

            val message = Message(
                    text = messageText.get().toString(),
                    timestamp = timestamp,
                    from = myUser,
                    id = "${myUser.uid}-$timestamp")
            Log.d("LLOADER","2")
            currentChat?.apply {
                val msgs = messages as ArrayList
                msgs.add(message)
                messages = msgs
                lastRead = timestamp
                lastEdit = timestamp
            }
            Log.d("LLOADER","3")

            //  Log.d("LLOADER",currentChat.toString())


            firestoreRepository.addDocument(Document(currentChat!!))

        }
    }
}


