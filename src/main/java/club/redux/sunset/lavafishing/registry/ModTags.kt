package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.registry.ModTags.Blocks.tag
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object ModTags {
    object Blocks {
        @JvmField val TEST_TAG = tag("test")

        private fun tag(name: String): TagKey<Block> {
            return BlockTags.create(ResourceLocation(BuildConstants.MOD_ID, name))
        }
    }

    object Items {
        @JvmField val TEST_TAG = tag("test")

        private fun tag(name: String): TagKey<Item> {
            return ItemTags.create(ResourceLocation(BuildConstants.MOD_ID, name))
        }
    }
}