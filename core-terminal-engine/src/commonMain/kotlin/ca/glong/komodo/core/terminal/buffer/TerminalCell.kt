package ca.glong.komodo.core.terminal.buffer

data class TerminalCell(
    val char: Char = ' ',
    val foreground: TerminalColor = TerminalColor.Default,
    val background: TerminalColor = TerminalColor.Default,
    val attributes: CellAttributes = CellAttributes.NONE
) {
    companion object {
        val EMPTY = TerminalCell()
    }
}

sealed class TerminalColor {
    data object Default : TerminalColor()
    data class Indexed(val index: Int) : TerminalColor()
    data class Rgb(val r: Int, val g: Int, val b: Int) : TerminalColor()
    
    companion object {
        val BLACK = Indexed(0)
        val RED = Indexed(1)
        val GREEN = Indexed(2)
        val YELLOW = Indexed(3)
        val BLUE = Indexed(4)
        val MAGENTA = Indexed(5)
        val CYAN = Indexed(6)
        val WHITE = Indexed(7)
        
        val BRIGHT_BLACK = Indexed(8)
        val BRIGHT_RED = Indexed(9)
        val BRIGHT_GREEN = Indexed(10)
        val BRIGHT_YELLOW = Indexed(11)
        val BRIGHT_BLUE = Indexed(12)
        val BRIGHT_MAGENTA = Indexed(13)
        val BRIGHT_CYAN = Indexed(14)
        val BRIGHT_WHITE = Indexed(15)
    }
}

@JvmInline
value class CellAttributes(val bits: Int) {
    val bold: Boolean get() = bits and BOLD != 0
    val italic: Boolean get() = bits and ITALIC != 0
    val underline: Boolean get() = bits and UNDERLINE != 0
    val blink: Boolean get() = bits and BLINK != 0
    val inverse: Boolean get() = bits and INVERSE != 0
    val hidden: Boolean get() = bits and HIDDEN != 0
    val strikethrough: Boolean get() = bits and STRIKETHROUGH != 0
    
    fun with(flag: Int): CellAttributes = CellAttributes(bits or flag)
    fun without(flag: Int): CellAttributes = CellAttributes(bits and flag.inv())
    
    companion object {
        const val BOLD = 1
        const val ITALIC = 2
        const val UNDERLINE = 4
        const val BLINK = 8
        const val INVERSE = 16
        const val HIDDEN = 32
        const val STRIKETHROUGH = 64
        
        val NONE = CellAttributes(0)
    }
}
