package club.redux.sunset.lavafishing.item.slingshot

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.util.UtilEnchantment
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
import net.minecraft.world.item.enchantment.Enchantments

class ItemPromethiumSlingshot(tier: Tier) : ItemSlingshot(tier, Properties().fireResistant().durability(3000)) {
    override fun customArrow(arrow: AbstractArrow): AbstractArrow {
        arrow as EntityPromethiumBullet
        arrow.dividable = true
        arrow.divisionTimes = 1

        val entity = arrow.owner
        if (entity is LivingEntity) {
            if (entity !is Player) {
                entity.handSlots.firstOrNull { it.item == ModItems.PROMETHIUM_SLINGSHOT.get() }?.let {
                    attachEnchantmentToBullet(arrow, it)
                }
            }
        }

        return arrow
    }


    override fun attachEnchantmentToBullet(bullet: EntityBullet, stack: ItemStack) {
        super.attachEnchantmentToBullet(bullet, stack)
        bullet as EntityPromethiumBullet
        UtilEnchantment.hasThen(Enchantments.POWER_ARROWS, stack) { bullet.divisionNum += it }
        UtilEnchantment.hasThen(Enchantments.FLAMING_ARROWS, stack) { bullet.isCarriedFire = true }
        UtilEnchantment.hasThen(Enchantments.MULTISHOT, stack) { bullet.divisionTimes = 3 }
    }
}