package mixtape.commons.jda

import net.dv8tion.jda.api.entities.EmbedType
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.Role
import java.net.URL
import java.time.OffsetDateTime
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public open class EmbedBuilder {
    public companion object {
        public const val ZERO_WIDTH_SPACE: String = "\u200b"

        public const val MAX_TITLE_LENGTH: Int = 256
        public const val MAX_FIELD_NAME_LENGTH: Int = 256
        public const val MAX_FIELD_VALUE_LENGTH: Int = 1024
        public const val MAX_AUTHOR_NAME_LENGTH: Int = 256
        public const val MAX_FOOTER_TEXT_LENGTH: Int = 2048
        public const val MAX_DESCRIPTION_LENGTH: Int = 4096
    }

    /**
     * All fields in the embed.
     */
    public var fields: MutableList<Field> = mutableListOf()

    /**
     * Information of the embed author.
     */
    public var author: Author? = null

    /**
     * Title information, if any.
     */
    public var title: Title? = null

    /**
     * Image information of the embed.
     */
    public var image: Image? = null

    /**
     * Thumbnail information of the embed.
     */
    public var thumbnail: Thumbnail? = null

    /**
     * Footer information of the embed.
     */
    public var footer: Footer? = null

    /**
     * The color of this embed, defaults to [Role.DEFAULT_COLOR_RAW]
     */
    public var color: Int = Role.DEFAULT_COLOR_RAW

    /**
     * The description text for this embed.
     */
    public var description: String? = null

    /**
     * The timestamp of this embed.
     */
    public var timestamp: OffsetDateTime? = null

    /**
     * Sets the [Title] of this embed using [builder].
     *
     * @param builder
     *   Configures the [Title]
     */
    public inline fun title(builder: Title.() -> Unit) {
        contract { callsInPlace(builder, InvocationKind.EXACTLY_ONCE) }

        title = Title().apply(builder)
    }

    /**
     * Sets the [Footer] of this embed using [builder].
     *
     * @param builder
     *   Configures the [Footer]
     */
    public inline fun footer(builder: Footer.() -> Unit) {
        contract { callsInPlace(builder, InvocationKind.EXACTLY_ONCE) }

        footer = Footer().apply(builder)
    }

    /**
     * Sets [Image] of this embed using [builder].
     *
     * @param builder
     *   Configures the [Image]
     */
    public inline fun image(builder: Image.() -> Unit) {
        contract { callsInPlace(builder, InvocationKind.EXACTLY_ONCE) }

        image = Image().apply(builder)
    }

    /**
     * Sets the thumbnail of this embed
     *
     * @param builder
     *   Configures the thumbnail data
     */
    public inline fun thumbnail(builder: Thumbnail.() -> Unit) {
        contract { callsInPlace(builder, InvocationKind.EXACTLY_ONCE) }

        thumbnail = Thumbnail().apply(builder)
    }

    /**
     * Sets the [Author] of the embed.
     *
     * @param builder
     *   Configures the [Author]
     */
    public inline fun author(builder: Author.() -> Unit) {
        contract { callsInPlace(builder, InvocationKind.EXACTLY_ONCE) }

        author = Author().apply(builder)
    }

    /**
     * Adds a new [Field] to this embed.
     *
     * @param builder
     *   Configures the [Field]
     */
    public inline fun field(builder: Field.() -> Unit) {
        contract { callsInPlace(builder, InvocationKind.EXACTLY_ONCE) }

        require (fields.size < 25)

        fields.add(Field().apply(builder))
    }

    public class Footer {
        /**
         * Footer text.
         */
        public var text: String? = null

        /**
         * URL of the footer icon, only supports http(s) and attachments.
         */
        public var iconUrl: String? = null

        /**
         * A proxied url of the footer icon.
         */
        public var proxyIconUrl: String? = null

        public fun build(): MessageEmbed.Footer {
            require(!text.isNullOrEmpty() && text!!.length in 1..MAX_FOOTER_TEXT_LENGTH) {
                "Footer text must not be null or empty and cannot exceed $MAX_FOOTER_TEXT_LENGTH characters."
            }

            return MessageEmbed.Footer(text, iconUrl, proxyIconUrl)
        }
    }

    public class Title {
        /**
         * Title of the embed.
         */
        public var text: String? = null

        /**
         * URL of the embed.
         */
        public var url: String? = null

        /**
         */
        public fun url(url: URL) {
            this.url = url.toString()
        }
    }

    public class Author {
        /**
         * Name of author
         */
        public var name: String? = null

        /**
         * URL of author
         */
        public var url: String? = null

        /**
         * URL of author icon, only supports http(s) and attachments.
         */
        public var iconUrl: String? = null

        public fun build(): MessageEmbed.AuthorInfo {
            require(!name.isNullOrEmpty() && name!!.length in 1..MAX_AUTHOR_NAME_LENGTH) {
                "Author name must not be null or empty. And cannot exceed $MAX_AUTHOR_NAME_LENGTH characters."
            }

            return MessageEmbed.AuthorInfo(name, iconUrl, url, null)
        }

    }

    public class Thumbnail {
        /**
         * Source url of thumbnail, only supports http(s) and attachments.
         */
        public var url: String? = null

        /**
         * A proxied url of the thumbnail
         */
        public var proxyUrl: String? = null

        /**
         * Height of the thumbnail
         */
        public var height: Int = 0

        /**
         * Width of the thumbnail
         */
        public var width: Int = 0

        /**
         */
        public fun url(url: URL) {
            this.url = url.toString()
        }

        public fun build(): MessageEmbed.Thumbnail = MessageEmbed.Thumbnail(url, proxyUrl, width, height)
    }

    public class Image {
        /**
         * Source url of image, only supports http(s) and attachments.
         */
        public var url: String? = null

        /**
         * A proxied url of the image
         */
        public var proxyUrl: String? = null

        /**
         * Height of the image
         */
        public var height: Int = 0

        /**
         * Width of the image
         */
        public var width: Int = 0

        public fun build(): MessageEmbed.ImageInfo = MessageEmbed.ImageInfo(url, proxyUrl, width, height)
    }

    public class Field {
        /**
         * Name of the field.
         */
        public var name: String? = null

        /**
         * Value of the field.
         */
        public var value: String? = null

        /**
         * Whether this field should display inlined.
         */
        public var inline: Boolean = false

        public fun build(): MessageEmbed.Field {
            require(!name.isNullOrEmpty() && name!!.length in 1..MAX_FIELD_NAME_LENGTH) {
                "Field name must not be null or empty. And cannot exceed $MAX_FIELD_NAME_LENGTH characters."
            }

            require(!value.isNullOrEmpty() && value!!.length in 1..MAX_FIELD_VALUE_LENGTH) {
                "Field value must be not be null or an empty string and cannot exceed $MAX_FIELD_VALUE_LENGTH characters."
            }

            return MessageEmbed.Field(name, value, inline, false)
        }
    }

    /**
     * Builds a usable [MessageEmbed].
     */
    public fun build(): MessageEmbed {
        title?.let {
            require(!it.text.isNullOrEmpty() && it.text!!.length in 1..MAX_TITLE_LENGTH) {
                "Title text must not be empty or exceed $MAX_TITLE_LENGTH characters."
            }
        }

        if (description != null) {
            require(description!!.isNotEmpty()) {
                "Description must not be empty."
            }

            require(description!!.length in 1..MAX_DESCRIPTION_LENGTH) {
                "Description not exceed $MAX_DESCRIPTION_LENGTH characters."
            }
        }

        return MessageEmbed(
            title?.url,
            title?.text,
            description,
            EmbedType.RICH,
            timestamp,
            color,
            thumbnail?.build(),
            null,
            author?.build(),
            null,
            footer?.build(),
            image?.build(),
            fields.map { it.build() }
        )
    }
}
