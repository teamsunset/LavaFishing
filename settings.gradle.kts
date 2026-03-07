pluginManagement {
    repositories {
        maven { url = uri("https://maven.neoforged.net/releases") }
        maven { url = uri("https://maven.parchmentmc.org") }
        gradlePluginPortal()
        mavenCentral()
    }
}


plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
