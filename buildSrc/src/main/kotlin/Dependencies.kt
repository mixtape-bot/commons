object Versions {
    /* kotlin */
    const val kotlin = "1.5.21"
    const val kotlinxCoroutines = "1.5.1"

    /* discord */
    const val flight = "2.1.6"
    const val jda = "4.3.0_310"
}

object Dependencies {
    /* kotlin */
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
    const val kotlinxCoroutinesJdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.kotlinxCoroutines}"

    /* discord */
    const val flight = "gg.mixtape:flight:${Versions.flight}"
    const val jda = "net.dv8tion:JDA:${Versions.jda}"
}
