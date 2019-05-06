package com.jedev.calculadora.controller

import android.view.View
import com.jedev.calculadora.model.Kalc
import com.jedev.calculadora.model.Operator

class KalcController(var model: Kalc) {

    fun setListenerOfOperator(component: View, operator: Operator) {
        component.setOnClickListener {
            model.addOperator(operator)
        }
    }

    fun setListenerOfNumber(component: View, num: String) {
        component.setOnClickListener {
            model.addNumber(num)
        }
    }

    fun setListenerOfClear(component: View) {
        component.setOnClickListener {
            model.clearDisplay()
        }
    }

    fun setListenerOfBackspace(component: View) {
        component.setOnClickListener {
            model.removeLastOperation()
        }
    }

}