package ca.glong.komodo.core.terminal.parser

import ca.glong.komodo.core.terminal.buffer.CellAttributes
import ca.glong.komodo.core.terminal.buffer.TerminalColor

data class SgrState(
    val foreground: TerminalColor = TerminalColor.Default,
    val background: TerminalColor = TerminalColor.Default,
    val attributes: CellAttributes = CellAttributes.NONE
) {
    companion object {
        val DEFAULT = SgrState()
    }
}

class SgrInterpreter {
    private var state = SgrState.DEFAULT
    
    val currentState: SgrState get() = state
    
    fun apply(params: List<Int>): SgrState {
        var i = 0
        var newFg = state.foreground
        var newBg = state.background
        var newAttrs = state.attributes
        
        while (i < params.size) {
            when (val code = params[i]) {
                0 -> {
                    newFg = TerminalColor.Default
                    newBg = TerminalColor.Default
                    newAttrs = CellAttributes.NONE
                }
                1 -> newAttrs = newAttrs.with(CellAttributes.BOLD)
                3 -> newAttrs = newAttrs.with(CellAttributes.ITALIC)
                4 -> newAttrs = newAttrs.with(CellAttributes.UNDERLINE)
                5 -> newAttrs = newAttrs.with(CellAttributes.BLINK)
                7 -> newAttrs = newAttrs.with(CellAttributes.INVERSE)
                8 -> newAttrs = newAttrs.with(CellAttributes.HIDDEN)
                9 -> newAttrs = newAttrs.with(CellAttributes.STRIKETHROUGH)
                
                21 -> newAttrs = newAttrs.without(CellAttributes.BOLD)
                22 -> newAttrs = newAttrs.without(CellAttributes.BOLD)
                23 -> newAttrs = newAttrs.without(CellAttributes.ITALIC)
                24 -> newAttrs = newAttrs.without(CellAttributes.UNDERLINE)
                25 -> newAttrs = newAttrs.without(CellAttributes.BLINK)
                27 -> newAttrs = newAttrs.without(CellAttributes.INVERSE)
                28 -> newAttrs = newAttrs.without(CellAttributes.HIDDEN)
                29 -> newAttrs = newAttrs.without(CellAttributes.STRIKETHROUGH)
                
                in 30..37 -> newFg = TerminalColor.Indexed(code - 30)
                38 -> {
                    val (color, consumed) = parse256OrRgb(params, i + 1)
                    color?.let { newFg = it }
                    i += consumed
                }
                39 -> newFg = TerminalColor.Default
                
                in 40..47 -> newBg = TerminalColor.Indexed(code - 40)
                48 -> {
                    val (color, consumed) = parse256OrRgb(params, i + 1)
                    color?.let { newBg = it }
                    i += consumed
                }
                49 -> newBg = TerminalColor.Default
                
                in 90..97 -> newFg = TerminalColor.Indexed(code - 90 + 8)
                in 100..107 -> newBg = TerminalColor.Indexed(code - 100 + 8)
            }
            i++
        }
        
        state = SgrState(newFg, newBg, newAttrs)
        return state
    }
    
    private fun parse256OrRgb(params: List<Int>, startIndex: Int): Pair<TerminalColor?, Int> {
        if (startIndex >= params.size) return null to 0
        
        return when (params[startIndex]) {
            5 -> {
                if (startIndex + 1 < params.size) {
                    TerminalColor.Indexed(params[startIndex + 1]) to 2
                } else {
                    null to 1
                }
            }
            2 -> {
                if (startIndex + 3 < params.size) {
                    TerminalColor.Rgb(
                        params[startIndex + 1],
                        params[startIndex + 2],
                        params[startIndex + 3]
                    ) to 4
                } else {
                    null to 1
                }
            }
            else -> null to 0
        }
    }
    
    fun reset() {
        state = SgrState.DEFAULT
    }
}
