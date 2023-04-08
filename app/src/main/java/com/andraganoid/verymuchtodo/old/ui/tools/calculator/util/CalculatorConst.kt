package com.andraganoid.verymuchtodo.old.ui.tools.calculator.util

import com.andraganoid.verymuchtodo.old.ui.tools.calculator.model.CurrencyModel
import com.andraganoid.verymuchtodo.old.ui.tools.calculator.model.UnitModel

val unitList: List<UnitModel> = listOf(
    UnitModel(name = "gram", code = "g", reference = "kg", multiplier = 1000),
    UnitModel(name = "deca gram", code = "dag", reference = "kg", multiplier = 100),
    UnitModel(name = "milligram", code = "mg", reference = "kg", multiplier = 10000),
    UnitModel(name = "kilogram", code = "Kg", reference = "kg", multiplier = 1),
    UnitModel(name = "milliliter", code = "mL", reference = "L", multiplier = 1000),
    UnitModel(name = "centiliter", code = "cL", reference = "L", multiplier = 100),
    UnitModel(name = "deciliter", code = "dL", reference = "L", multiplier = 10),
    UnitModel(name = "liter", code = "L", reference = "L", multiplier = 1),
    UnitModel(name = "pack", code = "pack", reference = "piece", multiplier = 1),
)

val currencyList: List<CurrencyModel> = listOf(
    CurrencyModel(name = "Dinar", code = "RSD"),
    CurrencyModel(name = "Euro", code = "EUR"),
    CurrencyModel(name = "Dollar", code = "USD"),
)