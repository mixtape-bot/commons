package mixtape.commons.extensions

import mixtape.commons.jda.EmbedBuilder
import kotlinx.coroutines.future.await
import me.devoxin.flight.api.Context
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.VoiceChannel

/**
 * The member's current [VoiceChannel], if any.
 */
val Context.voiceChannel: VoiceChannel?
  get() = member?.voiceState?.channel

/**
 * Our current [VoiceChannel], if any.
 */
val Context.selfVoiceChannel: VoiceChannel?
  get() = selfMember?.voiceState?.channel

/**
 * Us a Guild Member
 */
val Context.selfMember: Member?
  get() = guild?.selfMember

/**
 * Convenience method for replying to [Context.message]
 *
 * @param content
 *   The message content
 *
 * @param mention
 *   Whether to mention we're replying to.
 */
fun Context.reply(content: String, mention: Boolean = false) {
  messageChannel.sendMessage(content)
    .apply {
      mentionRepliedUser(mention)
      reference(message)
      queue()
    }
}

/**
 * Convenience method for replying to [Context.message]
 *
 * @param mention
 *   Whether to mention the user we're replying to.
 *
 * @param builder
 *   The embed builder.
 */
fun Context.reply(mention: Boolean = false, builder: EmbedBuilder.() -> Unit) {
  messageChannel.sendMessage(EmbedBuilder().apply(builder).build())
    .apply {
      mentionRepliedUser(mention)
      reference(message)
      queue()
    }
}


/**
 * Same as [reply] byt async.
 *
 * @param content
 *   The message content
 *
 * @param mention
 *   Whether to mention we're replying to.
 */
suspend fun Context.replyAsync(content: String, mention: Boolean = false): Message {
  return messageChannel.sendMessage(content)
    .mentionRepliedUser(mention)
    .reference(message)
    .submit()
    .await()
}


/**
 * Same as [reply] but async.
 *
 * @param mention
 *   Whether to mention the user we're replying to.
 *
 * @param builder
 *   The embed builder.
 */
suspend fun Context.replyAsync(mention: Boolean = false, builder: EmbedBuilder.() -> Unit): Message {
  return messageChannel.sendMessage(EmbedBuilder().apply(builder).build())
    .mentionRepliedUser(mention)
    .reference(message)
    .submit().await()
}
