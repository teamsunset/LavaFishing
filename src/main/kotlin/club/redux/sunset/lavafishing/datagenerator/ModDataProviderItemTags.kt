package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.item.fish.ItemLavaFish
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import club.redux.sunset.lavafishing.misc.ModTags
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModItemsAqua
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.FishingRodItem
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModDataProviderItemTags(
    packOutput: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    blockTags: CompletableFuture<TagLookup<Block>>,
    existingFileHelper: ExistingFileHelper,
) : ItemTagsProvider(packOutput, lookupProvider, blockTags, BuiltConstants.MOD_ID, existingFileHelper) {
    override fun addTags(pProvider: HolderLookup.Provider) {
        ModItems.getEntriesIsInstance<ItemLavaFish>().forEach { tag(ItemTags.FISHES).add(it) }

        tag(ModTags.OreDirectory.PROMETHIUM_INGOT).add(ModItems.PROMETHIUM_INGOT.get())
        tag(ModTags.OreDirectory.PROMETHIUM_NUGGET).add(ModItems.PROMETHIUM_NUGGET.get())
        tag(ModTags.OreDirectory.PROMETHIUM_BLOCK).add(ModItems.PROMETHIUM_BLOCK.get())

        tag(Tags.Items.INGOTS).addTags(ModTags.OreDirectory.PROMETHIUM_INGOT)
        tag(Tags.Items.NUGGETS).addTags(ModTags.OreDirectory.PROMETHIUM_NUGGET)
        tag(Tags.Items.STORAGE_BLOCKS).addTags(ModTags.OreDirectory.PROMETHIUM_BLOCK)

        tag(Tags.Items.ARMORS).add(*ModItems.getEntriesIsInstance<ArmorItem>().toTypedArray())
        tag(ItemTags.BOW_ENCHANTABLE).add(*ModItems.getEntriesIsInstance<ItemSlingshot>().toTypedArray())
        tag(ItemTags.CROSSBOW_ENCHANTABLE).add(*ModItems.getEntriesIsInstance<ItemSlingshot>().toTypedArray())

        tag(ItemTags.DURABILITY_ENCHANTABLE).add(
            *ModItems.getEntriesIsInstance<ItemSlingshot>().toTypedArray(),
            *ModItems.getEntriesIsInstance<FishingRodItem>().toTypedArray(),
        )

        tag(ItemTags.HEAD_ARMOR).add(ModItems.PROMETHIUM_HELMET.get())
        tag(ItemTags.CHEST_ARMOR).add(ModItems.PROMETHIUM_CHESTPLATE.get())
        tag(ItemTags.LEG_ARMOR).add(ModItems.PROMETHIUM_LEGGINGS.get())
        tag(ItemTags.FOOT_ARMOR).add(ModItems.PROMETHIUM_BOOTS.get())

        tag(Tags.Items.FOODS_SOUP).add(ModItems.SPICY_FISH_FILLET.get())
        tag(Tags.Items.FOODS_RAW_FISH).add(ModItems.FISH_PASTE.get())

        tag(Tags.Items.CHESTS).add(ModItems.PROMETHEUS_BOUNTY.get())
        tag(ItemTags.FISHING_ENCHANTABLE).add(
            *ModItems.getEntriesIsInstance<FishingRodItem>().toTypedArray(),
        )
        tag(Tags.Items.TOOLS_FISHING_ROD).add(
            *ModItems.getEntriesIsInstance<FishingRodItem>().toTypedArray(),
        )

        tag(ModTags.Item.NEPTUNIUM).add(
            ModItems.NEPTUNIUM_BULLET.get(),
            ModItems.NEPTUNIUM_SLINGSHOT.get()
        )

        tag(ModTags.Item.PROMETHIUM).add(
            ModItems.PROMETHIUM_HELMET.get(),
            ModItems.PROMETHIUM_CHESTPLATE.get(),
            ModItems.PROMETHIUM_LEGGINGS.get(),
            ModItems.PROMETHIUM_BOOTS.get(),
            ModItems.PROMETHIUM_INGOT.get(),
            ModItems.PROMETHIUM_NUGGET.get(),
            ModItems.PROMETHIUM_BLOCK.get(),
            ModItems.PROMETHIUM_BULLET.get(),
            ModItems.PROMETHIUM_SLINGSHOT.get()
        )

        tag(ModTags.Item.TOOLTIP)
            .add(
                ModItems.HYDROTHERMAL_HOOK.get(),
                ModItems.PROMETHIUM_HELMET.get(),
                ModItems.PROMETHIUM_CHESTPLATE.get(),
                ModItems.PROMETHIUM_LEGGINGS.get(),
                ModItems.PROMETHIUM_BOOTS.get(),
                ModItems.PROMETHIUM_SLINGSHOT.get(),
                ModItems.PROMETHIUM_BULLET.get(),
                ModItems.NEPTUNIUM_SLINGSHOT.get(),
                ModItems.NEPTUNIUM_BULLET.get(),
            )
    }
}
