package mixtape.commons.extensions

import me.devoxin.flight.internal.arguments.types.Snowflake
import me.devoxin.flight.internal.entities.Executable
import net.dv8tion.jda.api.entities.*
import java.net.URL

val argumentExamples = hashMapOf(
  /* kotlin things */
  String::class.java to "\"some thing\"",
  Int::class to "1",
  Long::class.java to "3",
  Double::class.java to "0.0",
  Boolean::class.java to "no",

  /* jda/discord */
  Snowflake::class.java to "396096412116320258",
  Member::class.java to "@Member",
  User::class.java to "@User",
  Role::class.java to "@DJ",
  TextChannel::class.java to "#general",
  VoiceChannel::class.java to "Music",

  /* java things */
  URL::class.java to "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
  java.lang.Long::class.java to "0",
  java.lang.Double::class.java to "0.0",
  java.lang.Boolean::class.java to "yes",
)

/**
 * Generates a usage string for this Executable.
 *
 * Example: `<toBan: Member> [reason: String]`
 *
 * @param withTypes
 *   Whether argument types should be included.
 */
fun Executable.generateUsage(withTypes: Boolean = true): String {
  val usage = buildString {
    arguments.forEach { append("${it.format(withTypes)} ") }
  }

  return usage.trim()
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
 * `@Member "some thing"`
 */
fun Executable.generateDefaultExample(): String =
  arguments
    .joinToString(" ") {
      argumentExamples[it.type]
        ?: if (it.type.isEnum) it.type.enumConstants.first().toString().toLowerCase()
        else "[unknown]"
    }
    .trim()
