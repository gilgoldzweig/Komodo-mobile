package ca.glong.komodo.core.terminal.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AnsiParserTest {
    private val parser = AnsiParser()
    
    @Test
    fun `parses plain text`() {
        val results = parser.parse("Hello World")
        assertEquals(1, results.size)
        assertEquals(ParsedSequence.Text("Hello World"), results[0])
    }
    
    @Test
    fun `parses SGR reset`() {
        val results = parser.parse("\u001b[0m")
        assertEquals(1, results.size)
        val sgr = results[0] as ParsedSequence.Sgr
        assertEquals(listOf(0), sgr.params)
    }
    
    @Test
    fun `parses SGR bold`() {
        val results = parser.parse("\u001b[1m")
        assertEquals(1, results.size)
        val sgr = results[0] as ParsedSequence.Sgr
        assertEquals(listOf(1), sgr.params)
    }
    
    @Test
    fun `parses SGR with multiple params`() {
        val results = parser.parse("\u001b[1;31;42m")
        assertEquals(1, results.size)
        val sgr = results[0] as ParsedSequence.Sgr
        assertEquals(listOf(1, 31, 42), sgr.params)
    }
    
    @Test
    fun `parses 256-color foreground`() {
        val results = parser.parse("\u001b[38;5;196m")
        assertEquals(1, results.size)
        val sgr = results[0] as ParsedSequence.Sgr
        assertEquals(listOf(38, 5, 196), sgr.params)
    }
    
    @Test
    fun `parses cursor position`() {
        val results = parser.parse("\u001b[10;20H")
        assertEquals(1, results.size)
        val pos = results[0] as ParsedSequence.CursorPosition
        assertEquals(10, pos.row)
        assertEquals(20, pos.col)
    }
    
    @Test
    fun `parses cursor movement`() {
        assertEquals(ParsedSequence.CursorUp(5), parser.parse("\u001b[5A")[0])
        parser.reset()
        assertEquals(ParsedSequence.CursorDown(3), parser.parse("\u001b[3B")[0])
        parser.reset()
        assertEquals(ParsedSequence.CursorForward(2), parser.parse("\u001b[2C")[0])
        parser.reset()
        assertEquals(ParsedSequence.CursorBack(1), parser.parse("\u001b[1D")[0])
    }
    
    @Test
    fun `parses erase display`() {
        val results = parser.parse("\u001b[2J")
        assertEquals(1, results.size)
        assertEquals(ParsedSequence.EraseDisplay(2), results[0])
    }
    
    @Test
    fun `parses erase line`() {
        val results = parser.parse("\u001b[K")
        assertEquals(1, results.size)
        assertEquals(ParsedSequence.EraseLine(0), results[0])
    }
    
    @Test
    fun `parses mixed text and sequences`() {
        val results = parser.parse("Hello\u001b[1mBold\u001b[0mNormal")
        assertEquals(5, results.size)
        assertEquals(ParsedSequence.Text("Hello"), results[0])
        assertTrue(results[1] is ParsedSequence.Sgr)
        assertEquals(ParsedSequence.Text("Bold"), results[2])
        assertTrue(results[3] is ParsedSequence.Sgr)
        assertEquals(ParsedSequence.Text("Normal"), results[4])
    }
    
    @Test
    fun `parses control characters`() {
        val results = parser.parse("A\nB\rC\tD")
        assertEquals(7, results.size)
        assertEquals(ParsedSequence.Text("A"), results[0])
        assertEquals(ParsedSequence.LineFeed, results[1])
        assertEquals(ParsedSequence.Text("B"), results[2])
        assertEquals(ParsedSequence.CarriageReturn, results[3])
        assertEquals(ParsedSequence.Text("C"), results[4])
        assertEquals(ParsedSequence.Tab, results[5])
        assertEquals(ParsedSequence.Text("D"), results[6])
    }
    
    @Test
    fun `parses OSC title`() {
        val results = parser.parse("\u001b]0;My Title\u0007")
        assertEquals(1, results.size)
        val title = results[0] as ParsedSequence.Title
        assertEquals("My Title", title.title)
    }
    
    @Test
    fun `parses OSC 52 clipboard write`() {
        val results = parser.parse("\u001b]52;c;SGVsbG8=\u0007")
        assertEquals(1, results.size)
        val clip = results[0] as ParsedSequence.ClipboardWrite
        assertEquals("c", clip.selection)
        assertEquals("Hello", clip.data)
    }
    
    @Test
    fun `parses OSC 52 clipboard read request`() {
        val results = parser.parse("\u001b]52;c;\u0007")
        assertEquals(1, results.size)
        val clip = results[0] as ParsedSequence.ClipboardRead
        assertEquals("c", clip.selection)
    }
    
    @Test
    fun `handles malformed sequences gracefully`() {
        val results = parser.parse("\u001b[")
        assertTrue(results.isEmpty())
    }
    
    @Test
    fun `parses scroll commands`() {
        assertEquals(ParsedSequence.ScrollUp(3), parser.parse("\u001b[3S")[0])
        parser.reset()
        assertEquals(ParsedSequence.ScrollDown(2), parser.parse("\u001b[2T")[0])
    }
    
    @Test
    fun `parses set mode for alternate screen`() {
        val enable = parser.parse("\u001b[?1049h")
        assertEquals(1, enable.size)
        val setMode = enable[0] as ParsedSequence.SetMode
        assertEquals(1049, setMode.mode)
        assertEquals(true, setMode.enabled)
        
        parser.reset()
        
        val disable = parser.parse("\u001b[?1049l")
        assertEquals(1, disable.size)
        val clearMode = disable[0] as ParsedSequence.SetMode
        assertEquals(1049, clearMode.mode)
        assertEquals(false, clearMode.enabled)
    }
    
    @Test
    fun `default cursor movement is 1`() {
        assertEquals(ParsedSequence.CursorUp(1), parser.parse("\u001b[A")[0])
        parser.reset()
        assertEquals(ParsedSequence.CursorPosition(1, 1), parser.parse("\u001b[H")[0])
    }
}
