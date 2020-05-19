package com.andraganoid.verymuchtodo.repository

import android.util.Log
import com.andraganoid.verymuchtodo.database.dao.ChatDao
import com.andraganoid.verymuchtodo.database.dao.UserDao
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.util.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListenersRepository(
        private val firebaseFirestore: FirebaseFirestore,
        private val userDao: UserDao,
        private val chatDao: ChatDao,
        private val databaseRepository: DatabaseRepository)

    : CoroutineScope {

    var userListener: ListenerRegistration? = null
    var chatListener: ListenerRegistration? = null

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Default

    fun setFirestoreListeners() {
        Log.d("XXXchatDao-2", chatDao.toString())
        Log.d("TODOSTART", "LISTENERS")

        userListener = firebaseFirestore.collection(COL_USER).addSnapshotListener { snapshots, e ->
            if (snapshots != null) {
                val users = arrayListOf<User>()
                for (doc in snapshots) {
                    users.add(doc.toObject(User::class.java))
                }
                Log.d("LLIISSTT-USER", users.toString())
                launch { userDao.saveUser(users as List<User>) }
            }
        }



        chatListener = firebaseFirestore.collection(COL_CHAT)
                .whereArrayContainsAny(FIELD_MEMBERS, listOf(CHAT_ALL_MEMBERS, myUser.uid))
                .addSnapshotListener { snapshots, e ->


                    if (snapshots != null) {
                        val chats = arrayListOf<Chat>()
                        for (doc in snapshots) {
                            chats.add(doc.toObject(Chat::class.java))
                        }
                        Log.d("LLIISSTT-USER", chats.toString())
                        launch { chatDao.saveChat(chats as List<Chat>) }
                    }




//                    Log.d("XXXchatDao-3", chatDao.toString())
//                    Log.d("LLIISSTT-E", e.toString())
//
//
//                    val added = mutableListOf<Chat>()
//                    val updated = mutableListOf<Chat>()
//                    val deleted = mutableListOf<Chat>()
//                    for (doc in snapshots!!.documentChanges) {
//                        when (doc.type) {
//                            DocumentChange.Type.ADDED -> added.add(doc.document.toObject(Chat::class.java))
//                            DocumentChange.Type.MODIFIED -> updated.add(doc.document.toObject(Chat::class.java))
//                            DocumentChange.Type.REMOVED -> deleted.add(doc.document.toObject(Chat::class.java))
//                        }
//                    }
//                    Log.d("LLIISSTT-MSG-ADDED", added.toString())
//                    Log.d("LLIISSTT-MSG-UPDATED", updated.toString())
//                    Log.d("LLIISSTT-MSG-DELETED", deleted.toString())
//                    launch(Dispatchers.Main) {
//                        if (added.isNotEmpty()) {
//                            Log.d("XXXchatDao-4", chatDao.toString())
//                            chatDao.saveChat(added)
//                        }
//                        if (updated.isNotEmpty()) {
//                            Log.d("XXXchatDao-5", chatDao.toString())
//                            Log.d("LLIISSTT-U", updated.toString())
//
//                            chatDao.updateChat(updated)
//
//
//
//                        //   println (databaseRepository.updateTest(updated))
//                        }
//                        if (deleted.isNotEmpty()) {
//                            chatDao.deleteChat(deleted)
//                        }
//                    }
                }

    }


}