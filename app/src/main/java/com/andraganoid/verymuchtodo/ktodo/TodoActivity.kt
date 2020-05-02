package com.andraganoid.verymuchtodo.ktodo

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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

    fun loaderState(loaderState: Boolean) {
        todoLoader.isVisible = loaderState
    }

    fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    fun todoMenuClicked(view: View) {
        val settingsDialogFragment = SettingsDialogFragment();
        settingsDialogFragment.show(supportFragmentManager,null)
    }


}
