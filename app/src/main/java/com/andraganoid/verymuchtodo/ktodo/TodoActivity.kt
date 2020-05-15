package com.andraganoid.verymuchtodo.ktodo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.ktodo.settings.SettingsDialogFragment
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import com.andraganoid.verymuchtodo.util.myUser
import com.andraganoid.verymuchtodo.util.networkStateChannel
import com.andraganoid.verymuchtodo.util.networkStatus
import kotlinx.android.synthetic.main.activity_todo_k.*
import kotlinx.coroutines.channels.consumeEach
import org.koin.android.ext.android.inject


class TodoActivity() : AppCompatActivity() {

    private val listenersRepository: ListenersRepository by inject()
    lateinit var todoNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_k)
        listenersRepository.setFirestoreListeners()
        setNavigationListener()
        networkListener()
        //  conn()
        toast(myUser.name.toString())
    }

    private fun networkListener() {
        lifecycleScope.launchWhenCreated {
            networkStateChannel.consumeEach {
              //  toast(it.toString())
               // lostNetworkIcon.isVisible = !it
            }
        }

       networkStatus.observe(this, Observer {
           Log.d("CCONN", "TODO-LIVEDATA "+it)
           toast(it.toString())
           lostNetworkIcon.isVisible = !it})


    }

    private fun setNavigationListener() {
        todoNavController = findNavController(R.id.todoFragmentLayout)
        bottomNavBar.setupWithNavController(todoNavController)
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
                getString(R.string.chat_frag_label) -> title = getString(R.string.chat)
            }
            todoTitle.text = title.toUpperCase()
            backIcon.isVisible = backArrow
            bottomNavBar.isVisible = bottomBar

        }
    }

    fun loaderVisibility(visibility: Boolean) {
        hideKeyboard()
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
        if (todoNavController.currentDestination?.label!!.equals(getString(R.string.stacks_frag_label))) {
            finishAffinity()
        }
        super.onBackPressed()
    }



    private fun toast(txt: String) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
    }

}


