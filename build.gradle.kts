import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("maven")
  kotlin("jvm") version Versions.kotlin
}

group = "gg.mixtape"
version = "1.0"

repositories {
  mavenCentral()
  dv8tion()
  jitpack()
}

dependencies {
  /* kotlin */
  implementation(Dependencies.kotlin)
  implementation(Dependencies.kotlinxCoroutines)

  /* discord */
  api(Dependencies.flight)
  api(Dependencies.jda)
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "1.8"
    freeCompilerArgs = listOf(
      CompilerArgs.requiresOptIn,
      CompilerArgs.experimentalStdlibApi,
      CompilerArgs.experimentalCoroutinesApi,
      CompilerArgs.flowPreview
    )
  }
}

tasks.withType<Wrapper> {
  gradleVersion = "6.8"
  distributionType = Wrapper.DistributionType.ALL
}
