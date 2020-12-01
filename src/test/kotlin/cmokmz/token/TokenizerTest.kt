package cmokmz.token

import com.sun.tools.corba.se.idl.constExpr.Plus
import junit.framework.TestCase

class TokenizerTest : TestCase() {

    fun testNextToken() {
        val input = "(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2"

        val actual = ArrayList<Token>()
        val tokenizer = Tokenizer(input.byteInputStream())
        while (true) {
            val token = tokenizer.createToken()
            if (token is EOF) {
                break
            }
            actual.add(token)
        }

        assertEquals(
            listOf(
                LeftParenthesis(), NumberToken("23"), PlusOperation(), NumberToken("10"), RightParenthesis(),
                MultiplyOperation(), NumberToken("5"), MinusOperation(), NumberToken("3"), MultiplyOperation(),
                LeftParenthesis(), NumberToken("32"), PlusOperation(), NumberToken("5"), RightParenthesis(),
                MultiplyOperation(), LeftParenthesis(), NumberToken("10"), MinusOperation(), NumberToken("4"),
                MultiplyOperation(), NumberToken("5"), RightParenthesis(), PlusOperation(), NumberToken("8"),
                DivideOperation(), NumberToken("2")
            ),
            actual
        )
    }
}