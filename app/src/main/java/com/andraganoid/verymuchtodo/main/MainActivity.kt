package com.andraganoid.verymuchtodo.main

import android.os.Bundle
import android.view.WindowManager
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
import com.andraganoid.verymuchtodo.ui.tools.ToolsActivity
import com.andraganoid.verymuchtodo.util.ARGS_DIALOG_DATA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ToolsActivity() {

    companion object {
        lateinit var insetController: WindowInsetsControllerCompat
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()


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
        navController = findNavController(R.id.fragmentContainer)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.stackFragment -> {
                    showTitle(getString(R.string.todo))
                    showArrow(false)
                    showSettings(true)
                    showCalculator(true)
                }
                R.id.todoListFragment -> {
                    showTitle("")
                    showArrow(true)
                    showSettings(true)
                    showCalculator(true)
                }
                R.id.settingsFragment -> {
                    showTitle(getString(R.string.settings))
                    showArrow(true)
                    showSettings(false)
                    showCalculator(false)
                }
                R.id.autocompleteEditFragment -> {
                    showTitle(getString(R.string.edit_autocomplete))
                    showArrow(true)
                    showSettings(false)
                    showCalculator(false)
                }
            }
        }
    }

    private fun setup() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding.backArrow.setOnClickListener { navController.popBackStack() }
        binding.settingsBtn.setOnClickListener { navController.navigate(R.id.settingsFragment) }
        binding.calculatorBtn.setOnClickListener { openCalculator() }
    }

    private fun setObservers() {
        viewModel.loaderVisibility.observe(this) { loaderVisibility -> binding.loader.isVisible = loaderVisibility }
        viewModel.message.observe(this) { message -> bottomToast(message) }
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

    private fun showCalculator(calculatorVisibility: Boolean) {
        binding.calculatorBtn.isVisible = calculatorVisibility
    }

    fun showKeyboard() {
      //  lifecycleScope.launch(Dispatchers.Main) {
       //     delay(300)
            insetController.show(WindowInsetsCompat.Type.ime())
     //   }
    }

    fun hideKeyboard() {
      //  lifecycleScope.launch(Dispatchers.Main) {
        //    delay(300)
            insetController.hide(WindowInsetsCompat.Type.ime())
      //  }
    }

    fun messageDialog(dialogData: MessageDialogData) {
        navController.navigate(R.id.messageDialog, bundleOf(ARGS_DIALOG_DATA to dialogData))
    }

    fun bottomToast(msg: Any?) {
        messageDialog(MessageDialogData(toast = msg.toString()))
    }

    fun invisibleToolbar(isInvisible: Boolean) {
        binding.topBarMask.isVisible = isInvisible
    }


}