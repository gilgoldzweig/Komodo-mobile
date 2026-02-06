package ca.glong.komodo.core.terminal.buffer

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class StyledBufferTest {
    
    @Test
    fun `default buffer is 80x24`() {
        val buffer = StyledBuffer()
        assertEquals(80, buffer.columns)
        assertEquals(24, buffer.rows)
    }
    
    @Test
    fun `setCell and getCell work`() {
        val buffer = StyledBuffer()
        buffer.setCell(0, 0, TerminalCell('X'))
        assertEquals('X', buffer.getCell(0, 0).char)
    }
    
    @Test
    fun `scrollUp moves lines and adds to scrollback`() {
        val buffer = StyledBuffer(columns = 10, rows = 3, maxScrollback = 100)
        buffer.setCell(0, 0, TerminalCell('A'))
        buffer.setCell(1, 0, TerminalCell('B'))
        buffer.setCell(2, 0, TerminalCell('C'))
        
        buffer.scrollUp()
        
        assertEquals('B', buffer.getCell(0, 0).char)
        assertEquals('C', buffer.getCell(1, 0).char)
        assertEquals(' ', buffer.getCell(2, 0).char)
        assertEquals(1, buffer.scrollbackSize)
        assertEquals('A', buffer.getScrollbackLine(0)?.getCell(0)?.char)
    }
    
    @Test
    fun `scrollback respects maxScrollback limit`() {
        val buffer = StyledBuffer(columns = 10, rows = 3, maxScrollback = 5)
        
        repeat(10) { i ->
            buffer.setCell(0, 0, TerminalCell('0' + i))
            buffer.scrollUp()
        }
        
        assertEquals(5, buffer.scrollbackSize)
        assertEquals('5', buffer.getScrollbackLine(0)?.getCell(0)?.char)
    }
    
    @Test
    fun `scrollDown shifts lines down`() {
        val buffer = StyledBuffer(columns = 10, rows = 3)
        buffer.setCell(0, 0, TerminalCell('A'))
        buffer.setCell(1, 0, TerminalCell('B'))
        
        buffer.scrollDown()
        
        assertEquals(' ', buffer.getCell(0, 0).char)
        assertEquals('A', buffer.getCell(1, 0).char)
        assertEquals('B', buffer.getCell(2, 0).char)
    }
    
    @Test
    fun `clearViewport resets all lines`() {
        val buffer = StyledBuffer(columns = 10, rows = 3)
        buffer.setCell(0, 0, TerminalCell('X'))
        buffer.setCell(2, 5, TerminalCell('Y'))
        
        buffer.clearViewport()
        
        assertEquals(' ', buffer.getCell(0, 0).char)
        assertEquals(' ', buffer.getCell(2, 5).char)
    }
    
    @Test
    fun `saveViewport and restoreViewport for alternate screen`() {
        val buffer = StyledBuffer(columns = 10, rows = 3)
        buffer.setCell(0, 0, TerminalCell('M'))
        
        buffer.saveViewport()
        assertTrue(buffer.hasSavedViewport)
        
        buffer.setCell(0, 0, TerminalCell('A'))
        assertEquals('A', buffer.getCell(0, 0).char)
        
        buffer.restoreViewport()
        assertFalse(buffer.hasSavedViewport)
        assertEquals('M', buffer.getCell(0, 0).char)
    }
    
    @Test
    fun `restoreViewport without save is no-op`() {
        val buffer = StyledBuffer()
        buffer.setCell(0, 0, TerminalCell('X'))
        buffer.restoreViewport()
        assertEquals('X', buffer.getCell(0, 0).char)
    }
    
    @Test
    fun `clearLine clears specified range`() {
        val buffer = StyledBuffer(columns = 10, rows = 3)
        for (i in 0 until 10) {
            buffer.setCell(0, i, TerminalCell('A'))
        }
        
        buffer.clearLine(0, fromCol = 3, toCol = 7)
        
        assertEquals('A', buffer.getCell(0, 0).char)
        assertEquals('A', buffer.getCell(0, 2).char)
        assertEquals(' ', buffer.getCell(0, 3).char)
        assertEquals(' ', buffer.getCell(0, 6).char)
        assertEquals('A', buffer.getCell(0, 7).char)
    }
    
    @Test
    fun `getScrollbackLine returns null for invalid index`() {
        val buffer = StyledBuffer()
        assertNull(buffer.getScrollbackLine(0))
        assertNull(buffer.getScrollbackLine(100))
    }
}
