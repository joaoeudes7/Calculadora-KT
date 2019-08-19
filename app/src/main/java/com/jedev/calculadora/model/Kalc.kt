package com.jedev.calculadora.model

import com.udojava.evalex.Expression
import java.math.BigDecimal
import kotlin.properties.Delegates

class Kalc(
    setOnDisplayListener: (value: String) -> Unit = {},
    setOnHistoryListener: (value: String) -> Unit = {}
) {

    private var expression = String()
    private var hasResult = true
    private var historic = arrayListOf<String>()

    // Observables
    private var display by Delegates.observable("0", { _, _, newValue ->
        setOnDisplayListener.invoke(newValue)
    })

    private var historicStr by Delegates.observable("", { _, _, newValue ->
        setOnHistoryListener.invoke(newValue)
    })

    private fun resolveExpression(_expression: String): BigDecimal? {
        return Expression(_expression).eval()
    }

    private fun addDisplayExpression(_display: String, _expression: String = _display) {
        if (this.hasResult) {
            this.display = _display
            this.expression = _expression
            this.hasResult = false
        } else {
            this.display += _display
            this.expression += _expression
        }
    }

    // Operators
    private fun some() {
        addDisplayExpression("+")
    }

    private fun minus() {
        addDisplayExpression("-")
    }

    private fun divider() {
        addDisplayExpression("÷", "/")
    }

    private fun multiply() {
        addDisplayExpression("x", "*")
    }

    private fun leftParentheses() {
        addDisplayExpression("(")
    }

    private fun rightParentheses() {
        addDisplayExpression(")")
    }

    private fun equals() {
        val hasOperationInExpression = checkIfHasOperationInExpression()
        val withoutResult = !this.hasResult

        if (withoutResult && hasOperationInExpression) {
            var result: String
            this.hasResult = true

            try {
                val bigResult = resolveExpression(this.expression)
                result = bigResult!!.toEngineeringString()

                this.saveInHistory(this.display, result)

                this.display = result
                this.expression = result

            } catch (e: Throwable) {
                result = "#Error"
                this.display = result
                this.expression = ""
            }
        }
    }

    private fun checkIfHasOperationInExpression(): Boolean {
        var hasOperationInExpression = false

        arrayListOf('+', '-', '/', '*', '(', ')').forEach {
            if (this.expression.contains(it)) {
                hasOperationInExpression = true
            }
        }

        return hasOperationInExpression
    }

    private fun saveInHistory(display: String, result: String) {
        val itemHistory = "$display = $result"
        val totalHistory = this.historic.size

        if (totalHistory == 20) {
            this.historic.removeAt(0)
        } else {
            this.historic.add(itemHistory)
        }

        this.updateHistoric()
    }

    private fun updateHistoric() {
        var auxHistoricStr = ""

        this.historic.forEach { h ->
            auxHistoricStr += "$h \n"
        }

         this.historicStr = auxHistoricStr
    }

    private fun clearExpression() {
        this.expression = ""
    }

    fun addNumber(num: String) {
        addDisplayExpression(num)
    }

    fun addOperator(operator: Operator) {
        if (operator == Operator.EQUALS && this.hasResult) {
            return
        }

        if (this.hasResult) {
            this.hasResult = false
        }

        if (this.display.last() in arrayOf('+', '-', '÷', 'x')) {
            this.removeLastOperation()
        }

        when(operator) {
            Operator.PLUS -> some()
            Operator.DIVIDE -> divider()
            Operator.MINUS -> minus()
            Operator.MULTIPLY -> multiply()
            Operator.LEFT_PARENTHESES -> leftParentheses()
            Operator.RIGHT_PARENTHESES -> rightParentheses()
            Operator.EQUALS -> equals()
        }
    }

    fun clearDisplay() {
        this.display = "0"
        this.hasResult = true
        this.clearExpression()
    }

    fun removeLastOperation() {
        if (this.display.length == 1) {
            this.display = "0"
            this.expression = ""
            this.hasResult = true
        } else {
            this.display = this.display.dropLast(1)
            this.expression = this.expression.dropLast(1)
        }
    }


    enum class Operator {
        PLUS,
        MINUS,
        MULTIPLY,
        RIGHT_PARENTHESES,
        LEFT_PARENTHESES,
        DIVIDE,
        EQUALS
    }

}