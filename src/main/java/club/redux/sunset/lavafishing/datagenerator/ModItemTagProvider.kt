package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.item.fishes.ItemLavaFish
import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.tags.ItemTags
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModItemTagProvider(
    packOutput: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    blockTags: CompletableFuture<TagLookup<Block>>,
    existingFileHelper: ExistingFileHelper,
) : ItemTagsProvider(packOutput, lookupProvider, blockTags, BuildConstants.MOD_ID, existingFileHelper) {
    override fun addTags(pProvider: HolderLookup.Provider) {
        ModItems.REGISTER.entries.map { it.get() }.filterIsInstance<ItemLavaFish>().forEach {
            tag(ItemTags.FISHES).add(it)
        }
    }
}