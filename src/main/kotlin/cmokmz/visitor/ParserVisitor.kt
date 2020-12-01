package cmokmz.visitor

import cmokmz.token.*
import java.lang.ArithmeticException
import java.util.*
import kotlin.collections.ArrayList

class ParserVisitor : TokenVisitor<List<Token>> {

    private val stack = Stack<Token>()
    private val result = ArrayList<Token>()

    override fun visit(token: OperationToken) {
        while (stack.size > 0) {
            val topToken = stack.peek()

            if (topToken is OperationToken && topToken.getPriority() >= token.getPriority()) {
                result.add(stack.pop())
            } else {
                break
            }
        }
        stack.push(token)
    }

    override fun visit(parenthesis: ParenthesisToken) {
        when(parenthesis) {
            is LeftParenthesis -> stack.push(parenthesis)
            else -> {
                var found = false
                while (stack.size > 0) {
                    when(val topToken = stack.pop()) {
                        is LeftParenthesis -> {
                            found = true
                            break
                        }
                        else -> result.add(topToken)
                    }
                }
                require(found) { "Missed opening bracket" }
            }
        }
    }

    override fun visit(number: NumberToken) {
        result.add(number)
    }

    override fun visit(eof: EOF) {

    }

    override fun extractResult(): List<Token> {
        while (!stack.isEmpty()) {
            val token = stack.pop()
            require(token is OperationToken) { "Missed closing bracket" }
            result.add(token)
        }
        return result
    }
}