package club.redux.sunset.lavafishing.misc

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.tool.tag.Tagger
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey

object ModTags {
    object Block : Tagger<net.minecraft.world.level.block.Block>(Registries.BLOCK, BuiltConstants.MOD_ID) {
        val TEST_TAG by this.tag()
    }

    object EntityType : Tagger<net.minecraft.world.entity.EntityType<*>>(
        Registries.ENTITY_TYPE,
        BuiltConstants.MOD_ID
    ) {
        val LAVA_FISH by this.tag()
    }

    object Item : Tagger<net.minecraft.world.item.Item>(Registries.ITEM, BuiltConstants.MOD_ID) {
        val TOOLTIP by this.tag()

        val NEPTUNIUM by this.tag()
        val PROMETHIUM by this.tag()
    }

    object OreDirectory {
        enum class OreDirectoryType(val type: String) {
            INGOTS("ingots"),
            NUGGETS("nuggets"),
            STORAGE_BLOCKS("storage_blocks")
        }

        val PROMETHIUM_INGOT = tagKey(OreDirectoryType.INGOTS, "promethium")
        val PROMETHIUM_NUGGET = tagKey(OreDirectoryType.NUGGETS, "promethium")
        val PROMETHIUM_BLOCK = tagKey(OreDirectoryType.STORAGE_BLOCKS, "promethium")

        private fun tagKey(type: OreDirectoryType, path: String): TagKey<net.minecraft.world.item.Item> =
            ItemTags.create(ResourceLocation("c", "${type.type}/${path}"))
    }
}