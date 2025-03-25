package club.redux.sunset.lavafishing.misc

import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.SimpleTier

object ModTiers {
    val OBSIDIAN = SimpleTier(
        BlockTags.INCORRECT_FOR_IRON_TOOL,
        400,
        7.0f,
        2.0f,
        9,
    ) { Ingredient.of(Items.OBSIDIAN) }
    val PROMETHIUM = SimpleTier(
        BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
        2250,
        10.0f,
        5.0f,
        18,
    ) { Ingredient.of(ModItems.PROMETHIUM_INGOT.get()) }
}
