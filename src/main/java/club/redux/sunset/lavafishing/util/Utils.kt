package club.redux.sunset.lavafishing.util

import java.security.SecureRandom

object Utils {
    @JvmStatic
    fun generateArchimedianScrew(numPoints: Int, b: Double = 1.0): List<Pair<Double, Double>> {
        val a = 0.0

        val random = SecureRandom()
        val randomOffset = 1.0
        val randomMultiplierMax = randomOffset + 1.0
        val randomMultiplierMin = 1.0 / randomMultiplierMax
        val points = mutableListOf<Pair<Double, Double>>()

        for (i in 0 until numPoints) {
            val randomMultiplier = random.nextDouble(randomMultiplierMin, randomMultiplierMax)
            val rotScale = 2.0 * randomMultiplier
            val sizeScale = 0.1 * (randomMultiplierMax - randomMultiplier)
            val maxTheta = numPoints * rotScale

            val theta = i * maxTheta / numPoints // 将 i 映射到角度范围
            val r = a + b * theta * sizeScale // 计算当前点的半径

            val x = r * kotlin.math.cos(theta)
            val y = r * kotlin.math.sin(theta)

            points.add(Pair(x, y))
        }

        return points
    }
}
