package ca.glong.komodo.core.terminal.buffer

class StyledLine(
    private val width: Int,
    private val defaultCell: TerminalCell = TerminalCell.EMPTY
) {
    private val cells = Array(width) { defaultCell }
    
    fun getCell(col: Int): TerminalCell {
        require(col in 0 until width) { "Column $col out of bounds [0, $width)" }
        return cells[col]
    }
    
    fun setCell(col: Int, cell: TerminalCell) {
        require(col in 0 until width) { "Column $col out of bounds [0, $width)" }
        cells[col] = cell
    }
    
    fun setChar(col: Int, char: Char, fg: TerminalColor, bg: TerminalColor, attrs: CellAttributes) {
        setCell(col, TerminalCell(char, fg, bg, attrs))
    }
    
    fun clear(fromCol: Int = 0, toCol: Int = width) {
        for (i in fromCol until toCol.coerceAtMost(width)) {
            cells[i] = defaultCell
        }
    }
    
    fun toText(): String = buildString {
        for (cell in cells) {
            append(cell.char)
        }
    }.trimEnd()
    
    fun copy(): StyledLine {
        val newLine = StyledLine(width, defaultCell)
        cells.copyInto(newLine.cells)
        return newLine
    }
    
    val size: Int get() = width
}
