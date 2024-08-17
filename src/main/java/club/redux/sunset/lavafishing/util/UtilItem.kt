package club.redux.sunset.lavafishing.util

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.registries.ForgeRegistries

object UtilItem {
    @JvmStatic
    fun getKey(item: Item): ResourceLocation? {
        return ForgeRegistries.ITEMS.getKey(item)
    }
}

fun Item.getKey(): ResourceLocation? {
    return ForgeRegistries.ITEMS.getKey(this)
}