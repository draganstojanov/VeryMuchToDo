package com.andraganoid.verymuchtodo.repository

import android.util.Log
import com.andraganoid.verymuchtodo.database.dao.ChatDao
import com.andraganoid.verymuchtodo.database.dao.UserDao
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.util.*
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListenersRepository(
        private val firebaseFirestore: FirebaseFirestore,
        private val userDao: UserDao,
        private val chatDao: ChatDao) {

    var userListener: ListenerRegistration? = null
    var chatListener: ListenerRegistration? = null

    fun setFirestoreListeners() {

        userListener = firebaseFirestore.collection(COL_USER).addSnapshotListener { snapshots, e ->
            if (snapshots != null) {
                val users = arrayListOf<User>()
                for (doc in snapshots) {
                    users.add(doc.toObject(User::class.java))
                }
                GlobalScope.launch { userDao.saveUser(users as List<User>) }
            }
        }



        chatListener = firebaseFirestore.collection(COL_CHAT)
                .whereArrayContainsAny(FIELD_MEMBERS, listOf(CHAT_ALL_MEMBERS, myUser.uid))
                .addSnapshotListener { snapshots, e ->

                    val added = mutableListOf<Chat>()
                    val updated = mutableListOf<Chat>()
                    val deleted = mutableListOf<Chat>()
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
                    GlobalScope.launch(Dispatchers.Default) {
                        if (added.isNotEmpty()) {
                            chatDao.saveChat(added)
                        }
                        if (updated.isNotEmpty()) {
                            chatDao.updateChat(updated)
                        }
                        if (deleted.isNotEmpty()) {
                            chatDao.deleteChat(deleted)
                        }
                    }
                }

    }


}