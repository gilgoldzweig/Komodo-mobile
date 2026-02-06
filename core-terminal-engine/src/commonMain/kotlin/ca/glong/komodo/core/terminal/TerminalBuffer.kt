package ca.glong.komodo.core.terminal

class TerminalBuffer(val maxLines: Int = 1000) {
    private val lines = ArrayDeque<String>()
    
    val lineCount: Int
        get() = lines.size
    
    fun isEmpty(): Boolean = lines.isEmpty()
    
    fun addLine(text: String) {
        val splitLines = text.split("\n")
        for (line in splitLines) {
            lines.addLast(line)
            if (lines.size > maxLines) {
                lines.removeFirst()
            }
        }
    }
    
    fun appendToCurrentLine(text: String) {
        if (lines.isEmpty()) {
            lines.addLast(text)
        } else {
            lines[lines.size - 1] = lines[lines.size - 1] + text
        }
    }
    
    fun getLine(index: Int): String {
        return lines[index]
    }
    
    fun getLines(): List<String> {
        return lines.toList()
    }
    
    fun getLines(startIndex: Int, count: Int): List<String> {
        val endIndex = minOf(startIndex + count, lines.size)
        if (startIndex >= lines.size) {
            return emptyList()
        }
        return lines.toList().subList(startIndex, endIndex)
    }
    
    fun getLastLines(count: Int): List<String> {
        val startIndex = maxOf(0, lines.size - count)
        return getLines(startIndex, count)
    }
    
    fun clear() {
        lines.clear()
    }
}
