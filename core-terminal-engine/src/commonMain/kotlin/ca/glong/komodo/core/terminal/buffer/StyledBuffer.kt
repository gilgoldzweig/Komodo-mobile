package ca.glong.komodo.core.terminal.buffer

class StyledBuffer(
    val columns: Int = 80,
    val rows: Int = 24,
    val maxScrollback: Int = 10_000
) {
    private val viewport = Array(rows) { StyledLine(columns) }
    private val scrollback = ArrayDeque<StyledLine>()
    private var savedViewport: Array<StyledLine>? = null
    
    fun getLine(row: Int): StyledLine {
        require(row in 0 until rows) { "Row $row out of bounds [0, $rows)" }
        return viewport[row]
    }
    
    fun setCell(row: Int, col: Int, cell: TerminalCell) {
        getLine(row).setCell(col, cell)
    }
    
    fun getCell(row: Int, col: Int): TerminalCell = getLine(row).getCell(col)
    
    fun scrollUp(lineCount: Int = 1) {
        repeat(lineCount) {
            scrollback.addLast(viewport[0].copy())
            while (scrollback.size > maxScrollback) {
                scrollback.removeFirst()
            }
            for (i in 0 until rows - 1) {
                viewport[i] = viewport[i + 1]
            }
            viewport[rows - 1] = StyledLine(columns)
        }
    }
    
    fun scrollDown(lineCount: Int = 1) {
        repeat(lineCount) {
            for (i in (rows - 1) downTo 1) {
                viewport[i] = viewport[i - 1]
            }
            viewport[0] = StyledLine(columns)
        }
    }
    
    fun clearLine(row: Int, fromCol: Int = 0, toCol: Int = columns) {
        getLine(row).clear(fromCol, toCol)
    }
    
    fun clearViewport() {
        for (i in 0 until rows) {
            viewport[i] = StyledLine(columns)
        }
    }
    
    fun clearScrollback() {
        scrollback.clear()
    }
    
    fun clearAll() {
        clearViewport()
        clearScrollback()
    }
    
    fun saveViewport() {
        savedViewport = Array(rows) { viewport[it].copy() }
    }
    
    fun restoreViewport() {
        savedViewport?.let { saved ->
            for (i in 0 until rows) {
                viewport[i] = saved[i]
            }
        }
        savedViewport = null
    }
    
    fun getScrollbackLine(index: Int): StyledLine? = scrollback.getOrNull(index)
    
    val scrollbackSize: Int get() = scrollback.size
    val hasSavedViewport: Boolean get() = savedViewport != null
}
