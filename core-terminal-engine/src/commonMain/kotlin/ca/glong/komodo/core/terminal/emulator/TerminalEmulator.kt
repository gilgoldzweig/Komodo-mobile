package ca.glong.komodo.core.terminal.emulator

import ca.glong.komodo.core.terminal.buffer.CellAttributes
import ca.glong.komodo.core.terminal.buffer.StyledBuffer
import ca.glong.komodo.core.terminal.buffer.TerminalCell
import ca.glong.komodo.core.terminal.buffer.TerminalColor
import ca.glong.komodo.core.terminal.parser.AnsiParser
import ca.glong.komodo.core.terminal.parser.ParsedSequence
import ca.glong.komodo.core.terminal.parser.SgrInterpreter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class TerminalEvent {
    data class ClipboardWrite(val selection: String, val data: String) : TerminalEvent()
    data class ClipboardRead(val selection: String) : TerminalEvent()
    data class TitleChanged(val title: String) : TerminalEvent()
    data object Bell : TerminalEvent()
}

enum class TerminalContext {
    NORMAL,
    EDITOR
}

data class CursorState(
    val row: Int = 0,
    val col: Int = 0,
    val visible: Boolean = true
)

class TerminalEmulator(
    val columns: Int = 80,
    val rows: Int = 24,
    maxScrollback: Int = 10_000
) {
    private val buffer = StyledBuffer(columns, rows, maxScrollback)
    private val parser = AnsiParser()
    private val sgrInterpreter = SgrInterpreter()
    
    private val _cursor = MutableStateFlow(CursorState())
    val cursor: StateFlow<CursorState> = _cursor.asStateFlow()
    
    private val _context = MutableStateFlow(TerminalContext.NORMAL)
    val context: StateFlow<TerminalContext> = _context.asStateFlow()
    
    private val _events = MutableSharedFlow<TerminalEvent>()
    val events: SharedFlow<TerminalEvent> = _events.asSharedFlow()
    
    private val _dirty = MutableStateFlow(0L)
    val dirty: StateFlow<Long> = _dirty.asStateFlow()
    
    private var scrollRegionTop = 0
    private var scrollRegionBottom = rows - 1
    private var originMode = false
    private var wrapMode = true
    
    fun write(data: String) {
        val sequences = parser.parse(data)
        for (seq in sequences) {
            processSequence(seq)
        }
        _dirty.value = System.currentTimeMillis()
    }
    
    private fun processSequence(seq: ParsedSequence) {
        when (seq) {
            is ParsedSequence.Text -> writeText(seq.text)
            is ParsedSequence.Sgr -> sgrInterpreter.apply(seq.params)
            is ParsedSequence.CursorPosition -> moveCursor(seq.row - 1, seq.col - 1)
            is ParsedSequence.CursorUp -> moveCursorRelative(-seq.count, 0)
            is ParsedSequence.CursorDown -> moveCursorRelative(seq.count, 0)
            is ParsedSequence.CursorForward -> moveCursorRelative(0, seq.count)
            is ParsedSequence.CursorBack -> moveCursorRelative(0, -seq.count)
            is ParsedSequence.CursorColumn -> moveCursor(_cursor.value.row, seq.col - 1)
            is ParsedSequence.EraseDisplay -> eraseDisplay(seq.mode)
            is ParsedSequence.EraseLine -> eraseLine(seq.mode)
            is ParsedSequence.ScrollUp -> scrollUp(seq.count)
            is ParsedSequence.ScrollDown -> scrollDown(seq.count)
            is ParsedSequence.SetMode -> handleSetMode(seq.mode, seq.enabled)
            is ParsedSequence.LineFeed -> lineFeed()
            is ParsedSequence.CarriageReturn -> carriageReturn()
            is ParsedSequence.Tab -> tab()
            is ParsedSequence.Backspace -> backspace()
            is ParsedSequence.Bell -> emitEvent(TerminalEvent.Bell)
            is ParsedSequence.Title -> emitEvent(TerminalEvent.TitleChanged(seq.title))
            is ParsedSequence.ClipboardWrite -> emitEvent(TerminalEvent.ClipboardWrite(seq.selection, seq.data))
            is ParsedSequence.ClipboardRead -> emitEvent(TerminalEvent.ClipboardRead(seq.selection))
            is ParsedSequence.Unknown -> { }
        }
    }
    
    private fun writeText(text: String) {
        val state = sgrInterpreter.currentState
        for (char in text) {
            if (_cursor.value.col >= columns) {
                if (wrapMode) {
                    carriageReturn()
                    lineFeed()
                } else {
                    continue
                }
            }
            buffer.setCell(
                _cursor.value.row,
                _cursor.value.col,
                TerminalCell(char, state.foreground, state.background, state.attributes)
            )
            _cursor.value = _cursor.value.copy(col = _cursor.value.col + 1)
        }
    }
    
    private fun moveCursor(row: Int, col: Int) {
        val newRow = row.coerceIn(0, rows - 1)
        val newCol = col.coerceIn(0, columns - 1)
        _cursor.value = _cursor.value.copy(row = newRow, col = newCol)
    }
    
    private fun moveCursorRelative(rowDelta: Int, colDelta: Int) {
        moveCursor(_cursor.value.row + rowDelta, _cursor.value.col + colDelta)
    }
    
    private fun lineFeed() {
        if (_cursor.value.row >= scrollRegionBottom) {
            buffer.scrollUp()
        } else {
            _cursor.value = _cursor.value.copy(row = _cursor.value.row + 1)
        }
    }
    
    private fun carriageReturn() {
        _cursor.value = _cursor.value.copy(col = 0)
    }
    
    private fun tab() {
        val nextTab = ((_cursor.value.col / 8) + 1) * 8
        _cursor.value = _cursor.value.copy(col = nextTab.coerceAtMost(columns - 1))
    }
    
    private fun backspace() {
        if (_cursor.value.col > 0) {
            _cursor.value = _cursor.value.copy(col = _cursor.value.col - 1)
        }
    }
    
    private fun eraseDisplay(mode: Int) {
        when (mode) {
            0 -> {
                eraseLine(0)
                for (row in (_cursor.value.row + 1) until rows) {
                    buffer.clearLine(row)
                }
            }
            1 -> {
                eraseLine(1)
                for (row in 0 until _cursor.value.row) {
                    buffer.clearLine(row)
                }
            }
            2, 3 -> buffer.clearViewport()
        }
    }
    
    private fun eraseLine(mode: Int) {
        when (mode) {
            0 -> buffer.clearLine(_cursor.value.row, _cursor.value.col, columns)
            1 -> buffer.clearLine(_cursor.value.row, 0, _cursor.value.col + 1)
            2 -> buffer.clearLine(_cursor.value.row)
        }
    }
    
    private fun scrollUp(count: Int) {
        repeat(count) { buffer.scrollUp() }
    }
    
    private fun scrollDown(count: Int) {
        repeat(count) { buffer.scrollDown() }
    }
    
    private fun handleSetMode(mode: Int, enabled: Boolean) {
        when (mode) {
            25 -> _cursor.value = _cursor.value.copy(visible = enabled)
            1049 -> {
                if (enabled) {
                    buffer.saveViewport()
                    buffer.clearViewport()
                    _context.value = TerminalContext.EDITOR
                } else {
                    buffer.restoreViewport()
                    _context.value = TerminalContext.NORMAL
                }
            }
            7 -> wrapMode = enabled
        }
    }
    
    private fun emitEvent(event: TerminalEvent) {
        _events.tryEmit(event)
    }
    
    fun getCell(row: Int, col: Int): TerminalCell = buffer.getCell(row, col)
    
    fun getLine(row: Int) = buffer.getLine(row)
    
    fun getLineText(row: Int): String = buffer.getLine(row).toText()
    
    fun getScrollbackLine(index: Int) = buffer.getScrollbackLine(index)
    
    val scrollbackSize: Int get() = buffer.scrollbackSize
    
    fun reset() {
        parser.reset()
        sgrInterpreter.reset()
        buffer.clearAll()
        _cursor.value = CursorState()
        _context.value = TerminalContext.NORMAL
        scrollRegionTop = 0
        scrollRegionBottom = rows - 1
        originMode = false
        wrapMode = true
        _dirty.value = System.currentTimeMillis()
    }
}
