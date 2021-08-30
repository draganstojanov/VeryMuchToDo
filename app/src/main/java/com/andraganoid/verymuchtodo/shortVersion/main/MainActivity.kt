package com.andraganoid.verymuchtodo.shortVersion.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.ActivityMainBinding
import com.andraganoid.verymuchtodo.shortVersion.util.bottomToast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    //   lateinit var mainNavController: NavController
    private lateinit var binding: ActivityMainBinding
    private val viewModel:MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //   setContentView(R.layout.activity_main)
        //   setNavigationListener()
        //   myUser = preferences.getMyUser()
        //   networkListener()
        setup()
        window.statusBarColor = ContextCompat.getColor(this, R.color.backgroundColor)
    }

    private fun setup() {
     viewModel.loaderVisibility.observe(this, { loaderVisibility -> binding.loader.isVisible = loaderVisibility })
//      viewModel.message.observe(this, { message -> Snackbar.make(binding.root, message.toString(), Snackbar.LENGTH_LONG).show() })

        viewModel.message.observe(this, { message -> bottomToast(message) })

    }

//    private fun setNavigationListener() {
//        mainNavController = findNavController(R.id.fragmentLayout)
//        mainNavController.addOnDestinationChangedListener { _, destination, _ ->
//            //    val backArrow = destination.label!!.equals(getString(R.string.register_frag_label))
////            if (destination.label!!.equals(getString(R.string.register_frag_label))) {
////                backArrow = true
////            }
//            //    backIcon.isVisible = backArrow
//
//          //  backIcon.isVisible = destination.label!!.equals(getString(R.string.register_frag_label))
//        }
//    }

//    fun loaderVisibility(visibility: Boolean) {
//        loader.isVisible = visibility
//    }
//
//
//    fun backClicked(view: View) {
//        mainNavController.popBackStack()
//    }



}