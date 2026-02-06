package ca.glong.komodo.core.terminal

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.test.assertContentEquals

class TerminalBufferTest {
    
    @Test
    fun `should create empty buffer`() {
        val buffer = TerminalBuffer(maxLines = 100)
        assertEquals(0, buffer.lineCount)
        assertTrue(buffer.isEmpty())
    }
    
    @Test
    fun `should add single line`() {
        val buffer = TerminalBuffer(maxLines = 100)
        buffer.addLine("Hello")
        
        assertEquals(1, buffer.lineCount)
        assertEquals("Hello", buffer.getLine(0))
        assertFalse(buffer.isEmpty())
    }
    
    @Test
    fun `should add multiple lines`() {
        val buffer = TerminalBuffer(maxLines = 100)
        buffer.addLine("Line 1")
        buffer.addLine("Line 2")
        buffer.addLine("Line 3")
        
        assertEquals(3, buffer.lineCount)
        assertEquals("Line 1", buffer.getLine(0))
        assertEquals("Line 2", buffer.getLine(1))
        assertEquals("Line 3", buffer.getLine(2))
    }
    
    @Test
    fun `should append to existing line`() {
        val buffer = TerminalBuffer(maxLines = 100)
        buffer.addLine("Hello")
        buffer.appendToCurrentLine(" World")
        
        assertEquals(1, buffer.lineCount)
        assertEquals("Hello World", buffer.getLine(0))
    }
    
    @Test
    fun `should enforce max lines limit by dropping oldest`() {
        val buffer = TerminalBuffer(maxLines = 3)
        buffer.addLine("Line 1")
        buffer.addLine("Line 2")
        buffer.addLine("Line 3")
        buffer.addLine("Line 4")
        
        assertEquals(3, buffer.lineCount)
        assertEquals("Line 2", buffer.getLine(0))
        assertEquals("Line 3", buffer.getLine(1))
        assertEquals("Line 4", buffer.getLine(2))
    }
    
    @Test
    fun `should return all lines in order`() {
        val buffer = TerminalBuffer(maxLines = 10)
        buffer.addLine("A")
        buffer.addLine("B")
        buffer.addLine("C")
        
        val lines = buffer.getLines()
        assertContentEquals(listOf("A", "B", "C"), lines)
    }
    
    @Test
    fun `should return lines range`() {
        val buffer = TerminalBuffer(maxLines = 10)
        buffer.addLine("Line 1")
        buffer.addLine("Line 2")
        buffer.addLine("Line 3")
        buffer.addLine("Line 4")
        buffer.addLine("Line 5")
        
        val lines = buffer.getLines(startIndex = 1, count = 3)
        assertContentEquals(listOf("Line 2", "Line 3", "Line 4"), lines)
    }
    
    @Test
    fun `should handle range beyond buffer size`() {
        val buffer = TerminalBuffer(maxLines = 10)
        buffer.addLine("Line 1")
        buffer.addLine("Line 2")
        
        val lines = buffer.getLines(startIndex = 0, count = 100)
        assertContentEquals(listOf("Line 1", "Line 2"), lines)
    }
    
    @Test
    fun `should return last n lines`() {
        val buffer = TerminalBuffer(maxLines = 10)
        buffer.addLine("A")
        buffer.addLine("B")
        buffer.addLine("C")
        buffer.addLine("D")
        buffer.addLine("E")
        
        val lines = buffer.getLastLines(3)
        assertContentEquals(listOf("C", "D", "E"), lines)
    }
    
    @Test
    fun `should clear buffer`() {
        val buffer = TerminalBuffer(maxLines = 10)
        buffer.addLine("Line 1")
        buffer.addLine("Line 2")
        assertEquals(2, buffer.lineCount)
        
        buffer.clear()
        
        assertEquals(0, buffer.lineCount)
        assertTrue(buffer.isEmpty())
    }
    
    @Test
    fun `should handle append to current line when buffer empty`() {
        val buffer = TerminalBuffer(maxLines = 100)
        buffer.appendToCurrentLine("Hello")
        
        assertEquals(1, buffer.lineCount)
        assertEquals("Hello", buffer.getLine(0))
    }
    
    @Test
    fun `should handle multiple appends to same line`() {
        val buffer = TerminalBuffer(maxLines = 100)
        buffer.appendToCurrentLine("Hello")
        buffer.appendToCurrentLine(" ")
        buffer.appendToCurrentLine("World")
        buffer.appendToCurrentLine("!")
        
        assertEquals(1, buffer.lineCount)
        assertEquals("Hello World!", buffer.getLine(0))
    }
    
    @Test
    fun `should handle line with control characters`() {
        val buffer = TerminalBuffer(maxLines = 100)
        buffer.addLine("Line\twith\ttabs")
        buffer.addLine("Line\nwith\nnewlines")
        
        assertEquals("Line\twith\ttabs", buffer.getLine(0))
        assertEquals(4, buffer.lineCount)
        assertEquals("Line", buffer.getLine(1))
        assertEquals("with", buffer.getLine(2))
        assertEquals("newlines", buffer.getLine(3))
    }
    
    @Test
    fun `should handle empty string input`() {
        val buffer = TerminalBuffer(maxLines = 100)
        buffer.addLine("")
        
        assertEquals(1, buffer.lineCount)
        assertEquals("", buffer.getLine(0))
    }
    
    @Test
    fun `should handle very large buffer size`() {
        val buffer = TerminalBuffer(maxLines = 10000)
        repeat(1000) {
            buffer.addLine("Line $it")
        }
        
        assertEquals(1000, buffer.lineCount)
        assertEquals("Line 0", buffer.getLine(0))
        assertEquals("Line 999", buffer.getLine(999))
    }
    
    @Test
    fun `should maintain line order after exceeding max lines multiple times`() {
        val buffer = TerminalBuffer(maxLines = 5)
        repeat(10) {
            buffer.addLine("Line $it")
        }
        
        assertEquals(5, buffer.lineCount)
        assertEquals("Line 5", buffer.getLine(0))
        assertEquals("Line 9", buffer.getLine(4))
    }
}
