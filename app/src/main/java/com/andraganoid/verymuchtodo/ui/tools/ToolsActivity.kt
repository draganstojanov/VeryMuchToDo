package com.andraganoid.verymuchtodo.ui.tools

import android.text.InputFilter
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.CalculatorLayoutBinding
import com.andraganoid.verymuchtodo.ui.custom.TopModal

open class ToolsActivity : AppCompatActivity() {

    //Calculator
    private var calculatorTopModal: TopModal? = null
    private lateinit var calculator: CalculatorLayoutBinding
    private val calculatorModel = CalculatorModel()


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
            it.isCancellable = true
            it.expand()
            it.requestFocusOnExpand = calculator.quantityInput
        }

        with(calculator.priceInput) {
            filters = arrayOf(DecimalFilter(8, 2))
            doOnTextChanged { text, _, _, _ ->
                calculatorModel.apply {
                    price?.set(text.toString().toFloatOrNull() ?: 0f)
                    calculatorModel.calculateResult()
                }
            }
        }

        setQuantityFilter()
        calculator.quantityInput.doOnTextChanged { text, _, _, _ ->
            calculatorModel.apply {
                quantity?.set(text.toString().toFloatOrNull() ?: 0f)
                calculatorModel.calculateResult()
            }
        }

        calculator.clearBtn.setOnClickListener {
            with(calculator.priceInput) {
                setText("")
                requestFocusFromTouch()
            }
            calculator.quantityInput.setText("")
            calculatorModel.calculateResult()
        }

        calculator.calcModel = calculatorModel
    }

    private fun setQuantityFilter() {
        if (calculatorModel.unit?.get()?.reference.equals("piece")) {
            with(calculator.quantityInput) {
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
                calculator.quantityInput.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(5))
            }
        } else {
            with(calculator.quantityInput) {
                inputType = InputType.TYPE_CLASS_PHONE or InputType.TYPE_NUMBER_FLAG_DECIMAL
                filters = arrayOf<InputFilter>(DecimalFilter(8, 3))
            }
        }
    }

}


