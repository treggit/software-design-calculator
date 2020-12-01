package cmokmz.token

import cmokmz.visitor.ParseException
import java.io.InputStream
import java.lang.StringBuilder


class Tokenizer(private val stream: InputStream) {

    private var state: TokenizerState = StartState().apply { nextChar() }

    private abstract inner class TokenizerState(protected var ptr: Int, protected var curChar: Char, var token: Token? = null) {

        abstract fun nextState(): TokenizerState

        fun nextChar(): Char {
            do {
                doNextChar()
            } while (curChar.isWhitespace())

            return curChar
        }

        fun doNextChar(): Char {
            ptr++
            val x = stream.read()
            curChar = if (x == -1) 0.toChar() else x.toChar()
            return curChar
        }
    }

    private inner class StartState(ptr: Int = -1, curChar: Char = 0.toChar(), token: Token? = null) : TokenizerState(ptr, curChar, token) {
        override fun nextState(): TokenizerState {
            if (curChar == ')') {
                nextChar()
                token = RightParenthesis()
                return this
            }

            if (curChar == '(') {
                nextChar()
                token = LeftParenthesis()
                return this
            }

            if (curChar == '*') {
                nextChar()
                token = MultiplyOperation()
                return this
            }

            if (curChar == '/') {
                nextChar()
                token = DivideOperation()
                return this
            }

            if (curChar == '+') {
                nextChar()
                token = PlusOperation()
                return this
            }

            if (curChar == '-') {
                nextChar()
                token = MinusOperation()
                return this
            }

            if (curChar.toInt() == 0) {
                return EOFState()
            }

            if (curChar.isDigit()) {
                return NumberState(ptr, curChar).nextState()
            }

            throw ParseException("Unrecognized token at position $ptr")
        }
    }

    private inner class EOFState : TokenizerState(-1, '0', EOF()) {
        override fun nextState() = this
    }

    private inner class NumberState(ptr: Int = -1, curChar: Char = 0.toChar()) : TokenizerState(ptr, curChar) {

        private var value = StringBuilder()

        override fun nextState(): TokenizerState {
            return if (curChar.isDigit()) {
                value.append(curChar)
                nextChar()
                nextState()
            } else {
                StartState(ptr, curChar, NumberToken(value.toString()))
            }
        }
    }

    fun createToken(): Token {
        state = state.nextState()
        require(state.token != null) { "failed to acquire token" }

        return state.token!!
    }
}