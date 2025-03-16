package club.redux.sunset.lavafishing.util

import net.minecraft.core.Holder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.EnchantmentHelper

object UtilEnchantment {
    fun hasThen(enchantment: Holder<Enchantment>, stack: ItemStack, action: (Int) -> Unit) {
        val lvl = EnchantmentHelper.getTagEnchantmentLevel(enchantment, stack)
        if (lvl > 0) {
            action(lvl)
        }
    }
}