package com.andraganoid.verymuchtodo.ktodo.message

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.andraganoid.verymuchtodo.kmodel.Document
import com.andraganoid.verymuchtodo.kmodel.Message
import com.andraganoid.verymuchtodo.repository.DatabaseRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.util.myUser

class MessagesViewModel(private val dbRepository: DatabaseRepository, private val firestoreRepository: FirestoreRepository) : ViewModel() {

    val allMessages: LiveData<List<Message>> = dbRepository.allMessages().asLiveData()

    val messageText = ObservableField<String>()

    fun sendMessage() {
        if (messageText.get()!!.isNotEmpty()) {
            val timesstamp = System.currentTimeMillis()
            val message = Message(
                    text = messageText.get().toString(),
                    timestamp = timesstamp,
                    user = myUser,
                    id = "${myUser.uid}-$timesstamp")
            firestoreRepository.addDocument(Document(message))
        }
    }
}


