package mixtape.commons.extensions

import kotlinx.coroutines.future.await
import me.devoxin.flight.api.Context
import mixtape.commons.jda.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.VoiceChannel
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

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
    return channel.sendMessage(content)
        .mentionRepliedUser(mention)
        .reference(message)
        .queue()
}

/**
 * Convenience method for replying to [Context.message]
 *
 * @param mention
 *   Whether to mention the user we're replying to.
 *
 * @param embed
 *   The embed builder.
 */
inline fun Context.reply(mention: Boolean = false, embed: EmbedBuilder.() -> Unit) {
    contract {
        callsInPlace(embed, InvocationKind.EXACTLY_ONCE)
    }

    return channel.sendMessageEmbeds(buildEmbed(embed))
        .mentionRepliedUser(mention)
        .reference(message)
        .queue()
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
 * @param embed
 *   The embed builder.
 */
suspend inline fun Context.replyAsync(mention: Boolean = false, embed: EmbedBuilder.() -> Unit): Message {
    contract {
        callsInPlace(embed, InvocationKind.EXACTLY_ONCE)
    }

    return channel.sendMessageEmbeds(buildEmbed(embed))
        .mentionRepliedUser(mention)
        .reference(message)
        .submit()
        .await()
}
