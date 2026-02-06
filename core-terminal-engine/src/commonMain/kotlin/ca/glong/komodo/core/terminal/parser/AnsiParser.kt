package ca.glong.komodo.core.terminal.parser

import ca.glong.komodo.core.terminal.buffer.CellAttributes
import ca.glong.komodo.core.terminal.buffer.TerminalColor

sealed class ParsedSequence {
    data class Text(val text: String) : ParsedSequence()
    data class Sgr(val params: List<Int>) : ParsedSequence()
    data class CursorPosition(val row: Int, val col: Int) : ParsedSequence()
    data class CursorUp(val count: Int) : ParsedSequence()
    data class CursorDown(val count: Int) : ParsedSequence()
    data class CursorForward(val count: Int) : ParsedSequence()
    data class CursorBack(val count: Int) : ParsedSequence()
    data class CursorColumn(val col: Int) : ParsedSequence()
    data class EraseDisplay(val mode: Int) : ParsedSequence()
    data class EraseLine(val mode: Int) : ParsedSequence()
    data class ScrollUp(val count: Int) : ParsedSequence()
    data class ScrollDown(val count: Int) : ParsedSequence()
    data class SetMode(val mode: Int, val enabled: Boolean) : ParsedSequence()
    data class ClipboardWrite(val selection: String, val data: String) : ParsedSequence()
    data class ClipboardRead(val selection: String) : ParsedSequence()
    data class Title(val title: String) : ParsedSequence()
    data object Bell : ParsedSequence()
    data object Backspace : ParsedSequence()
    data object Tab : ParsedSequence()
    data object LineFeed : ParsedSequence()
    data object CarriageReturn : ParsedSequence()
    data class Unknown(val sequence: String) : ParsedSequence()
}

class AnsiParser {
    private var state: State = State.Ground
    private val buffer = StringBuilder()
    private val params = mutableListOf<Int>()
    private var currentParam = 0
    private var oscBuffer = StringBuilder()
    
    private enum class State {
        Ground,
        Escape,
        Csi,
        CsiParam,
        Osc,
        OscString
    }
    
    fun parse(input: String): List<ParsedSequence> {
        val results = mutableListOf<ParsedSequence>()
        
        for (char in input) {
            when (state) {
                State.Ground -> handleGround(char, results)
                State.Escape -> handleEscape(char, results)
                State.Csi, State.CsiParam -> handleCsi(char, results)
                State.Osc -> handleOsc(char, results)
                State.OscString -> handleOscString(char, results)
            }
        }
        
        flushTextBuffer(results)
        return results
    }
    
    private fun handleGround(char: Char, results: MutableList<ParsedSequence>) {
        when (char) {
            '\u001b' -> {
                flushTextBuffer(results)
                state = State.Escape
            }
            '\u0007' -> {
                flushTextBuffer(results)
                results.add(ParsedSequence.Bell)
            }
            '\b' -> {
                flushTextBuffer(results)
                results.add(ParsedSequence.Backspace)
            }
            '\t' -> {
                flushTextBuffer(results)
                results.add(ParsedSequence.Tab)
            }
            '\n' -> {
                flushTextBuffer(results)
                results.add(ParsedSequence.LineFeed)
            }
            '\r' -> {
                flushTextBuffer(results)
                results.add(ParsedSequence.CarriageReturn)
            }
            else -> buffer.append(char)
        }
    }
    
    private fun handleEscape(char: Char, results: MutableList<ParsedSequence>) {
        when (char) {
            '[' -> {
                state = State.Csi
                params.clear()
                currentParam = 0
            }
            ']' -> {
                state = State.Osc
                oscBuffer.clear()
            }
            else -> {
                results.add(ParsedSequence.Unknown("\u001b$char"))
                state = State.Ground
            }
        }
    }
    
    private fun handleCsi(char: Char, results: MutableList<ParsedSequence>) {
        when {
            char in '0'..'9' -> {
                currentParam = currentParam * 10 + (char - '0')
                state = State.CsiParam
            }
            char == ';' -> {
                params.add(currentParam)
                currentParam = 0
                state = State.CsiParam
            }
            char == '?' -> {
                state = State.CsiParam
            }
            char.isLetter() -> {
                params.add(currentParam)
                results.add(parseCsiCommand(char, params))
                state = State.Ground
            }
            else -> {
                state = State.Ground
            }
        }
    }
    
