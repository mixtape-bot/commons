package mixtape.commons.extensions

import kotlinx.coroutines.future.await
import me.devoxin.flight.api.Context
import mixtape.commons.jda.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.AudioChannel
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.requests.restaction.MessageAction
import net.dv8tion.jda.internal.requests.restaction.MessageActionImpl
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * The member's current [AudioChannel], if any.
 */
@Deprecated("VoiceChannels in JDA have been abstracted into a single class.", replaceWith = ReplaceWith("Context.audioChannel"))
public val Context.voiceChannel: AudioChannel?
    get() = member?.voiceState?.channel

/**
 * The member's current [AudioChannel], if any.
 */
public val Context.audioChannel: AudioChannel?
    get() = member?.voiceState?.channel

/**
 * Our current [AudioChannel], if any.
 */
@Deprecated("VoiceChannels in JDA have been abstracted into a single class.", replaceWith = ReplaceWith("Context.selfAudioChannel"))
public val Context.selfVoiceChannel: AudioChannel?
    get() = selfMember?.voiceState?.channel

/**
 * Our current [AudioChannel], if any.
 */
public val Context.selfAudioChannel: AudioChannel?
    get() = selfMember?.voiceState?.channel

/**
 * Us a Guild Member
 */
public val Context.selfMember: Member?
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
public fun Context.reply(content: String, mention: Boolean = false) {
    return getReplyAction(mention)
        .content(content)
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
public inline fun Context.reply(mention: Boolean = false, embed: EmbedBuilder.() -> Unit) {
    contract {
        callsInPlace(embed, InvocationKind.EXACTLY_ONCE)
    }

    return getReplyAction(mention)
        .setEmbeds(buildEmbed(embed))
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
public suspend fun Context.replyAsync(content: String, mention: Boolean = false): Message {
    return getReplyAction(mention)
        .content(content)
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
public suspend inline fun Context.replyAsync(mention: Boolean = false, embed: EmbedBuilder.() -> Unit): Message {
    contract {
        callsInPlace(embed, InvocationKind.EXACTLY_ONCE)
    }

    return getReplyAction(mention)
        .setEmbeds(buildEmbed(embed))
        .submit()
        .await()
}

@PublishedApi
internal fun Context.getReplyAction(mention: Boolean): MessageAction {
    val action = MessageActionImpl(jda, null, channel)
    if (guild == null || selfMember?.hasPermission(textChannel!!, Permission.MESSAGE_HISTORY) == true) {
        action.mentionRepliedUser(mention).reference(message)
    }

    return action
}
