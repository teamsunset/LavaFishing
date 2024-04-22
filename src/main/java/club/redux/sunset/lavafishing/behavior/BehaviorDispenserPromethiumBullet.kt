package club.redux.sunset.lavafishing.behavior

import club.redux.sunset.lavafishing.entity.EntityPromethiumBullet
import net.minecraft.core.Position
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class BehaviorDispenserPromethiumBullet : AbstractProjectileDispenseBehavior() {
    override fun getProjectile(pLevel: Level, pPosition: Position, pStack: ItemStack): Projectile {
        return EntityPromethiumBullet(pLevel, pPosition.x(), pPosition.y(), pPosition.z(), true, 3, 1)
    }
}