import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

object Versions {
  /* kotlin */
  const val kotlin = "1.4.32" // heh heh
  const val kotlinxCoroutines = "1.4.3"

  /* discord */
  const val flight = "ccfd2e61f4"
  const val jda = "4.2.1_259"
}

object Dependencies {
  /* kotlin */
  const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
  const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"

  /* discord */
  const val flight = "com.github.mixtape-bot:Flight:${Versions.flight}"
  const val jda = "net.dv8tion:JDA:${Versions.jda}"
}

fun RepositoryHandler.dv8tion() = maven("https://m2.dv8tion.net/releases")

fun RepositoryHandler.jitpack() = maven("https://jitpack.io")