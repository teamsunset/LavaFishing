package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.item.fish.ItemLavaFish
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import club.redux.sunset.lavafishing.misc.ModTags
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModItemsAqua
import com.teammetallurgy.aquaculture.api.AquacultureAPI
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.FishingRodItem
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModDataProviderItemTags(
    packOutput: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    blockTags: CompletableFuture<TagLookup<Block>>,
    existingFileHelper: ExistingFileHelper,
) : ItemTagsProvider(packOutput, lookupProvider, blockTags, BuildConstants.MOD_ID, existingFileHelper) {
    override fun addTags(provider: HolderLookup.Provider) {
        ModItems.getEntriesIsInstance<ItemLavaFish>().forEach { tag(ItemTags.FISHES).add(it) }

        tag(ModTags.OreDirectory.PROMETHIUM_INGOT).add(ModItems.PROMETHIUM_INGOT.get())
        tag(ModTags.OreDirectory.PROMETHIUM_NUGGET).add(ModItems.PROMETHIUM_NUGGET.get())
        tag(ModTags.OreDirectory.PROMETHIUM_BLOCK).add(ModItems.PROMETHIUM_BLOCK.get())

        tag(Tags.Items.INGOTS).addTags(ModTags.OreDirectory.PROMETHIUM_INGOT)
        tag(Tags.Items.NUGGETS).addTags(ModTags.OreDirectory.PROMETHIUM_NUGGET)
        tag(Tags.Items.STORAGE_BLOCKS).addTags(ModTags.OreDirectory.PROMETHIUM_BLOCK)

        tag(Tags.Items.ARMORS).add(*ModItems.getEntriesIsInstance<ArmorItem>().toTypedArray())
        tag(Tags.Items.ARMORS_HELMETS).add(ModItems.PROMETHIUM_HELMET.get())
        tag(Tags.Items.ARMORS_CHESTPLATES).add(ModItems.PROMETHIUM_CHESTPLATE.get())
        tag(Tags.Items.ARMORS_LEGGINGS).add(ModItems.PROMETHIUM_LEGGINGS.get())
        tag(Tags.Items.ARMORS_BOOTS).add(ModItems.PROMETHIUM_BOOTS.get())

        tag(Tags.Items.CHESTS).add(ModItems.PROMETHEUS_BOUNTY.get())
        tag(Tags.Items.TOOLS_BOWS).add(*ModItems.getEntriesIsInstance<ItemSlingshot>().toTypedArray())
        tag(Tags.Items.TOOLS_CROSSBOWS).add(*ModItems.getEntriesIsInstance<ItemSlingshot>().toTypedArray())
        tag(Tags.Items.TOOLS_FISHING_RODS).add(*ModItems.getEntriesIsInstance<FishingRodItem>().toTypedArray())

        tag(ModTags.Item.NEPTUNIUM).add(
            ModItems.NEPTUNIUM_BULLET.get(),
            ModItems.NEPTUNIUM_SLINGSHOT.get(),
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
            ModItems.PROMETHIUM_SLINGSHOT.get(),
        )

        tag(ModTags.Item.TOOLTIP).add(
            ModItems.HYDROTHERMAL_HOOK.get(),
            ModItems.NEPTUNIUM_SLINGSHOT.get(),
            ModItems.NEPTUNIUM_BULLET.get(),
            ModItems.PROMETHIUM_SLINGSHOT.get(),
            ModItems.PROMETHIUM_BULLET.get(),
            ModItems.PROMETHIUM_HELMET.get(),
            ModItems.PROMETHIUM_CHESTPLATE.get(),
            ModItems.PROMETHIUM_LEGGINGS.get(),
            ModItems.PROMETHIUM_BOOTS.get(),
        )

        tag(AquacultureAPI.Tags.TOOLTIP).add(
            ModItemsAqua.DOUBLE_OBSIDIAN_HOOK.get(),
            ModItemsAqua.GLOWSTONE_HOOK.get(),
            ModItemsAqua.QUARTZ_HOOK.get(),
            ModItemsAqua.SOUL_SAND_HOOK.get(),
            ModItemsAqua.OBSIDIAN_NOTE_HOOK.get(),
        )
    }
}
