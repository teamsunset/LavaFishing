package club.redux.sunset.lavafishing.util

import club.redux.sunset.lavafishing.LavaFishing
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.phys.Vec3
import org.slf4j.LoggerFactory
import java.security.SecureRandom
import java.util.*

object Utils {
    fun Random.estimate(rate: Double) = this.nextDouble() < rate

    fun String.resourceLocation(path: String): ResourceLocation = ResourceLocation(this, path)

    fun generateArchimedianScrew(numPoints: Int, b: Double = 1.0): List<Pair<Double, Double>> {
        val a = 0.0

        val random = SecureRandom()
        val randomOffset = 1.0
        val randomMultiplierMax = randomOffset + 1.0
        val randomMultiplierMin = 1.0 / randomMultiplierMax
        val points = mutableListOf<Pair<Double, Double>>()

        for (i in 0 until numPoints) {
            val randomMultiplier = random.nextDouble(randomMultiplierMin, randomMultiplierMax)
            val maxTheta = numPoints * randomMultiplier

            val theta = i * maxTheta / numPoints // 将 i 映射到角度范围
            val r = a + b * theta * 0.05// 计算当前点的半径

            val x = r * kotlin.math.cos(theta)
            val y = r * kotlin.math.sin(theta)

            points.add(x to y)
        }

        return points
    }

    fun dot(vecA: Vec3, vecB: Vec3) = vecA.x * vecB.x + vecA.y * vecB.y + vecA.z * vecB.z

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

