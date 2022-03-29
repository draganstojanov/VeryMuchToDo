package com.andraganoid.verymuchtodo.main

import android.os.Bundle
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
import com.andraganoid.verymuchtodo.databinding.CalculatorLayoutBinding
import com.andraganoid.verymuchtodo.ui.custom.TopModalNEW
import com.andraganoid.verymuchtodo.ui.msgDialog.MessageDialogData
import com.andraganoid.verymuchtodo.util.ARGS_DIALOG_DATA
import com.andraganoid.verymuchtodo.util.calculatorState
import com.andraganoid.verymuchtodo.util.getKeyboardState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
        setObservers()
    }

    override fun onStart() {
        super.onStart()
        insetController = ViewCompat.getWindowInsetsController(binding.root)!!
        navController = findNavController(R.id.fragmentLayout)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.stackFragment -> {
                    showTitle(getString(R.string.todo))
                    showArrow(false)
                    showSettings(true)
                }
                R.id.todoListFragment -> {
                    showTitle("")
                    showArrow(true)
                    showSettings(true)
                }
                R.id.settingsFragment -> {
                    showTitle(getString(R.string.settings))
                    showArrow(true)
                    showSettings(false)
                }
                R.id.autocompleteEditFragment -> {
                    showTitle(getString(R.string.edit_autocomplete))
                    showArrow(true)
                    showSettings(false)
                }
            }
        }
    }

    private fun setup() {

        binding.backArrow.setOnClickListener { navController.popBackStack() }
        binding.settingsBtn.setOnClickListener { navController.navigate(R.id.settingsFragment) }
        binding.calculatorBtn.setOnClickListener {
            // navController.navigate(R.id.calculatorDialog)

            lifecycleScope.launch(Dispatchers.Main) { calculatorState.value = true }


        }


    }

    private fun setObservers() {
        viewModel.loaderVisibility.observe(this) { loaderVisibility -> binding.loader.isVisible = loaderVisibility }
        viewModel.message.observe(this) { message -> bottomToast(message) }
        lifecycleScope.launch(Dispatchers.Main) {
            getKeyboardState().collect { state ->
                if (state) showKeyboard() else hideKeyboard()
            }
        }
    }

    fun showTitle(title: String) {
        binding.topBar.text = title
    }

    private fun showArrow(arrowVisibility: Boolean) {
        binding.backArrow.isVisible = arrowVisibility
    }

    private fun showSettings(settingsVisibility: Boolean) {
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