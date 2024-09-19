package club.redux.sunset.lavafishing.util

import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantment

object UtilItemStack {
    @JvmStatic
    fun getEnchantmentLevel(stack: ItemStack, enchantment: ResourceKey<Enchantment>): Int =
        stack.tagEnchantments.keySet()
            .find { it.key == enchantment }
            ?.let { stack.tagEnchantments.getLevel(it) } ?: 0


    @JvmStatic
    fun hasEnchantmentThen(stack: ItemStack, enchantment: ResourceKey<Enchantment>, action: (Int) -> Unit) {
        val lvl = getEnchantmentLevel(stack, enchantment)
        if (lvl > 0) {
            action(lvl)
        }
    }
}

fun ItemStack.getEnchantmentLevel(enchantment: ResourceKey<Enchantment>) =
    UtilItemStack.getEnchantmentLevel(this, enchantment)

fun ItemStack.hasEnchantmentThen(enchantment: ResourceKey<Enchantment>, action: (Int) -> Unit) =
    UtilItemStack.hasEnchantmentThen(this, enchantment, action)

