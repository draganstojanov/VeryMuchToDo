package com.andraganoid.verymuchtodo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.andraganoid.verymuchtodo.databinding.CalculatorLayoutBinding
import com.andraganoid.verymuchtodo.databinding.StackFragmentBinding
import com.andraganoid.verymuchtodo.main.MainViewModel
import com.andraganoid.verymuchtodo.ui.custom.TopModalNEW
import com.andraganoid.verymuchtodo.util.getCalculatorState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class ToolsFragment : Fragment() {

    lateinit var calculatorLayoutBinding: CalculatorLayoutBinding

    private var _calculatorBinding: StackFragmentBinding? = null
    private val calculatorBinding get() = _calculatorBinding!!

    internal lateinit var parentView: ViewGroup
    private var calculatorTopModal: TopModalNEW? = null
    private lateinit var calculator: CalculatorLayoutBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        setObservers()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _calculatorBinding = null
    }

    private fun setup() {

    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getCalculatorState().collect {
                    if (it) {
                        openCalculator()
                    } else {
                        calculatorTopModal?.collapse()
                    }
                }
            }


        }
    }

    private fun openCalculator() {


        calculator = CalculatorLayoutBinding.inflate(layoutInflater)
        calculatorTopModal = TopModalNEW(parentView, calculator.root)

        calculator.quantityLabel.text = "XXXXXX"


    }

}