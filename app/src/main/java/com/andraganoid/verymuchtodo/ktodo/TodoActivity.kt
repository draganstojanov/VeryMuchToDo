package com.andraganoid.verymuchtodo.ktodo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andraganoid.verymuchtodo.R
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class TodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_k)
        FirebaseAuth.getInstance().signOut()
        Timer().schedule(object : TimerTask() {
            override fun run() {//todo null
                onBackPressed()
            }
        }, 5000)



    }
}
