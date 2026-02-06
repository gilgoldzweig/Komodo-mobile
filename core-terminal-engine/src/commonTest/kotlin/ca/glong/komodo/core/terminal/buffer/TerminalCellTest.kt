package ca.glong.komodo.core.terminal.buffer

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TerminalCellTest {
    
    @Test
    fun `empty cell has space character and default colors`() {
        val cell = TerminalCell.EMPTY
        assertEquals(' ', cell.char)
        assertEquals(TerminalColor.Default, cell.foreground)
        assertEquals(TerminalColor.Default, cell.background)
        assertEquals(CellAttributes.NONE, cell.attributes)
    }
    
    @Test
    fun `cell with character preserves it`() {
        val cell = TerminalCell(char = 'A')
        assertEquals('A', cell.char)
    }
    
    @Test
    fun `cell with indexed color`() {
        val cell = TerminalCell(
            char = 'X',
            foreground = TerminalColor.RED,
            background = TerminalColor.BLUE
        )
        assertEquals(TerminalColor.Indexed(1), cell.foreground)
        assertEquals(TerminalColor.Indexed(4), cell.background)
    }
    
    @Test
    fun `cell with RGB color`() {
        val cell = TerminalCell(
            foreground = TerminalColor.Rgb(255, 128, 0)
        )
        val rgb = cell.foreground as TerminalColor.Rgb
        assertEquals(255, rgb.r)
        assertEquals(128, rgb.g)
        assertEquals(0, rgb.b)
    }
    
    @Test
    fun `cell attributes bold flag`() {
        val attrs = CellAttributes.NONE.with(CellAttributes.BOLD)
        assertTrue(attrs.bold)
        assertFalse(attrs.italic)
        
        val withoutBold = attrs.without(CellAttributes.BOLD)
        assertFalse(withoutBold.bold)
    }
    
    @Test
    fun `cell attributes multiple flags`() {
        val attrs = CellAttributes.NONE
            .with(CellAttributes.BOLD)
            .with(CellAttributes.UNDERLINE)
            .with(CellAttributes.ITALIC)
        
        assertTrue(attrs.bold)
        assertTrue(attrs.italic)
        assertTrue(attrs.underline)
        assertFalse(attrs.blink)
    }
    
    @Test
    fun `bright colors are indexes 8-15`() {
        assertEquals(8, TerminalColor.BRIGHT_BLACK.index)
        assertEquals(15, TerminalColor.BRIGHT_WHITE.index)
    }
}
