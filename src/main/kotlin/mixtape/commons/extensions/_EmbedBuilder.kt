package mixtape.commons.extensions

import mixtape.commons.jda.EmbedBuilder
import mixtape.commons.jda.EmbedBuilder.*
import net.dv8tion.jda.api.entities.MessageEmbed
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public inline fun buildEmbed(build: EmbedBuilder.() -> Unit): MessageEmbed {
    contract {
        callsInPlace(build, InvocationKind.EXACTLY_ONCE)
    }

    return EmbedBuilder()
        .apply(build)
        .build()
}

/**
 * Convenience method for configuring [Title.text] and [Title.url]
 *
 * @param text
 *   Text of the embed title.
 *
 * @param url
 *   URL of the title, makes it clickable.
 */
public fun EmbedBuilder.title(text: String, url: String? = null) {
    title {
        this.text = text
        this.url = url
    }
}

/**
 * Convenience method for configuring [Footer.text] and [Footer.iconUrl]
 *
 * @param text
 *   Text to use in the footer.
 *
 * @param iconUrl
 *   URL of the footer icon, if any.
 */
public fun EmbedBuilder.footer(text: String, iconUrl: String? = null) {
    footer {
        this.text = text
        this.iconUrl = iconUrl
    }
}

/**
 * Convenience method to configure [Image.url]
 *
 * @param url
 *   URL of the image to include
 */
public fun EmbedBuilder.image(url: String) {
    image {
        this.url = url
    }
}

/**
 * Convenience method to configure [Thumbnail.url]
 *
 * @param url
 *   URL of the image to include
 */
public fun EmbedBuilder.thumbnail(url: String) {
    thumbnail {
        this.url = url
    }
}

/**
 * Convenience method to configure different values [Author] easily.
 *
 * @param name
 *   Value to set
 */
public fun EmbedBuilder.author(name: String, url: String? = null, iconUrl: String? = null) {
    author {
        this.name = name
        this.url = url
        this.iconUrl = iconUrl
    }
}

/**
 * Convenience method for adding fields to the embed.
 *
 * @param name
 *   Name of the field, or [EmbedBuilder.ZERO_WIDTH_SPACE] if null.
 *
 * @param value
 *   Value of this field.
 *
 * @param inline
 *   Whether this field is inlined with the others.
 */
public fun EmbedBuilder.field(name: String?, value: Any, inline: Boolean = false) {
    field {
        this.name = name ?: EmbedBuilder.ZERO_WIDTH_SPACE
        this.value = value.toString()
        this.inline = inline
    }
}
