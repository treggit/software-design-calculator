package cmokmz.token

import cmokmz.visitor.TokenVisitor

open class Token {
    fun <T> accept(tokenVisitor: TokenVisitor<T>) {
        tokenVisitor.visit(this)
    }

    override fun equals(other: Any?): Boolean {
        return toString() == other.toString()
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }
}

abstract class OperationToken : Token() {
    abstract fun getPriority(): Int
}

open class ParenthesisToken : Token()

class LeftParenthesis : ParenthesisToken() {
    override fun toString(): String {
        return "LP"
    }
}

class RightParenthesis : ParenthesisToken() {
    override fun toString(): String {
        return "RP"
    }
}

class PlusOperation : OperationToken() {
    override fun toString(): String {
        return "PLUS"
    }

    override fun getPriority() = 0
}

class MinusOperation : OperationToken() {
    override fun toString(): String {
        return "MINUS"
    }

    override fun getPriority() = 0
}

class MultiplyOperation : OperationToken() {
    override fun toString(): String {
        return "MUL"
    }

    override fun getPriority() = 1
}

class DivideOperation : OperationToken() {
    override fun toString(): String {
        return "DIV"
    }

    override fun getPriority() = 1
}

data class NumberToken(val value: String) : Token() {
    override fun toString(): String {
        return value
    }
}

class EOF : Token()

class START : Token()