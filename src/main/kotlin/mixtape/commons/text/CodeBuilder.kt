package mixtape.commons.text

@MarkdownDsl
public class CodeBuilder {
    private var lines: MutableList<String> = mutableListOf()

    /**
     * The language of the code block
     */
    public var language: String = ""

    /**
     * Adds a new line to the code block.
     */
    public fun line(text: String = "") {
        lines += text
    }

    /**
     * Adds this string as a code block line.
     */
    public operator fun String.unaryPlus() {
        lines += this
    }

    /**
     * Builds the markdown.
     */
    public fun build(): String = "```$language\n${lines.joinToString("\n")}\n```"
}
