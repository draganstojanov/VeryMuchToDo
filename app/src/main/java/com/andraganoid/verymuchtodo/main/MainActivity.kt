package com.andraganoid.verymuchtodo.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.ActivityMainBinding
import com.andraganoid.verymuchtodo.util.bottomToast
import com.andraganoid.verymuchtodo.util.keyboardState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private lateinit var insetController: WindowInsetsControllerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }


    private fun setup() {
        viewModel.loaderVisibility.observe(this, { loaderVisibility -> binding.loader.isVisible = loaderVisibility })
        viewModel.message.observe(this, { message -> bottomToast(message) })
        binding.backArrow.setOnClickListener { findNavController(R.id.fragmentLayout).popBackStack() }
        insetController = ViewCompat.getWindowInsetsController(binding.root)!!
        lifecycleScope.launch(Dispatchers.Main) {
            keyboardState.collect { state ->
                if (state) showKeyboard() else hideKeyboard()
            }
        }
    }

    fun showTitle(title: String) {
        binding.topBar.text = title
    }

    fun showArrow(arrowVisibility: Boolean) {
        binding.backArrow.isVisible = arrowVisibility
    }

    private fun showKeyboard() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(300)
            insetController.show(WindowInsetsCompat.Type.ime())
        }
    }

    private fun hideKeyboard() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(300)
            insetController.hide(WindowInsetsCompat.Type.ime())
        }
    }

}