package com.andraganoid.verymuchtodo.repository

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.andraganoid.verymuchtodo.database.dao.UserDao
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.util.COL_USER
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListenersRepository(private val firebaseFirestore: FirebaseFirestore, private val userDao: UserDao): CoroutineScope {


    init {
       // setFirestoreListeners()
    }

   fun setFirestoreListeners() {

       Log.d("LLIISSTT","1")


        firebaseFirestore.collection(COL_USER).addSnapshotListener { values, e ->
            Log.d("LLIISSTT","2"+e?.message)
            if (values != null) {
                val users = arrayListOf<User>()
                for (doc in values) {
                    users.add(doc.toObject(User::class.java))
                }

              launch { userDao.saveUser(users as List<User>) }
            }

        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job() +Dispatchers.Default



}