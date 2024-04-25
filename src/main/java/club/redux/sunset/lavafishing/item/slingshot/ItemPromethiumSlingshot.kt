package club.redux.sunset.lavafishing.item.slingshot

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.misc.ModTiers
import club.redux.sunset.lavafishing.util.UtilEnchantment
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantments

class ItemPromethiumSlingshot : ItemSlingshot(ModTiers.PROMETHIUM, Properties().fireResistant()) {
    override fun customBullet(bullet: EntityBullet): EntityBullet {
        bullet as EntityPromethiumBullet
        bullet.dividable = true
        bullet.divisionTimes = 1

        return bullet
    }

    override fun attachEnchantmentToBullet(bullet: EntityBullet, stack: ItemStack) {
        super.attachEnchantmentToBullet(bullet, stack)
        bullet as EntityPromethiumBullet
        UtilEnchantment.hasThen(Enchantments.POWER_ARROWS, stack) { bullet.divisionNum += it }
        UtilEnchantment.hasThen(Enchantments.FLAMING_ARROWS, stack) { bullet.isCarriedFire = true }
        UtilEnchantment.hasThen(Enchantments.MULTISHOT, stack) { bullet.divisionTimes = 3 }
    }
}