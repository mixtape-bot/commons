package mixtape.commons.jda

import net.dv8tion.jda.api.entities.EmbedType
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.Role
import java.net.URL
import java.time.OffsetDateTime

class EmbedBuilder {

    companion object {
        const val ZERO_WIDTH_SPACE = "\u200b"

        const val MAX_TITLE_LENGTH = 256
        const val MAX_FIELD_NAME_LENGTH = 256
        const val MAX_FIELD_VALUE_LENGTH = 256
        const val MAX_AUTHOR_NAME_LENGTH = 256
        const val MAX_FOOTER_TEXT_LENGTH = 2048
        const val MAX_DESCRIPTION_LENGTH = 4096
    }

    /**
     * All fields in the embed.
     */
    var fields: MutableList<Field> = mutableListOf()

    /**
     * Information of the embed author.
     */
    var author: Author? = null

    /**
     * Title information, if any.
     */
    var title: Title? = null

    /**
     * Image information of the embed.
     */
    var image: Image? = null

    /**
     * Thumbnail information of the embed.
     */
    var thumbnail: Thumbnail? = null

    /**
     * Footer information of the embed.
     */
    var footer: Footer? = null

    /**
     * The color of this embed, defaults to [Role.DEFAULT_COLOR_RAW]
     */
    var color: Int = Role.DEFAULT_COLOR_RAW

    /**
     * The description text for this embed.
     */
    var description: String? = null

    /**
     * The timestamp of this embed.
     */
    var timestamp: OffsetDateTime? = null

    /**
     * Sets the [Title] of this embed using [builder].
     *
     * @param builder
     *   Configures the [Title]
     */
    fun title(builder: Title.() -> Unit) {
        title = Title().apply(builder)
    }

    /**
     * Sets the [Footer] of this embed using [builder].
     *
     * @param builder
     *   Configures the [Footer]
     */
    fun footer(builder: Footer.() -> Unit) {
        footer = Footer().apply(builder)
    }

    /**
     * Sets [Image] of this embed using [builder].
     *
     * @param builder
     *   Configures the [Image]
     */
    fun image(builder: Image.() -> Unit) {
        image = Image().apply(builder)
    }

    /**
     * Sets the thumbnail of this embed
     *
     * @param builder
     *   Configures the thumbnail data
     */
    fun thumbnail(builder: Thumbnail.() -> Unit) {
        thumbnail = Thumbnail().apply(builder)
    }

    /**
     * Sets the [Author] of the embed.
     *
     * @param builder
     *   Configures the [Author]
     */
    fun author(builder: Author.() -> Unit) {
        author = Author().apply(builder)
    }

    /**
     * Adds a new [Field] to this embed.
     *
     * @param builder
     *   Configures the [Field]
     */
    fun field(builder: Field.() -> Unit) {
        val field = Field().apply(builder)
        if (fields.size > 25) {
            return
        }

        fields.add(field)
    }

    class Footer {
        /**
         * Footer text.
         */
        var text: String? = null

        /**
         * URL of the footer icon, only supports http(s) and attachments.
         */
        var iconUrl: String? = null

        /**
         * A proxied url of the footer icon.
         */
        var proxyIconUrl: String? = null

        fun build(): MessageEmbed.Footer {
            require(!text.isNullOrEmpty() && text!!.length in 1..MAX_FOOTER_TEXT_LENGTH) {
                "Footer text must not be null or empty and cannot exceed $MAX_FOOTER_TEXT_LENGTH characters."
            }

            return MessageEmbed.Footer(text, iconUrl, proxyIconUrl)
        }
    }

    class Title {
        /**
         * Title of the embed.
         */
        var text: String? = null

        /**
         * URL of the embed.
         */
        var url: String? = null

        /**
         */
        fun url(url: URL) {
            this.url = url.toString()
        }
    }

    class Author {
        /**
         * Name of author
         */
        var name: String? = null

        /**
         * URL of author
         */
        var url: String? = null

        /**
         * URL of author icon, only supports http(s) and attachments.
         */
        var iconUrl: String? = null

        fun build(): MessageEmbed.AuthorInfo {
            require(!name.isNullOrEmpty() && name!!.length in 1..MAX_AUTHOR_NAME_LENGTH) {
                "Author name must not be null or empty. And cannot exceed $MAX_AUTHOR_NAME_LENGTH characters."
            }

            return MessageEmbed.AuthorInfo(name, iconUrl, url, null)
        }

    }

    class Thumbnail {
        /**
         * Source url of thumbnail, only supports http(s) and attachments.
         */
        var url: String? = null

        /**
         * A proxied url of the thumbnail
         */
        var proxyUrl: String? = null

        /**
         * Height of the thumbnail
         */
        var height: Int = 0

        /**
         * Width of the thumbnail
         */
        var width: Int = 0

        /**
         */
        fun url(url: URL) {
            this.url = url.toString()
        }

        fun build(): MessageEmbed.Thumbnail {
            return MessageEmbed.Thumbnail(url, proxyUrl, width, height)
        }
    }

    class Image {
        /**
         * Source url of image, only supports http(s) and attachments.
         */
        var url: String? = null

        /**
         * A proxied url of the image
         */
        var proxyUrl: String? = null

        /**
         * Height of the image
         */
        var height: Int = 0

        /**
         * Width of the image
         */
        var width: Int = 0

        fun build(): MessageEmbed.ImageInfo {
            return MessageEmbed.ImageInfo(url, proxyUrl, width, height)
        }
    }

    class Field {
        /**
         * Name of the field.
         */
        var name: String? = null

        /**
         * Value of the field.
         */
        var value: String? = null

        /**
         * Whether this field should display inlined.
         */
        var inline: Boolean = false

        fun build(): MessageEmbed.Field {
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
    fun build(): MessageEmbed {
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
