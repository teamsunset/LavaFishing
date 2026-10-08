package club.redux.sunset.lavafishing.util

import net.minecraft.core.Vec3i
import net.minecraft.world.phys.Vec3
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

object UtilVec3 {
    fun Vec3i.toVec3(): Vec3 = Vec3.atLowerCornerOf(this)

    fun Vec3.rotateToward(to: Vec3, maxAngle: Double): Vec3 {
        val f = this.normalize()
        val t = to.normalize()
        val cosAngle = f.dot(t).coerceIn(-1.0, 1.0)
        if (cosAngle > 0.99999) return t
        val angle = acos(cosAngle)
        if (angle <= maxAngle) return t
        var axis = f.cross(t)
        if (axis.lengthSqr() < 1.0e-10) {
            // 方向几乎相反，任取一个垂直轴
            axis = f.cross(Vec3(0.0, 1.0, 0.0))
            if (axis.lengthSqr() < 1.0e-10) axis = f.cross(Vec3(1.0, 0.0, 0.0))
        }
        val k = axis.normalize()
        val cosA = cos(maxAngle)
        val sinA = sin(maxAngle)
        val term1 = f.scale(cosA)
        val term2 = k.cross(f).scale(sinA)
        val term3 = k.scale(k.dot(f) * (1.0 - cosA))
        return term1.add(term2).add(term3)
    }
}
