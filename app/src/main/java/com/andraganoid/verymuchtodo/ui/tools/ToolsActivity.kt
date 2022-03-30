package com.andraganoid.verymuchtodo.ui.tools

import androidx.appcompat.app.AppCompatActivity
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.CalculatorLayoutBinding
import com.andraganoid.verymuchtodo.ui.custom.TopModal

open class ToolsActivity : AppCompatActivity() {

    //Calculator
    private var calculatorTopModal: TopModal? = null
    private lateinit var calculator: CalculatorLayoutBinding

    internal fun closeTools() {
        calculatorTopModal?.collapse()
    }

    //Calculator
    internal fun toggleCalculator() {
        if (calculatorTopModal == null) {
            initCalculator()
        } else {
            calculatorTopModal?.toggleTopModal()
        }
    }

    internal fun openCalculator() {
        if (calculatorTopModal == null) {
            initCalculator()
        } else {
            calculatorTopModal?.expand()
        }
    }

    private fun initCalculator() {
        calculator = CalculatorLayoutBinding.inflate(layoutInflater)
            .also { it.cancelBtn.setOnClickListener { calculatorTopModal?.collapse() } }

        calculatorTopModal = TopModal(
            parent = findViewById(R.id.mainActivityRoot),
            customView = calculator.root
        ).also {
            it.isClickable = true
            it.expand()
        }


    }
}