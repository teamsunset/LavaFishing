package club.redux.sunset.lavafishing.util

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.registries.ForgeRegistries

fun Item.getKey(): ResourceLocation? {
    return ForgeRegistries.ITEMS.getKey(this)
}