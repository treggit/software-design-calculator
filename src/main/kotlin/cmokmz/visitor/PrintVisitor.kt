package cmokmz.visitor

import cmokmz.token.*

class PrintVisitor : TokenVisitor<Unit> {
    override fun visit(token: OperationToken) = doPrint(token)

    private fun doPrint(token: Token) {
        print("$token ")
    }

    override fun visit(parenthesis: ParenthesisToken) = doPrint(parenthesis)

    override fun visit(number: NumberToken) = doPrint(number)

    override fun visit(eof: EOF) {
    }

    override fun extractResult() {
        println()
    }
}