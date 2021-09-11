import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    kotlin("jvm") version Versions.kotlin
}

group = "gg.mixtape"
version = "1.4.1"

repositories {
    maven("https://jitpack.io")
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

/* tasks */
val sourcesJar = task<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

tasks.build {
    dependsOn(tasks.jar)
    dependsOn(sourcesJar)
}

tasks.publish {
    dependsOn(tasks.build)
    onlyIf {
        System.getenv("JFROG_USERNAME") != null && System.getenv("JFROG_PASSWORD") != null
    }
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

/* publishing */
publishing {
    repositories {
        maven("https://dimensional.jfrog.io/artifactory/maven") {
            name = "jfrog"
            credentials {
                username = System.getenv("JFROG_USERNAME")
                password = System.getenv("JFROG_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("jfrog") {
            from(components["java"])

            group = project.group as String
            version = project.version as String
            artifactId = "commons"

            pom {
                name.set("Mixtape Commons")
                description.set("Common utilities used within Mixtape")
                url.set("https://github.com/mixtape-bot/commons")

                organization {
                    name.set("Mixtape Bot")
                    url.set("https://github.com/mixtape-bot")
                }

                developers {
                    developer {
                        name.set("melike2d")
                    }
                }

                licenses {
                    license {
                        name.set("Apache 2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }
            }

            artifact(sourcesJar)
        }
    }
}
