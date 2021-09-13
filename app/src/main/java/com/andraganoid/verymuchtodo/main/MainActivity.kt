package com.andraganoid.verymuchtodo.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.databinding.ActivityMainBinding
import com.andraganoid.verymuchtodo.util.bottomToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()


//       window.statusBarColor = ContextCompat.getColor(this, R.color.backgroundColor)
//        val windowInsetController = ViewCompat.getWindowInsetsController(window.decorView)
//        windowInsetController?.isAppearanceLightStatusBars = true // or false

    }

    private fun setup() {
        viewModel.loaderVisibility.observe(this, { loaderVisibility -> binding.loader.isVisible = loaderVisibility })
        viewModel.message.observe(this, { message -> bottomToast(message) })
    }

    fun setTitle(title:String){
        binding.topBar.text=title
    }

}