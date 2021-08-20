import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    kotlin("jvm") version Versions.kotlin
}

group = "bot.mixtape"
version = "1.3.0"

repositories {
    maven("https://dimensional.jfrog.io/artifactory/maven")
    maven("https://m2.dv8tion.net/releases")
    mavenCentral()
}

dependencies {
    /* kotlin */
    implementation(Dependencies.kotlin)
    implementation(Dependencies.kotlinxCoroutines)
    implementation(Dependencies.kotlinxCoroutinesJdk8)

    /* discord */
    api(Dependencies.flight)
    api(Dependencies.jda)
}

tasks.withType<KotlinCompile> {
    targetCompatibility = "16"
    sourceCompatibility = "16"
    kotlinOptions {
        jvmTarget = "16"
        freeCompilerArgs = listOf(
            CompilerArgs.requiresOptIn,
            CompilerArgs.experimentalStdlibApi,
            CompilerArgs.experimentalCoroutinesApi,
            CompilerArgs.flowPreview
        )
    }
}
