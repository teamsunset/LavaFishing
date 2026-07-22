package club.redux.sunset.lavafishing.util

import club.redux.sunset.lavafishing.LavaFishing
import net.minecraft.resources.Identifier
import org.slf4j.LoggerFactory

object Utils {
    fun String.identifier(path: String): Identifier = Identifier.fromNamespaceAndPath(this, path)

    fun asciiArt() {
        this.javaClass.getResourceAsStream("/ascii_art.txt")?.let {
            it.bufferedReader().use { reader ->
                reader.lines().forEach { line ->
                    LoggerFactory.getLogger(LavaFishing::class.java).info(line)
                }
            }
        }
    }
}

