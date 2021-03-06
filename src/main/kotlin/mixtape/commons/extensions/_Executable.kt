package mixtape.commons.extensions

import me.devoxin.flight.internal.arguments.types.Mentionable
import me.devoxin.flight.internal.arguments.types.Snowflake
import me.devoxin.flight.internal.entities.Executable
import net.dv8tion.jda.api.entities.*
import java.net.URL
import java.util.HashMap

public val argumentExamples: HashMap<Class<*>, String> = hashMapOf(
    /* kotlin things */
    String::class.java to "\"something\"",
    Int::class.java to "1",
    Long::class.java to "3",
    Double::class.java to "0.0",
    Float::class.java to "0.0",
    Boolean::class.java to "no",

    /* jda/discord */
    Snowflake::class.java to "396096412116320258",
    Mentionable::class.java to "@Member/@Role",
    Member::class.java to "@Member",
    User::class.java to "@User",
    Role::class.java to "@Role",
    TextChannel::class.java to "#general",
    VoiceChannel::class.java to "#!Music",

    /* java things */
    URL::class.java to "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
    java.lang.Integer::class.java to "0",
    java.lang.Long::class.java to "0",
    java.lang.Double::class.java to "0.0",
    java.lang.Float::class.java to "0.0",
    java.lang.Boolean::class.java to "yes",
)

/**
 * Generates a usage string for this Executable.
 *
 * Example: `<toBan: Member> [...reason: String]`
 *
 * @param withTypes
 *   Whether argument types should be included.
 */
public fun Executable.generateUsage(withTypes: Boolean = true): String {
    return arguments
        .joinToString(" ") { it.format(withTypes) }
        .trim()
}

/**
 * Generates an example for this Executable.
 *
 * ## Example
 *
 * ```
 * fun ban(member: Member, reason: String?) {}
 * ```
 *
 * would be
 *
 * `@Member "something"`
 */
public fun Executable.generateDefaultExample(): String =
    arguments
        .joinToString(" ") {
            argumentExamples[it.type]
                ?: if (it.type.isEnum) it.type.enumConstants.first().toString().lowercase()
                else "[unknown]"
        }
        .trim()
