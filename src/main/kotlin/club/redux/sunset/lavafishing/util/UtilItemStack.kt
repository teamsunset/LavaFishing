package club.redux.sunset.lavafishing.util

import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantment

object UtilItemStack {
    fun ItemStack.getEnchantmentLevel(enchantment: ResourceKey<Enchantment>) =
        this.tagEnchantments.keySet()
            .find { it.key == enchantment }
            ?.let { this.tagEnchantments.getLevel(it) } ?: 0

    fun ItemStack.hasEnchantmentThen(enchantment: ResourceKey<Enchantment>, action: (Int) -> Unit) {
        val lvl = this.getEnchantmentLevel(enchantment)
        if (lvl > 0) {
            action(lvl)
        }
    }
}



