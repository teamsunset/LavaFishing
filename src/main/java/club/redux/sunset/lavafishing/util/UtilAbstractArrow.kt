package club.redux.sunset.lavafishing.util

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.AbstractArrow

fun <T : AbstractArrow> T.setShooter(pShooter: LivingEntity): T {
    this.setPos(pShooter.x, pShooter.eyeY - 0.10000000149011612, pShooter.z)
    this.owner = pShooter
    return this
}