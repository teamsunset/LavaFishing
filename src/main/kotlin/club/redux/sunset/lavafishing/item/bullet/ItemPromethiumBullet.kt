package club.redux.sunset.lavafishing.item.bullet

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.registry.ModDataComponentTypes
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemPromethiumBullet(properties: Properties) : ItemBullet(
    properties.fireResistant().component(ModDataComponentTypes.BULLET_DIVISION_TIMES, 1),
    { ModEntityTypes.PROMETHIUM_BULLET.get() }
) {
    override fun createBullet(pLevel: Level, pAmmo: ItemStack, pShooter: Entity?, pWeapon: ItemStack?): EntityBullet {
        return super.createBullet(pLevel, pAmmo, pShooter, pWeapon).apply {
            if (this !is EntityPromethiumBullet) return@apply
            divisionTimes = pAmmo.components.get(ModDataComponentTypes.BULLET_DIVISION_TIMES.get()) ?: 1
        }
    }
}
