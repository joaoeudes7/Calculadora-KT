package com.jedev.calculadora.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jedev.calculadora.R
import com.jedev.calculadora.model.Kalc
import com.jedev.calculadora.model.Kalc.Operator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val model = Kalc(::onAddToDisplay, ::onAddToHistory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListenerOfNumber(btn_0, "0")
        setListenerOfNumber(btn_1, "1")
        setListenerOfNumber(btn_2, "2")
        setListenerOfNumber(btn_3, "3")
        setListenerOfNumber(btn_4, "4")
        setListenerOfNumber(btn_5, "5")
        setListenerOfNumber(btn_6, "6")
        setListenerOfNumber(btn_7, "7")
        setListenerOfNumber(btn_8, "8")
        setListenerOfNumber(btn_9, "9")
        setListenerOfNumber(btn_point, ".")

        setListenerOfOperator(btn_sum, Operator.PLUS)
        setListenerOfOperator(btn_minus, Operator.MINUS)
        setListenerOfOperator(btn_multiply, Operator.MULTIPLY)
        setListenerOfOperator(btn_divider, Operator.DIVIDE)
        setListenerOfOperator(btn_right_parentese, Operator.RIGHT_PARENTHESES)
        setListenerOfOperator(btn_left_parentese, Operator.LEFT_PARENTHESES)
        setListenerOfOperator(btn_equals, Operator.EQUALS)

        setListenerOfClear(btn_c)
        setListenerOfBackspace(btn_backspace)
    }

    private fun onAddToDisplay(value: String) {
        display.text = value
    }

    private fun onAddToHistory(newValue: String) {
        history.text = newValue
        scroll.arrowScroll(View.FOCUS_DOWN)
    }

    private fun setListenerOfOperator(component: View, operator: Operator) {
        component.setOnClickListener {
            model.addOperator(operator)
        }
    }

    private fun setListenerOfNumber(component: View, num: String) {
        component.setOnClickListener {
            model.addNumber(num)
        }
    }

    private fun setListenerOfClear(component: View) {
        component.setOnClickListener {
            model.clearDisplay()
        }
    }

    private fun setListenerOfBackspace(component: View) {
        component.setOnClickListener {
            model.removeLastOperation()
        }
    }
}
