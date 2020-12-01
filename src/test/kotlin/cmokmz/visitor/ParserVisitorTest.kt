package cmokmz.visitor

import cmokmz.token.*
import junit.framework.TestCase
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.test.assertFailsWith

class ParserVisitorTest : TestCase() {

    fun `test low priority operations`() {
        assertEquals(
            ParserVisitor().visitAll(
                listOf(NumberToken("2"), PlusOperation(), NumberToken("3"), MinusOperation(), NumberToken("4"))
            ),
            listOf(NumberToken("2"), NumberToken("3"), PlusOperation(), NumberToken("4"), MinusOperation())
        )
    }

    fun `test high priority operations`() {
        assertEquals(
            ParserVisitor().visitAll(
                listOf(NumberToken("2"), MultiplyOperation(), NumberToken("3"), DivideOperation(), NumberToken("4"))
            ),
            listOf(NumberToken("2"), NumberToken("3"), MultiplyOperation(), NumberToken("4"), DivideOperation())
        )
    }

    fun `test mixed priorities`() {
        assertEquals(
            ParserVisitor().visitAll(
                listOf(NumberToken("2"), PlusOperation(), NumberToken("3"), MultiplyOperation(), NumberToken("4"))
            ),
            listOf(NumberToken("2"), NumberToken("3"), NumberToken("4"), MultiplyOperation(), PlusOperation())
        )
    }

    fun `test expression with parenthesis`() {
        assertEquals(
            ParserVisitor().visitAll(
                listOf(NumberToken("3"), PlusOperation(), NumberToken("4"), MultiplyOperation(), NumberToken("2"),
                    DivideOperation(), LeftParenthesis(), NumberToken("1"), MinusOperation(), NumberToken("5"), RightParenthesis()
                )
            ),
            listOf(NumberToken("3"), NumberToken("4"), NumberToken("2"), MultiplyOperation(),
                NumberToken("1"), NumberToken("5"), MinusOperation(), DivideOperation(), PlusOperation()
            )
        )
    }

    fun `test missing closing bracket`() {
        assertFailsWith<IllegalArgumentException> {
            ParserVisitor().visitAll(
                listOf(LeftParenthesis(), NumberToken("3"), PlusOperation(), NumberToken("4"))
            )
        }
    }

    fun `test missing opening bracket`() {
        assertFailsWith<IllegalArgumentException> {
            ParserVisitor().visitAll(
                listOf(NumberToken("3"), PlusOperation(), NumberToken("4"), RightParenthesis())
            )
        }
    }
}