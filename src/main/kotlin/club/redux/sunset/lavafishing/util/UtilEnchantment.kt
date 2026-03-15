package club.redux.sunset.lavafishing.util

import net.minecraft.core.Holder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantment

object UtilEnchantment {
    fun hasThen(enchantment: Enchantment, stack: ItemStack, action: (Int) -> Unit) {
        val lvl = stack.getEnchantmentLevel(enchantment)
        if (lvl > 0) {
            action(lvl)
        }
    }

    fun hasThen(enchantment: Holder<Enchantment>, stack: ItemStack, action: (Int) -> Unit) {
        hasThen(enchantment.value(), stack, action)
    }
}