    private fun handleOsc(char: Char, results: MutableList<ParsedSequence>) {
        when (char) {
            '\u0007' -> {
                results.add(parseOscSequence(oscBuffer.toString()))
                state = State.Ground
            }
            '\u001b' -> state = State.OscString
            else -> {
                oscBuffer.append(char)
                state = State.Osc
            }
        }
    }
    
    private fun handleOscString(char: Char, results: MutableList<ParsedSequence>) {
        if (char == '\\') {
            results.add(parseOscSequence(oscBuffer.toString()))
        }
        state = State.Ground
    }
    
    private fun parseCsiCommand(command: Char, params: List<Int>): ParsedSequence {
        val p0 = params.getOrElse(0) { 0 }
        val p1 = params.getOrElse(1) { 0 }
        
        return when (command) {
            'm' -> ParsedSequence.Sgr(params.toList())
            'H', 'f' -> ParsedSequence.CursorPosition(
                row = if (p0 == 0) 1 else p0,
                col = if (p1 == 0) 1 else p1
            )
            'A' -> ParsedSequence.CursorUp(if (p0 == 0) 1 else p0)
            'B' -> ParsedSequence.CursorDown(if (p0 == 0) 1 else p0)
            'C' -> ParsedSequence.CursorForward(if (p0 == 0) 1 else p0)
            'D' -> ParsedSequence.CursorBack(if (p0 == 0) 1 else p0)
            'G' -> ParsedSequence.CursorColumn(if (p0 == 0) 1 else p0)
            'J' -> ParsedSequence.EraseDisplay(p0)
            'K' -> ParsedSequence.EraseLine(p0)
            'S' -> ParsedSequence.ScrollUp(if (p0 == 0) 1 else p0)
            'T' -> ParsedSequence.ScrollDown(if (p0 == 0) 1 else p0)
            'h' -> ParsedSequence.SetMode(p0, enabled = true)
            'l' -> ParsedSequence.SetMode(p0, enabled = false)
            else -> ParsedSequence.Unknown("\u001b[${params.joinToString(";")}$command")
        }
    }
    
    private fun parseOscSequence(content: String): ParsedSequence {
        val parts = content.split(';', limit = 3)
        val code = parts.getOrNull(0)?.toIntOrNull() ?: return ParsedSequence.Unknown("\u001b]$content")
        
        return when (code) {
            0, 1, 2 -> ParsedSequence.Title(parts.getOrElse(1) { "" })
            52 -> {
                val selection = parts.getOrElse(1) { "c" }
                val base64Data = parts.getOrElse(2) { "" }
                if (base64Data.isEmpty()) {
                    ParsedSequence.ClipboardRead(selection)
                } else {
                    val decoded = try {
                        base64Data.decodeBase64()
                    } catch (_: Exception) {
                        ""
                    }
                    ParsedSequence.ClipboardWrite(selection, decoded)
                }
            }
            else -> ParsedSequence.Unknown("\u001b]$content")
        }
    }
    
    private fun flushTextBuffer(results: MutableList<ParsedSequence>) {
        if (buffer.isNotEmpty()) {
            results.add(ParsedSequence.Text(buffer.toString()))
            buffer.clear()
        }
    }
    
    fun reset() {
        state = State.Ground
        buffer.clear()
        params.clear()
        currentParam = 0
        oscBuffer.clear()
    }
}

private fun String.decodeBase64(): String {
    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    
    val cleanInput = this.filter { it in alphabet || it == '=' }
    if (cleanInput.isEmpty()) return ""
    
    val bytes = mutableListOf<Byte>()
    var i = 0
    while (i + 3 < cleanInput.length) {
        val c1 = alphabet.indexOf(cleanInput[i])
        val c2 = alphabet.indexOf(cleanInput[i + 1])
        val c3 = if (cleanInput[i + 2] == '=') -1 else alphabet.indexOf(cleanInput[i + 2])
        val c4 = if (cleanInput[i + 3] == '=') -1 else alphabet.indexOf(cleanInput[i + 3])
        
        bytes.add(((c1 shl 2) or (c2 shr 4)).toByte())
        if (c3 != -1) {
            bytes.add((((c2 and 0x0F) shl 4) or (c3 shr 2)).toByte())
        }
        if (c4 != -1) {
            bytes.add((((c3 and 0x03) shl 6) or c4).toByte())
        }
        i += 4
    }
    
    return bytes.toByteArray().decodeToString()
}
