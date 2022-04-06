package com.andraganoid.verymuchtodo.ui.tools.calculator

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.andraganoid.verymuchtodo.databinding.CalculatorLayoutBinding
import com.andraganoid.verymuchtodo.ui.tools.arrowCollapseIfExpanded
import com.andraganoid.verymuchtodo.ui.tools.calculator.adapter.CurrencyAdapter
import com.andraganoid.verymuchtodo.ui.tools.calculator.adapter.UnitAdapter
import com.andraganoid.verymuchtodo.ui.tools.calculator.model.CalculatorModel
import com.andraganoid.verymuchtodo.ui.tools.calculator.model.CurrencyModel
import com.andraganoid.verymuchtodo.ui.tools.calculator.model.UnitModel
import com.andraganoid.verymuchtodo.ui.tools.calculator.util.DecimalFilter
import com.andraganoid.verymuchtodo.ui.tools.collapseIfExpanded
import com.andraganoid.verymuchtodo.ui.tools.toggleArrow
import com.andraganoid.verymuchtodo.ui.tools.toggleExpand
import com.andraganoid.verymuchtodo.util.tm.TopModal

class CalculatorTool(private val context: Context) {

    private lateinit var calculator: CalculatorLayoutBinding
    private val calculatorModel = CalculatorModel()
    private var calculatorTopModal: TopModal? = null
    private lateinit var root: ViewGroup

    internal fun toggleCalculator() {
        if (calculatorTopModal == null) {
            initCalculator()
        } else {
            calculatorTopModal?.toggleTopModal()
        }
    }

    internal fun openCalculator(root: ViewGroup) {
        if (calculatorTopModal == null) {
            this.root = root
            initCalculator()
        } else {
            calculatorTopModal?.expand()
        }
    }

    private fun initCalculator() {
        calculator = CalculatorLayoutBinding.inflate(LayoutInflater.from(context))
            .also { it.cancelBtn.setOnClickListener { calculatorTopModal?.collapse() } }

        calculatorTopModal = TopModal(
            parent = root,
            customView = calculator.root
        ).also { topModal ->
            topModal.isCancellable = true
            topModal.expand()
            topModal.requestFocusOnExpand = calculator.quantityInput
        }

        calculator.calcModel = calculatorModel

        setInputs()
        setDropdowns()

    }

    private fun setInputs() {
        with(calculator.priceInput) {
            filters = arrayOf(DecimalFilter(8, 2))
            doOnTextChanged { text, _, _, _ ->
                calculatorModel.apply {
                    price?.set(text.toString().toFloatOrNull() ?: 0f)
                    calculatorModel.calculateResult()
                }
            }
            onFocusChangeListener = calculatorFocusChangeListener
        }

        setQuantityFilter()
        with(calculator.quantityInput) {
            doOnTextChanged { text, _, _, _ ->
                calculatorModel.apply {
                    quantity?.set(text.toString().toFloatOrNull() ?: 0f)
                    calculatorModel.calculateResult()
                }
            }

            onFocusChangeListener = calculatorFocusChangeListener
        }
    }

    private fun setQuantityFilter() {
        with(calculator.quantityInput) {
            if (calculatorModel.unit?.get()?.reference.equals("piece")) {
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
                filters = arrayOf<InputFilter>(InputFilter.LengthFilter(5))
            } else {
                inputType = InputType.TYPE_CLASS_PHONE or InputType.TYPE_NUMBER_FLAG_DECIMAL
                filters = arrayOf<InputFilter>(DecimalFilter(8, 3))
            }
        }


    }


//        if (calculatorModel.unit?.get()?.reference.equals("piece")) {
//            with(calculator.quantityInput) {
//                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
//                filters = arrayOf<InputFilter>(InputFilter.LengthFilter(5))
//            }
//        } else {
//            with(calculator.quantityInput) {
//                inputType = InputType.TYPE_CLASS_PHONE or InputType.TYPE_NUMBER_FLAG_DECIMAL
//                filters = arrayOf<InputFilter>(DecimalFilter(8, 3))
//            }
//        }
//}

    private fun setDropdowns() {
        calculator.unitDropdown.adapter = UnitAdapter(unitList, this::unitClick)
        calculator.currencyDropdown.adapter = CurrencyAdapter(currencyList, this::currencyClick)

        calculator.clearBtn.setOnClickListener {
            with(calculator.priceInput) {
                setText("")
                requestFocusFromTouch()
            }
            calculator.quantityInput.setText("")
            calculatorModel.calculateResult()
        }

        calculator.unitArrow.setOnClickListener {
            it.toggleArrow()
            calculator.unitDropdown.toggleExpand()
            currencyCollapse()
            focusClear()
        }

        calculator.currencyArrow.setOnClickListener {
            it.toggleArrow()
            calculator.currencyDropdown.toggleExpand()
            unitCollapse()
            focusClear()
        }
    }

    private val calculatorFocusChangeListener = View.OnFocusChangeListener { _, b ->
        if (b) {
            currencyCollapse()
            unitCollapse()
        }
    }

    private fun focusClear() {
        calculator.quantityInput.clearFocus()
        calculator.priceInput.clearFocus()
    }

    private fun currencyCollapse() {
        calculator.currencyArrow.arrowCollapseIfExpanded()
        calculator.currencyDropdown.collapseIfExpanded()
    }

    private fun unitCollapse() {
        calculator.unitArrow.arrowCollapseIfExpanded()
        calculator.unitDropdown.collapseIfExpanded()
    }

    private fun unitClick(unitModel: UnitModel) {
        calculatorModel.apply {
            unit?.set(unitModel)
            calculateResult()
        }

    }

    private fun currencyClick(currencyModel: CurrencyModel) {
        calculatorModel.apply {
            currency?.set(currencyModel)
            calculateResult()
        }
    }
}