package com.andraganoid.verymuchtodo.ui.tools

import androidx.databinding.ObservableField

class CalculatorModel(
    val unit: ObservableField<UnitModel?> = ObservableField<UnitModel?>(unitList[0]),
    val currency: ObservableField<CurrencyModel?> = ObservableField<CurrencyModel?>(currencyList[0]),
    val quantity: ObservableField<Number?> = ObservableField<Number?>(0),
    val price: ObservableField<Number?> = ObservableField<Number?>(0),
) {
    val result: ObservableField<String> = ObservableField<String>()

    fun calculateResult() {

        val p: Float? = price.get()?.toFloat()
        val q: Float? = quantity.get()?.toFloat()
        val m: Int? = unit.get()?.multiplier

        val ppu = p!! / q!! * m!!

        val r = "$ppu ${unit.get()?.code}/${currency.get()?.code}"

        result.set(r)
    }
}