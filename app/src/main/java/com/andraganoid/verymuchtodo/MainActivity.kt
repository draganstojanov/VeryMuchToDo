package com.andraganoid.verymuchtodo

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.andraganoid.verymuchtodo.base.BaseActivity
import com.andraganoid.verymuchtodo.util.Preferences
import com.andraganoid.verymuchtodo.util.myUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.android.ext.android.inject

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class MainActivity : BaseActivity() {

    lateinit var mainNavController: NavController
    private val preferences: Preferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNavigationListener()
        myUser = preferences.getMyUser()
        networkListener()
        window.statusBarColor = ContextCompat.getColor(this, R.color.backgorundColor)
    }

    private fun setNavigationListener() {
        mainNavController = findNavController(R.id.fragmentLayout)
        mainNavController.addOnDestinationChangedListener { _, destination, _ ->
            //    val backArrow = destination.label!!.equals(getString(R.string.register_frag_label))
//            if (destination.label!!.equals(getString(R.string.register_frag_label))) {
//                backArrow = true
//            }
            //    backIcon.isVisible = backArrow

            backIcon.isVisible = destination.label!!.equals(getString(R.string.register_frag_label))
        }
    }

    fun loaderVisibility(visibility: Boolean) {
        loader.isVisible = visibility
    }


    fun backClicked(view: View) {
        mainNavController.popBackStack()
    }

}



