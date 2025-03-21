package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.item.ItemPromethiumArmor
import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.item.fish.ItemLavaFish
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.world.item.FishingRodItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.MobBucketItem
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.client.model.generators.ModelFile
import net.neoforged.neoforge.common.data.ExistingFileHelper
import org.slf4j.LoggerFactory

class ModDataProviderItemModel(
    packOutput: PackOutput,
    existingFileHelper: ExistingFileHelper,
) : ItemModelProvider(packOutput, BuiltConstants.MOD_ID, existingFileHelper) {

    override fun registerModels() {
        LoggerFactory.getLogger("ModItemModelProvider").info("Registering item models...")

        listOf(
            ModItems.getEntriesIsInstance<ItemLavaFish>(),
            ModItems.getEntriesIsInstance<ItemPromethiumArmor>(),
            ModItems.getEntriesIsInstance<ItemBullet>(),
            ModItems.getEntriesIsInstance<MobBucketItem>()
        ).flatten().forEach(::registerForCommon)

        // Food
        registerForCommon(ModItems.SPICY_FISH_FILLET.get())
        registerForCommon(ModItems.FISH_PASTE.get())

        // Misc
        registerForCommon(ModItems.PROMETHIUM_NUGGET.get())
        registerForCommon(ModItems.PROMETHIUM_INGOT.get())

        // Slingshot
        ModItems.getEntriesIsInstance<ItemSlingshot>().forEach(::registerForSlingshot)
        // FishingRod
        ModItems.getEntriesIsInstance<FishingRodItem>().forEach(::registerForFishingRod)
    }


    private fun registerForCommon(item: Item) {
        val path = BuiltInRegistries.ITEM.getKey(item).path
        withExistingParent("item/${path}", "item/generated")
            .texture("layer0", "item/${path}")
    }


    private fun registerForSlingshot(item: ItemSlingshot) {
        val path = BuiltInRegistries.ITEM.getKey(item).path
        val parent = LavaFishing.resourceLocation("item/template/slingshot")
        for (i in 0..2) {
            withExistingParent("item/${path}_pulling_${i}", parent)
                .texture("layer0", "item/${path}_pulling_${i}")
        }
        withExistingParent("item/${path}", parent)
            .texture("layer0", "item/${path}")
            .override()
            .predicate(mcLoc("pulling"), 1F)
            .model(ModelFile.UncheckedModelFile(LavaFishing.resourceLocation("item/${path}_pulling_0")))
            .end()
            .override()
            .predicate(mcLoc("pulling"), 1F)
            .predicate(mcLoc("pull"), 0.65F)
            .model(ModelFile.UncheckedModelFile(LavaFishing.resourceLocation("item/${path}_pulling_1")))
            .end()
            .override()
            .predicate(mcLoc("pulling"), 1F)
            .predicate(mcLoc("pull"), 0.9F)
            .model(ModelFile.UncheckedModelFile(LavaFishing.resourceLocation("item/${path}_pulling_2")))
            .end()
    }

    private fun registerForFishingRod(item: FishingRodItem) {
        val path = BuiltInRegistries.ITEM.getKey(item).path
        withExistingParent("item/${path}_cast", "item/handheld_rod")
            .texture("layer0", "item/${path}_cast")

        withExistingParent("item/${path}", "item/handheld_rod")
            .texture("layer0", "item/${path}")
            .override()
            .predicate(mcLoc("cast"), 1F)
            .model(ModelFile.UncheckedModelFile(LavaFishing.resourceLocation("item/${path}_cast")))
            .end()
    }
}