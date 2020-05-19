package com.andraganoid.verymuchtodo.ktodo.chat.chats

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

class ChatsViewModel(private val dbRepository: DatabaseRepository, private val firestoreRepository: FirestoreRepository) : ViewModel() {

    val _newChat = MutableLiveData<Chat>(null)
    val newChat: LiveData<Chat>
        get() = _newChat

    var allUsers: List<User> = listOf()
    var userMap = hashMapOf<String, String?>()

   suspend fun allChats(): LiveData<List<Chat>> {

        Log.d("XXXchatDao-99","allChats")

        _loaderVisibility.postValue(false)
   allUsers = dbRepository.allUsersClean()
        allUsers.forEach { user ->
            userMap.put(user.uid, user.imageUrl
            )
        }
        return dbRepository.allChats().asLiveData()
    }

    var currentChat: Chat? = null

    private val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    val messageText = ObservableField<String>()

    fun sendMessage() {

        if (messageText.get()!!.isNotEmpty()) {

        //    _loaderVisibility.value = true
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
//viewModelScope.launch {

    firestoreRepository.addDocument(Document(currentChat!!))
//}


        }
    }

}



