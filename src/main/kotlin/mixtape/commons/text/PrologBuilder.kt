package mixtape.commons.text

@MarkdownDsl
public class PrologBuilder(
    private val separator: Char = ':',
    private val direction: PaddingDirection = PaddingDirection.Start
) {
    public var lines: MutableList<Pair<String, Any>> = mutableListOf()

    /**
     * Adds a new blank line
     */
    public fun line(): PrologBuilder {
        return line("" to "")
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
    public fun line(name: String, value: Any): PrologBuilder =
        line(name to value)


    /**
     * Adds a new line.
     *
     * @param line
     *   The line to add.
     */
    public fun line(line: Pair<String, Any>): PrologBuilder {
        lines.add(line)
        return this
    }

    /**
     * Build the prolog code block.
     */
    public fun build(): String {
        val padding = lines.maxOf { line -> line.first.length }

        return buildCode {
            language = "prolog"
            for (line in this@PrologBuilder.lines) {
                if (line.first.isNotEmpty() && line.second.toString().isNotEmpty()) {
                    val name = line.first.pad(this@PrologBuilder.direction, padding)
                    line("$name ${this@PrologBuilder.separator} ${line.second}")
                }
            }
        }
    }
}
