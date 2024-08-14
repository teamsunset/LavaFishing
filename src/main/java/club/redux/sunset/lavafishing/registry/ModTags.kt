package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.misc.ModResourceLocation
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object ModTags {
    object Blocks {
        @JvmField val TEST_TAG = tag("test")

        private fun tag(path: String): TagKey<Block> = BlockTags.create(ModResourceLocation(path))

    }

    object Items {
        @JvmField val TOOLTIP = tag("tooltip")

        private fun tag(path: String): TagKey<Item> = ItemTags.create(ModResourceLocation(path))
    }

    object OreDirectories {
        enum class OreDirectoryType(val type: String) {
            INGOTS("ingots"), NUGGETS("nuggets"), STORAGE_BLOCKS("storage_blocks")
        }

        @JvmField val PROMETHIUM_INGOT = tagKey(OreDirectoryType.INGOTS, "promethium")
        @JvmField val PROMETHIUM_NUGGET = tagKey(OreDirectoryType.NUGGETS, "promethium")
        @JvmField val PROMETHIUM_BLOCK = tagKey(OreDirectoryType.STORAGE_BLOCKS, "promethium")

        private fun tagKey(type: OreDirectoryType, path: String): TagKey<Item> =
            ItemTags.create(ResourceLocation("forge", "${type.type}/${path}"))
    }
}