package com.andraganoid.verymuchtodo.ktodo

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.ktodo.settings.SettingsDialogFragment
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import kotlinx.android.synthetic.main.activity_todo_k.*
import org.koin.android.ext.android.inject

class TodoActivity() : AppCompatActivity() {

    private val listenersRepository: ListenersRepository by inject()

    lateinit var todoNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_k)
        listenersRepository.setFirestoreListeners()
        todoNavController = findNavController(R.id.todoFragmentLayout)
        bottomNavBar.setupWithNavController(todoNavController)
        setNavigationListener()
    }

    private fun setNavigationListener() {
        todoNavController.addOnDestinationChangedListener { _, destination, _ ->
            var title = ""
            var backArrow = false
            var bottomBar = true
            when (destination.label) {

                getString(R.string.profile_frag_label) -> {
                    backArrow = true
                    bottomBar = false
                }
                getString(R.string.stacks_frag_label) -> title = getString(R.string.stacks)
                getString(R.string.users_frag_label) -> title = getString(R.string.users)
                getString(R.string.messages_frag_label) -> title = getString(R.string.messages)
                getString(R.string.map_frag_label) -> title = getString(R.string.map)
            }
            todoTitle.text = title.toUpperCase()
            backIcon.isVisible = backArrow
            bottomNavBar.isVisible = bottomBar

        }
    }

    fun loaderVisibility(visibility: Boolean) {
        todoLoader.isVisible = visibility
    }

    fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    fun menuClicked(view: View) {
        val settingsDialogFragment = SettingsDialogFragment();
        settingsDialogFragment.show(supportFragmentManager, null)
    }

    fun backClicked(view: View) {
        onBackPressed()
    }


    override fun onBackPressed() {
        if (todoNavController.currentDestination?.label!!.equals("ListsFragment")) {
            finishAffinity()
        }
        super.onBackPressed()
    }

}


