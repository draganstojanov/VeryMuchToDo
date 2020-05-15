package com.andraganoid.verymuchtodo.repository

import android.util.Log
import com.andraganoid.verymuchtodo.database.dao.ChatDao
import com.andraganoid.verymuchtodo.database.dao.MessageDao
import com.andraganoid.verymuchtodo.database.dao.UserDao
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.util.*
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListenersRepository(
        private val firebaseFirestore: FirebaseFirestore,
        private val userDao: UserDao,
        private val messageDao: MessageDao,
        private val chatDao: ChatDao)
    : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Default

    fun setFirestoreListeners() {

        firebaseFirestore.collection(COL_USER).addSnapshotListener { snapshots, e ->
            if (snapshots != null) {
                val users = arrayListOf<User>()
                for (doc in snapshots) {
                    users.add(doc.toObject(User::class.java))
                }
                Log.d("LLIISSTT-USER", users.toString())
                launch { userDao.saveUser(users as List<User>) }
            }
        }

//        firebaseFirestore.collection(COL_МESSAGE).addSnapshotListener { snapshots, e ->
//            val added = arrayListOf<Message>()
//            val updated = arrayListOf<Message>()
//            val deleted = arrayListOf<Message>()
//            for (doc in snapshots!!.documentChanges) {
//                when (doc.type) {
//                    DocumentChange.Type.ADDED -> added.add(doc.document.toObject(Message::class.java))
//                    DocumentChange.Type.MODIFIED -> updated.add(doc.document.toObject(Message::class.java))
//                    DocumentChange.Type.REMOVED -> deleted.add(doc.document.toObject(Message::class.java))
//                }
//            }
//            Log.d("LLIISSTT-MSG-ADDED", added.toString())
//            Log.d("LLIISSTT-MSG-UPDATED", updated.toString())
//            Log.d("LLIISSTT-MSG-DELETED", deleted.toString())
//            launch {
//                if (added.isNotEmpty()) {
//                    messageDao.saveMessage(added as List<Message>)
//                }
//                if (updated.isNotEmpty()) {
//                    messageDao.updateMessage(updated as List<Message>)
//                }
//                if (deleted.isNotEmpty()) {
//                    messageDao.deleteMessage(deleted as List<Message>)
//                }
//            }
//        }

        firebaseFirestore.collection(COL_CHAT)
                .whereArrayContains(FIELD_MEMBERS, listOf(CHAT_ALL_MEMBERS, myUser.uid))
                .addSnapshotListener { snapshots, e ->
                    val added = arrayListOf<Chat>()
                    val updated = arrayListOf<Chat>()
                    val deleted = arrayListOf<Chat>()
                    for (doc in snapshots!!.documentChanges) {
                        when (doc.type) {
                            DocumentChange.Type.ADDED -> added.add(doc.document.toObject(Chat::class.java))
                            DocumentChange.Type.MODIFIED -> updated.add(doc.document.toObject(Chat::class.java))
                            DocumentChange.Type.REMOVED -> deleted.add(doc.document.toObject(Chat::class.java))
                        }
                    }
                    Log.d("LLIISSTT-MSG-ADDED", added.toString())
                    Log.d("LLIISSTT-MSG-UPDATED", updated.toString())
                    Log.d("LLIISSTT-MSG-DELETED", deleted.toString())
                    launch {
                        if (added.isNotEmpty()) {
                            chatDao.saveChat(added as List<Chat>)
                        }
                        if (updated.isNotEmpty()) {
                            chatDao.updateChat(updated as List<Chat>)
                        }
                        if (deleted.isNotEmpty()) {
                            chatDao.deleteChat(deleted as List<Chat>)
                        }
                    }
                }

    }


}