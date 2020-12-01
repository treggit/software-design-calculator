package cmokmz.visitor

import cmokmz.token.*
import junit.framework.TestCase

class RPNCalculatorVisitorTest : TestCase() {

    fun `test simple expression`() {
        val tokens = listOf(NumberToken("2"), NumberToken("3"), PlusOperation())
        assertEquals(2.0 + 3.0, RPNCalculatorVisitor().visitAll(tokens))
    }

    fun `test expression with multiply`() {
        val tokens = listOf(NumberToken("2"), NumberToken("3"), PlusOperation(), NumberToken("10"), MultiplyOperation())
        assertEquals((2.0 + 3.0) * 10.0, RPNCalculatorVisitor().visitAll(tokens))
    }

    fun `test expression with division`() {
        val tokens = listOf(NumberToken("2"), NumberToken("3"), DivideOperation(), NumberToken("10"), PlusOperation())
        assertEquals(2.0 / 3 + 10, RPNCalculatorVisitor().visitAll(tokens))
    }

    fun `test complex expression`() {
        val tokens = listOf(NumberToken("3"), NumberToken("4"), NumberToken("2"), MultiplyOperation(),
            NumberToken("1"), NumberToken("5"), MinusOperation(), DivideOperation(), PlusOperation()
        )

        assertEquals(2.0 * 4 / (1 - 5) + 3, RPNCalculatorVisitor().visitAll(tokens))
    }
}