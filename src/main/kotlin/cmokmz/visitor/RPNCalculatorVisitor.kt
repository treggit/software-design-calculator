package cmokmz.visitor

import cmokmz.token.*
import java.util.*

class RPNCalculatorVisitor : TokenVisitor<Double> {

    private val stack = Stack<Double>()

    override fun visit(token: OperationToken) {
        require(stack.size > 0)
        val y = stack.pop()
        val x = stack.pop()

        val result = when(token) {
            is PlusOperation -> x + y
            is MinusOperation -> x - y
            is MultiplyOperation -> x * y
            is DivideOperation -> x / y
            else -> 0.0
        }

        stack.push(result)
    }

    override fun visit(parenthesis: ParenthesisToken) {
        throw ParseException("Cannot handle parenthesis in RPN calculator")
    }

    override fun visit(number: NumberToken) {
        stack.push(number.value.toDouble())
    }

    override fun visit(eof: EOF) {

    }

    override fun extractResult(): Double {
        require(stack.size == 1)
        return stack.pop()
    }
}