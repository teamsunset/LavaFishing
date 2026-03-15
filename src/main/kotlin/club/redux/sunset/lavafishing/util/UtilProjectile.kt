package club.redux.sunset.lavafishing.util

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.projectile.Projectile

fun <T : Projectile> T.setShooter(pShooter: Entity?) = this.apply {
    if (pShooter == null) {
        owner = null
        return@apply
    }

    setPos(pShooter.x, pShooter.eyeY - 0.10000000149011612, pShooter.z)
    xRot = pShooter.xRot
    yRot = pShooter.yRot
    owner = pShooter
}
