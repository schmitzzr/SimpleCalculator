package com.sliacen.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var lastNumeric: Boolean = true
    private var hasDot: Boolean = false
    private var onlyZero: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        if (onlyZero) {
            tvInput?.text = (view as Button).text
            onlyZero = false
        } else tvInput?.append((view as Button).text)

        lastNumeric = true

    }

    fun onClear(view: View) {
        tvInput?.text = "0"
        lastNumeric = true
        hasDot = false
        onlyZero = true
    }

    fun onDecimalPoint(view: View) {
        if(!hasDot){
            tvInput?.append(".")
            lastNumeric = false
            onlyZero = false
            hasDot = true
        }
    }

    private fun finalize(result: String) : String {
        var value = result
        if(result.endsWith(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    fun onEqual(view: View) {
        if(lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = 1.0
            try {
                if (tvValue.startsWith("-")) {
                    prefix = -1.0
                    tvValue = tvValue.substring(1)
                }

                val splitValue = tvValue.split("*","-","+","/")
                val num1 = splitValue[0]
                val num2 = splitValue[1]

                if (tvValue.contains("-")) {
                    tvInput?.text = finalize((prefix*num1.toDouble() - num2.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    tvInput?.text = finalize((prefix*num1.toDouble() + num2.toDouble()).toString())
                } else if (tvValue.contains("*")) {
                    tvInput?.text = finalize((prefix*num1.toDouble() * num2.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    tvInput?.text = finalize((prefix*num1.toDouble() / num2.toDouble()).toString())
                }

            } catch (e: ArithmeticException) {
                tvInput?.text = "ERR"
            }

        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (onlyZero && (view as Button).text == "-") {
                tvInput?.text = "-"
                onlyZero = false
            }
            else if(lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                hasDot = false
            }
        }
    }

    private fun isOperatorAdded(value : String) : Boolean {
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("+")
                    || value.contains("-")
                    || value.contains("*")
        }
    }
}