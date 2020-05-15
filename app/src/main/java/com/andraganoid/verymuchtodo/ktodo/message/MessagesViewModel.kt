package com.andraganoid.verymuchtodo.ktodo.message

import android.util.Log
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

    val allChats: LiveData<List<Chat>>  = dbRepository.allChats().asLiveData()

    private val _allMessages = MutableLiveData<List<Message>>()
    val allMessages: LiveData<List<Message>>
        get() = _allMessages

    fun getAllMessages(chats: List<Chat>) {
        _loaderVisibility.value = false


        val xcurrentChat = chats.filter { chat ->
            chat.id.equals(currentChat?.id)
        }.get(0)

        Log.d("CCCUUURR-1", currentChat!!.id)
        Log.d("CCCUUURR-2", xcurrentChat.id)


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

    // val allMessages = dbRepository.allMessages().asLiveData()

    //   suspend fun getAllMessages(): ArrayList<Message>? {
    //       _loaderVisibility.value = false
//
//        //  val mList = dbRepository.allMessages().first()
//        val mList = allMessages.value as ArrayList
//        val users = dbRepository.allUsers().first()
//
//        mList.forEach { message ->
//            message.from = users.filter { user -> user.uid.equals(message.from.uid) }[0]
//            message.isMyMsg = myUser.uid.equals(message.from.uid)
//        }
//        return mList
    //   }


    val messageText = ObservableField<String>()


    fun sendMessage() {
        if (messageText.get()!!.isNotEmpty()) {
          //  _loaderVisibility.value = true
            val timestamp = System.currentTimeMillis()

//            val message = Message(
//                    text = messageText.get().toString(),
//                    timestamp = timestamp,
//                    from = myUser,
//                    id = "${myUser.uid}-$timestamp")
//
//

            val message = Message(
                    text = messageText.get().toString(),
                    timestamp = timestamp,
                    from = myUser,
                    id = "${myUser.uid}-$timestamp")

            currentChat?.apply {
                val msgs = messages as ArrayList
                msgs.add(message)
                messages = msgs
                /// messages.toMutableList().add(message)
                lastRead = timestamp
            }
            firestoreRepository.addDocument(Document(currentChat!!))

            //    firestoreRepository.addDocument(Document(message))
        }
    }
}


