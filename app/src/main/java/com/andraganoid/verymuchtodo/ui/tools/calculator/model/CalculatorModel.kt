package com.andraganoid.verymuchtodo.ui.tools.calculator.model

import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import com.andraganoid.verymuchtodo.ui.tools.calculator.util.currencyList
import com.andraganoid.verymuchtodo.ui.tools.calculator.util.unitList
import java.text.DecimalFormat

class CalculatorModel(
    val unit: ObservableField<UnitModel?>? = ObservableField<UnitModel?>(unitList[0]),
    val currency: ObservableField<CurrencyModel?>? = ObservableField<CurrencyModel?>(currencyList[0]),
    val quantity: ObservableFloat? = ObservableFloat(0f),
    val price: ObservableFloat? = ObservableFloat(0f)
) {
    val result: ObservableField<String> = ObservableField<String>()

    init {
        calculateResult()
    }

    fun calculateResult() {

        val p: Float? = price?.get()
        val q: Float? = quantity?.get()
        val m: Int? = unit?.get()?.multiplier

        var ppu: Float? = if (p == null || q == null || q == 0f) null else p / q * m!!
        if (ppu?.isNaN() == true || ppu?.isInfinite() == true || ppu == 0f) {
            ppu = null
        }

        val ppuString = if (ppu != null) {
            val df = DecimalFormat("#.00")
            df.isDecimalSeparatorAlwaysShown = false
            (df.format(ppu.toDouble()))
        } else {
            null
        }

        val r = if (ppuString != null) "$ppuString ${currency?.get()?.code}/${unit?.get()?.reference}" else ""

        result.set(r)
    }
}