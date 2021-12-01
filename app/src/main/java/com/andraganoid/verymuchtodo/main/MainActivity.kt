package com.andraganoid.verymuchtodo.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.ActivityMainBinding
import com.andraganoid.verymuchtodo.ui.msgDialog.MessageDialogData
import com.andraganoid.verymuchtodo.util.ARGS_DIALOG_DATA
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

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    override fun onStart() {
        super.onStart()
        navController = findNavController(R.id.fragmentLayout)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            Toast.makeText(this, destination.label, Toast.LENGTH_SHORT).show()

        }
        insetController = ViewCompat.getWindowInsetsController(binding.root)!!
    }


    private fun setup() {
        viewModel.loaderVisibility.observe(this, { loaderVisibility -> binding.loader.isVisible = loaderVisibility })
        viewModel.message.observe(this, { message -> bottomToast(message) })
        binding.backArrow.setOnClickListener { navController.popBackStack() }
        binding.settingsBtn.setOnClickListener { navController.navigate(R.id.settingsFragment) }
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

    fun showSettings(settingsVisibility: Boolean) {
        binding.settingsBtn.isVisible = settingsVisibility
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

    fun messageDialog(dialogData: MessageDialogData) {
        navController.navigate(R.id.messageDialog, bundleOf(ARGS_DIALOG_DATA to dialogData))
    }

    fun bottomToast(msg: Any?) {
        messageDialog(MessageDialogData(toast = msg.toString()))
    }


}