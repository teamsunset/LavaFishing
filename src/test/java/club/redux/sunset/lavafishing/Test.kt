package club.redux.sunset.lavafishing

fun generateArchimedeanSpiralPoints(numPoints: Int, a: Double = 0.0, b: Double = 1.0): List<Pair<Double, Double>> {
    val points = mutableListOf<Pair<Double, Double>>()
    val maxTheta = numPoints * 0.1 // 控制螺旋增长的角度，可以根据需要调整系数

    for (i in 0 until numPoints) {
        val theta = i * maxTheta / numPoints // 将 i 映射到角度范围
        val r = a + b * theta // 计算当前点的半径

        val x = r * kotlin.math.cos(theta)
        val y = r * kotlin.math.sin(theta)

        points.add(Pair(x, y))
    }

    return points
}

// 使用示例
fun main() {
    val numPoints = 100 // 生成100个点
    val spiralPoints = generateArchimedeanSpiralPoints(numPoints, 0.0, 1.0)
    spiralPoints.forEach { println("(${it.first}, ${it.second})") }
}