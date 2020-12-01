package cmokmz.visitor

import cmokmz.token.*

interface TokenVisitor<T> {
    fun visit(token: Token) {
        when(token) {
            is OperationToken -> visit(token)
            is ParenthesisToken -> visit(token)
            is NumberToken -> visit(token)
            is EOF -> visit(token)
        }
    }

    fun visitAll(tokens: List<Token>): T {
        for (token in tokens) {
            visit(token)
        }
        return extractResult()
    }

    fun visit(token: OperationToken)
    fun visit(parenthesis: ParenthesisToken)
    fun visit(number: NumberToken)
    fun visit(eof: EOF)
    fun extractResult(): T
}