package club.redux.sunset.lavafishing.misc

import net.minecraft.tags.BlockTags
import net.minecraft.world.item.ToolMaterial
import net.neoforged.neoforge.common.Tags

object ModToolMaterials {
    val OBSIDIAN = ToolMaterial(
        BlockTags.INCORRECT_FOR_IRON_TOOL,
        400,
        7.0f,
        2.0f,
        9,
        Tags.Items.OBSIDIANS,
    )
    val PROMETHIUM = ToolMaterial(
        BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
        2250,
        10.0f,
        5.0f,
        18,
        ModTags.OreDirectory.PROMETHIUM_INGOT,
    )
}
