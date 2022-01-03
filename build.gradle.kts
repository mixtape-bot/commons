import lol.dimensional.gradle.dsl.Version
import lol.dimensional.gradle.dsl.ReleaseType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        maven("https://maven.dimensional.fun/releases")
    }

    dependencies {
        classpath("fun.dimensional.gradle:gradle-tools:1.0.2")
    }
}

plugins {
    `maven-publish`

    kotlin("jvm") version "1.6.10"
}

val version = Version(1, 4, 0, release = ReleaseType.ReleaseCandidate)
project.group = "gg.mixtape"
project.version = version.asString()

kotlin {
    explicitApi()
}

repositories {
    mavenCentral()
    dimensionalFun(snapshots)
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    /* kotlin */
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.0")

    /* logging */
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")

    /* discord */
    api("gg.mixtape:flight:2.5-RC.3")
    api("net.dv8tion:JDA:5.0.0-alpha.3")

    /* testing */
    testImplementation("ch.qos.logback:logback-classic:1.2.10")
}

/* tasks */
val sourcesJar by tasks.registering(Jar::class) {
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
        System.getenv("REPO_ALIAS") != null && System.getenv("REPO_TOKEN") != null
    }
}

tasks.withType<KotlinCompile> {
    targetCompatibility = "16"
    sourceCompatibility = "16"
    kotlinOptions {
        jvmTarget = "16"
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=kotlin.ExperimentalStdlibApi",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlin.contracts.ExperimentalContracts",
            "-Xopt-in=kotlinx.coroutines.FlowPreview"
        )
    }
}

/* publishing */
publishing {
    repositories {
        maven(version.repository.fullUrl) {
            authentication {
                create<BasicAuthentication>("basic")
            }

            credentials {
                username = System.getenv("REPO_ALIAS")
                password = System.getenv("REPO_TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>("MixtapeCommons") {
            from(components["kotlin"])

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
