package com.andraganoid.verymuchtodo.ui.tools.calculator

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import com.andraganoid.verymuchtodo.databinding.CalculatorLayoutBinding
import com.andraganoid.verymuchtodo.ui.tools.calculator.adapter.CurrencyAdapter
import com.andraganoid.verymuchtodo.ui.tools.calculator.adapter.UnitAdapter
import com.andraganoid.verymuchtodo.ui.tools.calculator.model.CalculatorModel
import com.andraganoid.verymuchtodo.ui.tools.calculator.model.CurrencyModel
import com.andraganoid.verymuchtodo.ui.tools.calculator.model.UnitModel
import com.andraganoid.verymuchtodo.ui.tools.calculator.util.*
import com.andraganoid.verymuchtodo.util.tm.TopModal

class CalculatorTool @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    val binding = CalculatorLayoutBinding.inflate(LayoutInflater.from(context), this, false)

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

        calculatorTopModal = TopModal(parentView = root, customView = binding.root)

        calculatorTopModal?.apply {
            isCancellable = true
            expand()
            requestFocusOnExpand = binding.quantityInput
        }

        binding.cancelBtn.setOnClickListener { calculatorTopModal?.collapse() }
        binding.calcModel = calculatorModel

        setInputs()
        setDropdowns()

    }

    private fun setInputs() {
        with(binding.priceInput) {
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
        with(binding.quantityInput) {
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
        with(binding.quantityInput) {
            if (calculatorModel.unit?.get()?.reference.equals("piece")) {
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
                filters = arrayOf<InputFilter>(InputFilter.LengthFilter(5))
            } else {
                inputType = InputType.TYPE_CLASS_PHONE or InputType.TYPE_NUMBER_FLAG_DECIMAL
                filters = arrayOf<InputFilter>(DecimalFilter(8, 3))
            }
        }

    }

    private fun setDropdowns() {
        binding.unitDropdown.adapter = UnitAdapter(unitList, this::unitClick)
        binding.currencyDropdown.adapter = CurrencyAdapter(currencyList, this::currencyClick)

        binding.clearBtn.setOnClickListener {
            with(binding.priceInput) {
                setText("")
                requestFocusFromTouch()
            }
            binding.quantityInput.setText("")
            calculatorModel.calculateResult()
        }

        binding.unitArrow.setOnClickListener {
            it.toggleArrow()
            binding.unitDropdown.toggleExpand()
            currencyCollapse()
            focusClear()
        }

        binding.currencyArrow.setOnClickListener {
            it.toggleArrow()
            binding.currencyDropdown.toggleExpand()
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
        binding.quantityInput.clearFocus()
        binding.priceInput.clearFocus()
    }

    private fun currencyCollapse() {
        binding.currencyArrow.arrowCollapseIfExpanded()
        binding.currencyDropdown.collapseIfExpanded()
    }

    private fun unitCollapse() {
        binding.unitArrow.arrowCollapseIfExpanded()
        binding.unitDropdown.collapseIfExpanded()
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