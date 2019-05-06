package com.jedev.calculadora.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jedev.calculadora.R
import com.jedev.calculadora.controller.KalcController
import com.jedev.calculadora.model.Kalc
import com.jedev.calculadora.model.Operator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val model = Kalc()
    private val controller = KalcController(model)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controller.setListenerOfNumber(btn_0, "0")
        controller.setListenerOfNumber(btn_1, "1")
        controller.setListenerOfNumber(btn_2, "2")
        controller.setListenerOfNumber(btn_3, "3")
        controller.setListenerOfNumber(btn_4, "4")
        controller.setListenerOfNumber(btn_5, "5")
        controller.setListenerOfNumber(btn_6, "6")
        controller.setListenerOfNumber(btn_7, "7")
        controller.setListenerOfNumber(btn_8, "8")
        controller.setListenerOfNumber(btn_9, "9")
        controller.setListenerOfNumber(btn_point, ".")

        controller.setListenerOfOperator(btn_sum, Operator.PLUS)
        controller.setListenerOfOperator(btn_minus, Operator.MINUS)
        controller.setListenerOfOperator(btn_multiply, Operator.MULTIPLY)
        controller.setListenerOfOperator(btn_divider, Operator.DIVIDE)
        controller.setListenerOfOperator(btn_right_parentese, Operator.RIGHT_PARENTHESES)
        controller.setListenerOfOperator(btn_left_parentese, Operator.LEFT_PARENTHESES)
        controller.setListenerOfOperator(btn_equals, Operator.EQUALS)

        controller.setListenerOfClear(btn_c)
        controller.setListenerOfBackspace(btn_backspace)

        model.setOnDisplayListener = { newValue ->
            display.text = newValue
        }

        model.setOnHistoryListener = { newValue ->
            history.text = newValue
            scroll.arrowScroll(View.FOCUS_DOWN)
        }
    }
}
