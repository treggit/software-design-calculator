package cmokmz

import cmokmz.token.EOF
import cmokmz.token.Tokenizer
import cmokmz.visitor.ParserVisitor
import cmokmz.visitor.PrintVisitor
import cmokmz.visitor.RPNCalculatorVisitor
import java.lang.StringBuilder
import java.util.*

fun main(args: Array<String>) {
    var expr = ""
    with(Scanner(System.`in`)) {
        println("Enter the expression")
        expr = readLine() ?: ""
    }

    val tokenizer = Tokenizer(expr.byteInputStream())
    val parser = ParserVisitor()
    while (true) {
        val token = tokenizer.createToken()
        if (token is EOF) {
            break
        }
        parser.visit(token)
    }

    val tokens = parser.extractResult()
    println("Expression in RPN")
    PrintVisitor().visitAll(tokens)
    println("Expression result: ${RPNCalculatorVisitor().visitAll(tokens)}")
}