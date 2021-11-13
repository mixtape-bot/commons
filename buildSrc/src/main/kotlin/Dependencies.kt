object Versions {
    /* kotlin */
    const val kotlin = "1.5.31"
    const val kotlinxCoroutines = "1.5.2"

    /* logging */
    const val kotlinLogging = "2.0.11"

    /* discord */
    const val flight = "2.2.2"
    const val jda = "4.4.0_DEV" // "4.3.0_313"
}

object Dependencies {
    /* kotlin */
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
    const val kotlinxCoroutinesJdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.kotlinxCoroutines}"

    /* logging */
    const val kotlinLogging = "io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}"

    /* discord */
    const val flight = "gg.mixtape:flight:${Versions.flight}"
    const val jda = "net.dv8tion:JDA:${Versions.jda}"
}
