package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.item.fish.ItemLavaFish
import club.redux.sunset.lavafishing.misc.ModTags
import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.tags.ItemTags
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModDataProviderItemTags(
    packOutput: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    blockTags: CompletableFuture<TagLookup<Block>>,
    existingFileHelper: ExistingFileHelper,
) : ItemTagsProvider(packOutput, lookupProvider, blockTags, BuildConstants.MOD_ID, existingFileHelper) {
    override fun addTags(pProvider: HolderLookup.Provider) {
        ModItems.REGISTER.entries.map { it.get() }
            .filterIsInstance<ItemLavaFish>()
            .forEach { tag(ItemTags.FISHES).add(it) }

        tag(ModTags.OreDirectories.PROMETHIUM_INGOT).add(ModItems.PROMETHIUM_INGOT.get())
        tag(ModTags.OreDirectories.PROMETHIUM_NUGGET).add(ModItems.PROMETHIUM_NUGGET.get())
        tag(ModTags.OreDirectories.PROMETHIUM_BLOCK).add(ModItems.PROMETHIUM_BLOCK.get())

        tag(Tags.Items.INGOTS).addTags(ModTags.OreDirectories.PROMETHIUM_INGOT)
        tag(Tags.Items.NUGGETS).addTags(ModTags.OreDirectories.PROMETHIUM_NUGGET)
        tag(Tags.Items.STORAGE_BLOCKS).addTags(ModTags.OreDirectories.PROMETHIUM_BLOCK)

        tag(Tags.Items.ARMORS).add(ModItems.PROMETHIUM_HELMET.get())
            .add(ModItems.PROMETHIUM_CHESTPLATE.get())
            .add(ModItems.PROMETHIUM_LEGGINGS.get())
            .add(ModItems.PROMETHIUM_BOOTS.get())

        tag(ItemTags.BOW_ENCHANTABLE)
            .add(ModItems.NEPTUNIUM_SLINGSHOT.get())
            .add(ModItems.PROMETHIUM_SLINGSHOT.get())

        tag(ItemTags.DURABILITY_ENCHANTABLE)
            .add(ModItems.NEPTUNIUM_SLINGSHOT.get())
            .add(ModItems.PROMETHIUM_SLINGSHOT.get())
            .add(ModItems.OBSIDIAN_FISHING_ROD.get())
            .add(ModItems.NETHERITE_FISHING_ROD.get())

        tag(ItemTags.HEAD_ARMOR).add(ModItems.PROMETHIUM_HELMET.get())
        tag(ItemTags.CHEST_ARMOR).add(ModItems.PROMETHIUM_CHESTPLATE.get())
        tag(ItemTags.LEG_ARMOR).add(ModItems.PROMETHIUM_LEGGINGS.get())
        tag(ItemTags.FOOT_ARMOR).add(ModItems.PROMETHIUM_BOOTS.get())

        tag(Tags.Items.CHESTS).add(ModItems.PROMETHEUS_BOUNTY.get())
        tag(ItemTags.FISHING_ENCHANTABLE)
            .add(ModItems.OBSIDIAN_FISHING_ROD.get())
            .add(ModItems.NETHERITE_FISHING_ROD.get())
        tag(Tags.Items.TOOLS_FISHING_ROD)
            .add(ModItems.OBSIDIAN_FISHING_ROD.get())
            .add(ModItems.NETHERITE_FISHING_ROD.get())
    }
}