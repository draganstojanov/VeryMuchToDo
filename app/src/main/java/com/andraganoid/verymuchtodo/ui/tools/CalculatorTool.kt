package com.andraganoid.verymuchtodo.ui.tools

import android.content.Context
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.andraganoid.verymuchtodo.databinding.CalculatorLayoutBinding
import com.andraganoid.verymuchtodo.ui.custom.TopModal

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

    internal fun openCalculator(context: Context, root: ViewGroup) {

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
            setOnFocusChangeListener { _, b ->
                if (b) {
                    currencyCollapse()
                    unitCollapse()
                }
            }
        }

        setQuantityFilter()
        with(calculator.quantityInput) {
            doOnTextChanged { text, _, _, _ ->
                calculatorModel.apply {
                    quantity?.set(text.toString().toFloatOrNull() ?: 0f)
                    calculatorModel.calculateResult()
                }
            }
            setOnFocusChangeListener { _, b ->
                if (b) {
                    currencyCollapse()
                    unitCollapse()
                }
            }
        }

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

        calculator.calcModel = calculatorModel

        calculator.unitArrow.setOnClickListener {
            it.toggleArrow()
            calculator.unitDropdown.toggleExpand()

            currencyCollapse()
            calculator.quantityInput.clearFocus()
            calculator.priceInput.clearFocus()
        }

        calculator.currencyArrow.setOnClickListener {
            it.toggleArrow()
            calculator.currencyDropdown.toggleExpand()

            unitCollapse()
            calculator.quantityInput.clearFocus()
            calculator.priceInput.clearFocus()
        }
    }

    private fun setQuantityFilter() {
        if (calculatorModel.unit?.get()?.reference.equals("piece")) {
            with(calculator.quantityInput) {
                inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_VARIATION_NORMAL
                calculator.quantityInput.filters = arrayOf<InputFilter>(android.text.InputFilter.LengthFilter(5))
            }
        } else {
            with(calculator.quantityInput) {
                inputType = android.text.InputType.TYPE_CLASS_PHONE or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
                filters = arrayOf<InputFilter>(DecimalFilter(8, 3))
            }
        }
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
        calculatorModel.unit?.set(unitModel)
        calculatorModel.calculateResult()
    }

    private fun currencyClick(currencyModel: CurrencyModel) {
        calculatorModel.currency?.set(currencyModel)
        calculatorModel.calculateResult()
    }
}