package club.redux.sunset.lavafishing.util

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantment

object UtilItemStack {
    fun hasEnchantmentThen(stack: ItemStack, enchantment: Enchantment, action: (Int) -> Unit) {
        val lvl = stack.getEnchantmentLevel(enchantment)
        if (lvl > 0) {
            action(lvl)
        }
    }
}

fun ItemStack.hasEnchantmentThen(enchantment: Enchantment, action: (Int) -> Unit) =
    UtilItemStack.hasEnchantmentThen(this, enchantment, action)