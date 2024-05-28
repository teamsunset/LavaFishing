package club.redux.sunset.lavafishing.util

import net.minecraft.core.BlockPos
import net.minecraft.core.Vec3i
import net.minecraft.util.Mth
import net.minecraft.world.phys.Vec3

fun Vec3.toVec3i(): Vec3i = Vec3i(Mth.floor(x), Mth.floor(y), Mth.floor(z))
fun Vec3.toBlockPos(): BlockPos = BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z))