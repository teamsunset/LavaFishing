package club.redux.sunset.lavafishing.util

import net.minecraft.core.Vec3i
import net.minecraft.world.phys.Vec3

object UtilVec3 {
    fun Vec3i.toVec3(): Vec3 = Vec3.atLowerCornerOf(this)
}