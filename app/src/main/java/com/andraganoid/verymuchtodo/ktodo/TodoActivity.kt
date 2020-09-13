package com.andraganoid.verymuchtodo.ktodo

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.base.BaseActivity
import com.andraganoid.verymuchtodo.ktodo.settings.SettingsDialogFragment
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import kotlinx.android.synthetic.main.activity_todo_k.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.android.ext.android.inject
import java.util.*


@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class TodoActivity() : BaseActivity() {

    private val listenersRepository: ListenersRepository by inject()
    lateinit var todoNavController: NavController
 //   private var bottomValue: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_k)
        listenersRepository.setFirestoreListeners()
        setNavigationListener()
        networkListener()
        errorMessageListener()
     //   bottomBarToggleListener()
    //    bottomValue = getBottomValue()
    }

//    fun getBottomValue(): Int {
//        val rect = Rect()
//        todoRoot.getWindowVisibleDisplayFrame(rect)
//        return rect.bottom
//    }

//    private fun bottomBarToggleListener() {TODO listener???
//        todoRoot.viewTreeObserver.addOnGlobalLayoutListener {
//            val tempBottomValue = getBottomValue()
//            if (bottomValue - tempBottomValue > 100) {
//                hideBottomBar()
//            } else {
//                showBottomBar()
//            }
//        }
//    }

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
                getString(R.string.messages_frag_label) -> backArrow = true
                getString(R.string.map_frag_label) -> title = getString(R.string.map)
                getString(R.string.chat_frag_label) -> title = getString(R.string.chats)
            }
            todoTitle.text = title.toUpperCase(Locale.ROOT)
            backIcon.isVisible = backArrow
            bottomNavBar.isVisible = bottomBar

        }
    }

    fun loaderVisibility(visibility: Boolean) {
        hideKeyboard()
        todoLoader.isVisible = visibility
    }

    fun menuClicked(view: View) {
        val settingsDialogFragment = SettingsDialogFragment();
        settingsDialogFragment.show(supportFragmentManager, null)
    }

    fun backClicked(view: View) {
        hideKeyboard()
        onBackPressed()
    }

    override fun onBackPressed() {
        if (todoNavController.currentDestination?.label!!.equals(getString(R.string.stacks_frag_label))) {
            finishAffinity()
        }
        super.onBackPressed()
    }

    fun setTitle(title: String) {
        todoTitle.text = title
    }

    fun hideBottomBar() {
        bottomNavBar.isVisible = false
    }

    fun showBottomBar() {
        bottomNavBar.isVisible = true
    }

}


