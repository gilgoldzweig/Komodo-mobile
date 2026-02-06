package ca.glong.komodo.core.terminal.parser

import ca.glong.komodo.core.terminal.buffer.CellAttributes
import ca.glong.komodo.core.terminal.buffer.TerminalColor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SgrInterpreterTest {
    private val interpreter = SgrInterpreter()
    
    @Test
    fun `initial state is default`() {
        assertEquals(SgrState.DEFAULT, interpreter.currentState)
    }
    
    @Test
    fun `reset code clears all attributes`() {
        interpreter.apply(listOf(1, 31))
        interpreter.apply(listOf(0))
        assertEquals(SgrState.DEFAULT, interpreter.currentState)
    }
    
    @Test
    fun `bold attribute`() {
        interpreter.apply(listOf(1))
        assertTrue(interpreter.currentState.attributes.bold)
    }
    
    @Test
    fun `italic attribute`() {
        interpreter.apply(listOf(3))
        assertTrue(interpreter.currentState.attributes.italic)
    }
    
    @Test
    fun `underline attribute`() {
        interpreter.apply(listOf(4))
        assertTrue(interpreter.currentState.attributes.underline)
    }
    
    @Test
    fun `standard foreground colors`() {
        interpreter.apply(listOf(31))
        assertEquals(TerminalColor.Indexed(1), interpreter.currentState.foreground)
        
        interpreter.apply(listOf(34))
        assertEquals(TerminalColor.Indexed(4), interpreter.currentState.foreground)
    }
    
    @Test
    fun `standard background colors`() {
        interpreter.apply(listOf(42))
        assertEquals(TerminalColor.Indexed(2), interpreter.currentState.background)
    }
    
    @Test
    fun `bright foreground colors`() {
        interpreter.apply(listOf(91))
        assertEquals(TerminalColor.Indexed(9), interpreter.currentState.foreground)
        
        interpreter.apply(listOf(97))
        assertEquals(TerminalColor.Indexed(15), interpreter.currentState.foreground)
    }
    
    @Test
    fun `256-color foreground`() {
        interpreter.apply(listOf(38, 5, 196))
        assertEquals(TerminalColor.Indexed(196), interpreter.currentState.foreground)
    }
    
    @Test
    fun `256-color background`() {
        interpreter.apply(listOf(48, 5, 240))
        assertEquals(TerminalColor.Indexed(240), interpreter.currentState.background)
    }
    
    @Test
    fun `RGB foreground`() {
        interpreter.apply(listOf(38, 2, 255, 128, 0))
        val rgb = interpreter.currentState.foreground as TerminalColor.Rgb
        assertEquals(255, rgb.r)
        assertEquals(128, rgb.g)
        assertEquals(0, rgb.b)
    }
    
    @Test
    fun `RGB background`() {
        interpreter.apply(listOf(48, 2, 0, 100, 200))
        val rgb = interpreter.currentState.background as TerminalColor.Rgb
        assertEquals(0, rgb.r)
        assertEquals(100, rgb.g)
        assertEquals(200, rgb.b)
    }
    
    @Test
    fun `default foreground code 39`() {
        interpreter.apply(listOf(31))
        interpreter.apply(listOf(39))
        assertEquals(TerminalColor.Default, interpreter.currentState.foreground)
    }
    
    @Test
    fun `default background code 49`() {
        interpreter.apply(listOf(42))
        interpreter.apply(listOf(49))
        assertEquals(TerminalColor.Default, interpreter.currentState.background)
    }
    
    @Test
    fun `turn off bold`() {
        interpreter.apply(listOf(1))
        assertTrue(interpreter.currentState.attributes.bold)
        interpreter.apply(listOf(22))
        assertFalse(interpreter.currentState.attributes.bold)
    }
    
    @Test
    fun `combined attributes persist`() {
        interpreter.apply(listOf(1, 31, 42))
        assertTrue(interpreter.currentState.attributes.bold)
        assertEquals(TerminalColor.Indexed(1), interpreter.currentState.foreground)
        assertEquals(TerminalColor.Indexed(2), interpreter.currentState.background)
    }
    
    @Test
    fun `reset clears everything`() {
        interpreter.apply(listOf(1, 3, 4, 31, 42))
        interpreter.reset()
        assertEquals(SgrState.DEFAULT, interpreter.currentState)
    }
}
