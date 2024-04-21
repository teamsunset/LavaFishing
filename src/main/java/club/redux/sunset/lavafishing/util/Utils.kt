package club.redux.sunset.lavafishing.util

import kotlin.random.Random

object Utils {
    @JvmStatic
    fun generateArchimedianScrew(numPoints: Int, a: Double = 0.0, b: Double = 1.0): List<Pair<Double, Double>> {
        val random = Random(System.currentTimeMillis())
        val multiplier = random.nextDouble(0.5, 2.0)
        val scale = random.nextDouble(0.2, 0.4)
        val points = mutableListOf<Pair<Double, Double>>()
        val maxTheta = numPoints * multiplier // 控制螺旋增长的角度，可以根据需要调整系数

        for (i in 0 until numPoints) {
            val theta = i * maxTheta / numPoints // 将 i 映射到角度范围
            val r = a + b * theta * scale // 计算当前点的半径

            val x = r * kotlin.math.cos(theta)
            val y = r * kotlin.math.sin(theta)

            points.add(Pair(x, y))
        }

        return points
    }
}
