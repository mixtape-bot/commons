package gg.mixtape.commons.text

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

fun code(builder: CodeBlockBuilder.() -> Unit): String =
  CodeBlockBuilder().apply(builder).build()
