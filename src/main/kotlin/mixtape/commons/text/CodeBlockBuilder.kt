package mixtape.commons.text

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class CodeBlockBuilder {
    var language: String = ""

    private var lines: MutableList<String> = mutableListOf()

    fun line(text: String = "", newLine: Boolean = true) =
        lines.add("$text${if (newLine) "\n" else ""}")

    fun build(): String =
        buildString {
            appendLine("```$language")
            for (line in lines) {
                append(line)
            }

            append("\n```")
        }
}

inline fun buildCodeBlock(builder: CodeBlockBuilder.() -> Unit): String {
    contract {
        callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
    }

    return CodeBlockBuilder()
        .apply(builder)
        .build()
}
