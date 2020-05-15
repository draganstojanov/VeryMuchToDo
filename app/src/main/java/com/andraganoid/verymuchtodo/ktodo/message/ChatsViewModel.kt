package com.andraganoid.verymuchtodo.ktodo.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.andraganoid.verymuchtodo.repository.DatabaseRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import kotlinx.coroutines.flow.first

class ChatsViewModel(private val dbRepository: DatabaseRepository, private val firestoreRepository: FirestoreRepository) : ViewModel() {

//lateinit var usersList:ArrayList<User>
//    init {
//        viewModelScope.launch { usersList= dbRepository.allUsers().first() as ArrayList<User>}
//    }

//    private val _loaderVisibility = MutableLiveData<Boolean>()
//    val loaderVisibility: LiveData<Boolean>
//        get() = _loaderVisibility

    val allChats = dbRepository.allChats().asLiveData()

 suspend fun allUsers() = dbRepository.allUsers().first()


//    suspend fun getAllMessages(): ArrayList<Message>? {
//        _loaderVisibility.value = false
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
//    }


//    val messageText = ObservableField<String>()


//    fun sendMessage() {
//        if (messageText.get()!!.isNotEmpty()) {
//            _loaderVisibility.value = true
//            val timestamp = System.currentTimeMillis()
//            val message = Message(
//                    text = messageText.get().toString(),
//                    timestamp = timestamp,
//                    from = myUser,
//                    id = "${myUser.uid}-$timestamp")
//            firestoreRepository.addDocument(Document(message))
//        }
//    }
}



