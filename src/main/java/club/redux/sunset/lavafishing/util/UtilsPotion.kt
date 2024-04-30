package club.redux.sunset.lavafishing.util

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionUtils

class UtilsPotion {
}

fun Potion.getItemStack(): ItemStack {
    return PotionUtils.setPotion(ItemStack(Items.POTION), this)
}