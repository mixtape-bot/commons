package gg.mixtape.commons.text

import java.lang.Integer.max

class ProLogBuilder {
  private val lines = ArrayList<Line>()

  /**
   * Add a new line.
   *
   * @param name
   *   the name of the line
   *
   * @param value
   *   the value of the line
   */
  fun addLine(name: String, value: Any): ProLogBuilder =
    addLine(Line(name, value))


  /**
   * Adds a new line.
   *
   * @param line
   *   The line to add.
   */
  fun addLine(line: Line): ProLogBuilder {
    lines.add(line)
    return this
  }

  /**
   * Adds a new blank line
   */
  fun addBlankLine(): ProLogBuilder {
    return addLine(Line("", ""))
  }

  /**
   * Build the prolog code block.
   */
  fun build(): String {
    val padding = this.calculatePadding()

    return code {
      language = "prolog"
      for (line in lines) {
        if (line.name.isNotEmpty() && line.value.toString().isNotEmpty()) {
          line("${line.name.padStart(padding, ' ')} : ${line.value.toString()}", false)
        }

        line()
      }
    }
  }

  /**
   * Calculate the amount of padding for line names.
   */
  private fun calculatePadding(): Int {
    var padding = 0
    for (line in lines) {
      padding = max(padding, line.name.length)
    }

    return padding
  }
}

data class Line(val name: String, val value: Any)

fun prolog(builder: ProLogBuilder.() -> Unit): String =
  ProLogBuilder().apply(builder).build()