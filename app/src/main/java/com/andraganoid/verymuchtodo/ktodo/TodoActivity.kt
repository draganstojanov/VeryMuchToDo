package com.andraganoid.verymuchtodo.ktodo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.ktodo.settings.SettingsDialogFragment
import kotlinx.android.synthetic.main.activity_todo_k.*

class TodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_k)

        val navController = findNavController(R.id.todoFragmentLayout)
        bottomNavBar.setupWithNavController(navController)
    }

    fun todoMenuClicked(view: View) {
        val setttingsDialogFragment = SettingsDialogFragment();
        setttingsDialogFragment.show(supportFragmentManager, setttingsDialogFragment.tag)
    }

}
