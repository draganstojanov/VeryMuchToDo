package com.andraganoid.verymuchtodo.ui.tools

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.andraganoid.verymuchtodo.ui.tools.calculator.CalculatorTool
import org.koin.android.ext.android.inject

open class ToolsActivity : AppCompatActivity() {

    private val calculatorTool: CalculatorTool by inject()

    internal fun openCalculator(root: ViewGroup) {
        calculatorTool.openCalculator(root)
    }

}


