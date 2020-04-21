package com.andraganoid.verymuchtodo.ktodo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.andraganoid.verymuchtodo.R
import kotlinx.android.synthetic.main.activity_todo_k.*

class TodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_k)





        // Finding the Navigation Controller
        var navController = findNavController(R.id.todoFragmentLayout)

        // Setting Navigation Controller with the BottomNavigationView
        bottomNavBar.setupWithNavController(navController)

    }

    fun todoMenuClicked(view: View) {
        Toast.makeText(this, "MENU CLICKED", Toast.LENGTH_LONG).show()
        findNavController(R.id.todoFragmentLayout).navigate(R.id.profileFragment)


    }
}
