package club.redux.sunset.lavafishing.util

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

object UtilItem {
    @JvmStatic
    fun getKey(item: Item): ResourceLocation {
        return BuiltInRegistries.ITEM.getKey(item)
    }
}

fun Item.getKey(): ResourceLocation {
    return BuiltInRegistries.ITEM.getKey(this)
}