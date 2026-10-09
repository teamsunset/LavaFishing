package club.redux.sunset.lavafishing

import club.redux.sunset.lavafishing.api.IConstants

object Deps : IConstants {
    const val MIXIN_PROCESSOR = "org.spongepowered:mixin:0.8.7:processor"
    const val AQUACULTURE = "curse.maven:aquaculture-60028:8192770"
    const val APPLE_SKIN = "squeek.appleskin:appleskin-neoforge:mc26.1-3.0.9"
    const val OKIO = "com.squareup.okio:okio-jvm:3.18.2"
    const val JUNIT_API = "org.junit.jupiter:junit-jupiter-api:6.1.2"
    const val JUNIT_ENGINE = "org.junit.jupiter:junit-jupiter-engine:6.1.2"
    const val JUNIT_LAUNCHER = "org.junit.platform:junit-platform-launcher"

    const val KOTLIN_FOR_FORGE = "thedarkcolour:kotlinforforge-neoforge:${Props.KOTLIN_FOR_FORGE_VERSION}"
    const val JEI_FORGE_API = "mezz.jei:jei-${Props.MINECRAFT_VERSION}-neoforge-api:${Props.JEI_VERSION}"
    const val JEI = "mezz.jei:jei-${Props.MINECRAFT_VERSION}-neoforge:${Props.JEI_VERSION}"
}
