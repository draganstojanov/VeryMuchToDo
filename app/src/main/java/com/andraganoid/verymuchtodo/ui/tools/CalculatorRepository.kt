package com.andraganoid.verymuchtodo.ui.tools

object CalculatorRepository {

    fun getUnitList(): List<UnitModel> = listOf(
        UnitModel(name = "gram", short = "g", reference = "kg", multiplier = 1000),
        UnitModel(name = "decagram", short = "dag", reference = "kg", multiplier = 100),
        UnitModel(name = "milligram", short = "mg", reference = "kg", multiplier = 10000),
        UnitModel(name = "kilogram", short = "Kg", reference = "kg", multiplier = 1),
        UnitModel(name = "milliliter", short = "mL", reference = "L", multiplier = 1000),
        UnitModel(name = "centiliter", short = "cL", reference = "L", multiplier = 100),
        UnitModel(name = "deciliter", short = "dL", reference = "L", multiplier = 10),
        UnitModel(name = "liter", short = "L", reference = "L", multiplier = 1),
        UnitModel(name = "pack", short = "pack", reference = "piece", multiplier = 1),
    )

    fun getCurrencyList(): List<CurrencyModel> = listOf(
        CurrencyModel(name = "Dinar", code = "RSD"),
        CurrencyModel(name = "Euro", code = "EUR"),
        CurrencyModel(name = "Dollar", code = "USD"),
    )

}