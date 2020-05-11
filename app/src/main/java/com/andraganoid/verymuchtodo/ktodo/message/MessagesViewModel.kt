package com.andraganoid.verymuchtodo.ktodo.message

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.andraganoid.verymuchtodo.kmodel.Document
import com.andraganoid.verymuchtodo.kmodel.Message
import com.andraganoid.verymuchtodo.repository.DatabaseRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.util.myUser
import kotlinx.coroutines.flow.first

class MessagesViewModel(private val dbRepository: DatabaseRepository, private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

     val allMessages = dbRepository.allMessages().asLiveData()

    suspend fun getAllMessages(): List<Message> {
        _loaderVisibility.value = false

        val mList = dbRepository.allMessages().first()
        val users = dbRepository.allUsers().first()

        mList.forEach { message ->
            message.user = users.filter { user -> user.uid.equals(message.user.uid) }[0]
            message.isMyMsg = myUser.uid.equals(message.user.uid)
        }
        return mList
    }


    val messageText = ObservableField<String>()


    fun sendMessage() {
        if (messageText.get()!!.isNotEmpty()) {
            _loaderVisibility.value = true
            val timestamp = System.currentTimeMillis()
            val message = Message(
                    text = messageText.get().toString(),
                    timestamp = timestamp,
                    user = myUser,
                    id = "${myUser.uid}-$timestamp")
            firestoreRepository.addDocument(Document(message))
        }
    }
}


