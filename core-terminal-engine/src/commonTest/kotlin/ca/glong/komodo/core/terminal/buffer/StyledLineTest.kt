package ca.glong.komodo.core.terminal.buffer

import kotlin.test.Test
import kotlin.test.assertEquals

class StyledLineTest {
    
    @Test
    fun `new line contains empty cells`() {
        val line = StyledLine(80)
        assertEquals(' ', line.getCell(0).char)
        assertEquals(TerminalColor.Default, line.getCell(0).foreground)
    }
    
    @Test
    fun `setCell updates cell at position`() {
        val line = StyledLine(80)
        line.setCell(5, TerminalCell('A', TerminalColor.RED, TerminalColor.BLUE, CellAttributes.NONE))
        
        assertEquals('A', line.getCell(5).char)
        assertEquals(TerminalColor.RED, line.getCell(5).foreground)
        assertEquals(TerminalColor.BLUE, line.getCell(5).background)
    }
    
    @Test
    fun `toText returns trimmed string`() {
        val line = StyledLine(10)
        line.setCell(0, TerminalCell('H'))
        line.setCell(1, TerminalCell('i'))
        
        assertEquals("Hi", line.toText())
    }
    
    @Test
    fun `clear resets cells to empty`() {
        val line = StyledLine(10)
        line.setCell(0, TerminalCell('X'))
        line.setCell(1, TerminalCell('Y'))
        line.clear()
        
        assertEquals(' ', line.getCell(0).char)
        assertEquals(' ', line.getCell(1).char)
    }
    
    @Test
    fun `copy creates independent duplicate`() {
        val line = StyledLine(10)
        line.setCell(0, TerminalCell('A'))
        
        val copy = line.copy()
        copy.setCell(0, TerminalCell('B'))
        
        assertEquals('A', line.getCell(0).char)
        assertEquals('B', copy.getCell(0).char)
    }
}
