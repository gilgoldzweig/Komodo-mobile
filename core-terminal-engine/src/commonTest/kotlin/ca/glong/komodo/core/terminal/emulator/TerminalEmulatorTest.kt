package ca.glong.komodo.core.terminal.emulator

import ca.glong.komodo.core.terminal.buffer.CellAttributes
import ca.glong.komodo.core.terminal.buffer.TerminalColor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TerminalEmulatorTest {
    
    @Test
    fun `write plain text to buffer`() {
        val emu = TerminalEmulator(columns = 80, rows = 24)
        emu.write("Hello")
        
        assertEquals('H', emu.getCell(0, 0).char)
        assertEquals('e', emu.getCell(0, 1).char)
        assertEquals('l', emu.getCell(0, 2).char)
        assertEquals('l', emu.getCell(0, 3).char)
        assertEquals('o', emu.getCell(0, 4).char)
        assertEquals(0, emu.cursor.value.row)
        assertEquals(5, emu.cursor.value.col)
    }
    
    @Test
    fun `newline moves cursor down`() {
        val emu = TerminalEmulator(columns = 80, rows = 24)
        emu.write("Line1\r\nLine2")
        
        assertEquals("Line1", emu.getLineText(0))
        assertEquals("Line2", emu.getLineText(1))
        assertEquals(1, emu.cursor.value.row)
    }
    
    @Test
    fun `carriage return moves cursor to start`() {
        val emu = TerminalEmulator(columns = 80, rows = 24)
        emu.write("Hello\rWorld")
        
        assertEquals("World", emu.getLineText(0))
    }
    
    @Test
    fun `SGR colors apply to subsequent text`() {
        val emu = TerminalEmulator()
        emu.write("\u001b[31mRed")
        
        assertEquals(TerminalColor.Indexed(1), emu.getCell(0, 0).foreground)
        assertEquals(TerminalColor.Indexed(1), emu.getCell(0, 1).foreground)
        assertEquals(TerminalColor.Indexed(1), emu.getCell(0, 2).foreground)
    }
    
    @Test
    fun `SGR bold attribute`() {
        val emu = TerminalEmulator()
        emu.write("\u001b[1mBold")
        
        assertTrue(emu.getCell(0, 0).attributes.bold)
    }
    
    @Test
    fun `cursor movement CUP`() {
        val emu = TerminalEmulator()
        emu.write("\u001b[5;10H")
        
        assertEquals(4, emu.cursor.value.row)
        assertEquals(9, emu.cursor.value.col)
    }
    
    @Test
    fun `cursor up CUU`() {
        val emu = TerminalEmulator()
        emu.write("\u001b[10;10H")
        emu.write("\u001b[3A")
        
        assertEquals(6, emu.cursor.value.row)
        assertEquals(9, emu.cursor.value.col)
    }
    
    @Test
    fun `erase line from cursor to end`() {
        val emu = TerminalEmulator()
        emu.write("Hello World")
        emu.write("\u001b[6G")
        emu.write("\u001b[K")
        
        assertEquals("Hello", emu.getLineText(0))
    }
    
    @Test
    fun `erase display clears screen`() {
        val emu = TerminalEmulator()
        emu.write("Line 1\nLine 2\nLine 3")
        emu.write("\u001b[2J")
        
        assertEquals("", emu.getLineText(0))
        assertEquals("", emu.getLineText(1))
        assertEquals("", emu.getLineText(2))
    }
    
    @Test
    fun `scroll up adds to scrollback`() {
        val emu = TerminalEmulator(columns = 10, rows = 3, maxScrollback = 100)
        emu.write("A\nB\nC\nD")
        
        assertTrue(emu.scrollbackSize > 0)
    }
    
    @Test
    fun `alternate screen buffer save and restore`() {
        val emu = TerminalEmulator()
        emu.write("Main screen")
        emu.write("\u001b[?1049h")
        
        assertEquals(TerminalContext.EDITOR, emu.context.value)
        assertEquals("", emu.getLineText(0))
        
        emu.write("Alt screen")
        emu.write("\u001b[?1049l")
        
        assertEquals(TerminalContext.NORMAL, emu.context.value)
        assertEquals("Main screen", emu.getLineText(0))
    }
    
    @Test
    fun `cursor visibility DECTCEM`() {
        val emu = TerminalEmulator()
        assertTrue(emu.cursor.value.visible)
        
        emu.write("\u001b[?25l")
        assertFalse(emu.cursor.value.visible)
        
        emu.write("\u001b[?25h")
        assertTrue(emu.cursor.value.visible)
    }
    
    @Test
    fun `tab moves to next tab stop`() {
        val emu = TerminalEmulator()
        emu.write("A\tB")
        
        assertEquals('A', emu.getCell(0, 0).char)
        assertEquals('B', emu.getCell(0, 8).char)
    }
    
    @Test
    fun `backspace moves cursor back`() {
        val emu = TerminalEmulator()
        emu.write("ABC")
        emu.write("\b")
        
        assertEquals(2, emu.cursor.value.col)
    }
    
    @Test
    fun `line wrap at end of row`() {
        val emu = TerminalEmulator(columns = 5, rows = 3)
        emu.write("HelloWorld")
        
        assertEquals("Hello", emu.getLineText(0))
        assertEquals("World", emu.getLineText(1))
    }
    
    @Test
    fun `reset clears state`() {
        val emu = TerminalEmulator()
        emu.write("\u001b[31mRed Text")
        emu.reset()
        
        assertEquals("", emu.getLineText(0))
        assertEquals(0, emu.cursor.value.row)
        assertEquals(0, emu.cursor.value.col)
        assertEquals(TerminalContext.NORMAL, emu.context.value)
    }
    
    @Test
    fun `256 color support`() {
        val emu = TerminalEmulator()
        emu.write("\u001b[38;5;196mRed")
        
        assertEquals(TerminalColor.Indexed(196), emu.getCell(0, 0).foreground)
    }
    
    @Test
    fun `RGB color support`() {
        val emu = TerminalEmulator()
        emu.write("\u001b[38;2;255;128;0mOrange")
        
        val color = emu.getCell(0, 0).foreground as TerminalColor.Rgb
        assertEquals(255, color.r)
        assertEquals(128, color.g)
        assertEquals(0, color.b)
    }
}
