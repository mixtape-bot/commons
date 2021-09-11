object Versions {
    /* kotlin */
    const val kotlin = "1.5.30"
    const val kotlinxCoroutines = "1.5.1"

    /* discord */
    const val flight = "3.0.0"
    const val jda = "development-SNAPSHOT"
}

object Dependencies {
    /* kotlin */
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
    const val kotlinxCoroutinesJdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.kotlinxCoroutines}"

    /* discord */
    const val flight = "gg.mixtape:flight:${Versions.flight}"
    const val jda = "com.github.mixtape-oss:JDA:${Versions.jda}"
}
