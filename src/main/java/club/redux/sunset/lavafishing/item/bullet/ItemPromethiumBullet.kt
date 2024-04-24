package club.redux.sunset.lavafishing.item.bullet

import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.misc.ModTiers
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemPromethiumBullet : ItemBullet(
    ModTiers.PROMETHIUM,
    Properties().fireResistant()
) {
    override fun createArrow(pLevel: Level, pStack: ItemStack, pShooter: LivingEntity): AbstractArrow {
        return EntityPromethiumBullet(pLevel, pShooter)
    }
}