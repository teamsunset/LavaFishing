pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://maven.neoforged.net/releases") }
        maven { url = uri("https://maven.parchmentmc.org") }
    }
}


plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
