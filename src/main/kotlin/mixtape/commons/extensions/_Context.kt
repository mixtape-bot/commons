package mixtape.commons.extensions

import kotlinx.coroutines.future.await
import me.devoxin.flight.api.command.Context
import me.devoxin.flight.api.command.message.MessageContext
import me.devoxin.flight.api.command.slash.SlashContext
import mixtape.commons.jda.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.interactions.InteractionHook

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
fun MessageContext.reply(content: String, mention: Boolean = false) {
    channel.sendMessage(content)
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
fun MessageContext.reply(mention: Boolean = false, builder: EmbedBuilder.() -> Unit) {
    channel.sendMessageEmbeds(buildEmbed(builder))
        .apply {
            mentionRepliedUser(mention)
            reference(message)
            queue()
        }
}

/**
 * Convenience method for replying to [Context.author]
 *
 * @param content
 *   The message content
 */
fun Context.reply(content: String) {
    if (this is SlashContext) reply(content) else if (this is MessageContext) reply(content)
}

/**
 * Convenience method for replying to [Context.author]
 *
 * @param builder
 *   The embed builder.
 */
fun Context.reply(builder: EmbedBuilder.() -> Unit) {
    if (this is SlashContext) reply(builder = builder) else if (this is MessageContext) reply(builder = builder)
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
suspend fun MessageContext.replyAsync(content: String, mention: Boolean = false): Message {
    return channel.sendMessage(content)
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
suspend fun MessageContext.replyAsync(mention: Boolean = false, builder: EmbedBuilder.() -> Unit): Message {
    return channel.sendMessageEmbeds(buildEmbed(builder))
        .mentionRepliedUser(mention)
        .reference(message)
        .submit().await()
}


/**
 * Convenience method for replying to [SlashContext.interaction]
 *
 * @param content
 *   The message content
 *
 * @param private
 *   Whether to mention we're replying to.
 */
fun SlashContext.reply(content: String, private: Boolean = false) {
    interaction.deferReply(private)
        .setContent(content)
        .queue()
}

/**
 * Convenience method for replying to [SlashContext.interaction]
 *
 * @param private
 *   Whether to privately reply to the user.
 *
 * @param builder
 *   The embed builder.
 */
fun SlashContext.reply(private: Boolean = false, builder: EmbedBuilder.() -> Unit) {
    interaction.deferReply(private)
        .addEmbeds(buildEmbed(builder))
        .queue()
}

/**
 * Same as [reply] byt async.
 *
 * @param content
 *   The message content
 *
 * @param mention
 *   Whether to privately reply to the user.
 */
suspend fun SlashContext.replyAsync(content: String, private: Boolean = false): InteractionHook {
    return interaction.deferReply(private)
        .setContent(content)
        .submit()
        .await()
}

/**
 * Same as [reply] but async.
 *
 * @param private
 *   Whether to privately reply to the user.
 *
 * @param builder
 *   The embed builder.
 */
suspend fun SlashContext.replyAsync(private: Boolean = false, builder: EmbedBuilder.() -> Unit): InteractionHook {
    return interaction.deferReply(private)
        .addEmbeds(buildEmbed(builder))
        .submit()
        .await()
}
