package mixtape.commons.text

import java.lang.Integer.max

class ProLogBuilder {
    var lines = mutableListOf<Line>()

    /**
     * Adds a new blank line
     */
    fun line(): ProLogBuilder {
        return line(Line("", ""))
    }

    /**
     * Add a new line.
     *
     * @param name
     *   the name of the line
     *
     * @param value
     *   the value of the line
     */
    fun line(name: String, value: Any): ProLogBuilder =
        line(Line(name, value))


    /**
     * Adds a new line.
     *
     * @param line
     *   The line to add.
     */
    fun line(line: Line): ProLogBuilder {
        lines.add(line)
        return this
    }

    /**
     * Build the prolog code block.
     */
    fun build(): String {
        val padding = this.calculatePadding()

        return buildCodeBlock {
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

fun buildProlog(builder: ProLogBuilder.() -> Unit): String =
    ProLogBuilder()
        .apply(builder)
        .build()
