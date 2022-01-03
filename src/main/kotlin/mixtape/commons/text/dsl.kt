package mixtape.commons.text

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public enum class PaddingDirection { Start, End }

internal fun String.pad(direction: PaddingDirection, length: Int, char: Char = ' ') =
    if (direction == PaddingDirection.Start) padStart(length, char) else padEnd(length, char)

@MarkdownDsl
public inline fun buildCode(build: CodeBuilder.() -> Unit): String {
    contract { callsInPlace(build, InvocationKind.EXACTLY_ONCE) }

    return CodeBuilder()
        .apply(build)
        .build()
}

@MarkdownDsl
public inline fun buildProlog(
    separator: Char = ':',
    direction: PaddingDirection = PaddingDirection.Start,
    build: PrologBuilder.() -> Unit
): String {
    contract { callsInPlace(build, InvocationKind.EXACTLY_ONCE) }

    return PrologBuilder(separator, direction)
        .apply(build)
        .build()
}
