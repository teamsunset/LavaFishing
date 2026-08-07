package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.client.renderer.item.property.ItemPropertySlingshotPull
import club.redux.sunset.lavafishing.item.ItemPromethiumArmor
import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.item.fish.ItemLavaFish
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.client.data.models.ModelProvider
import net.minecraft.client.data.models.model.ItemModelUtils
import net.minecraft.client.data.models.model.ModelTemplates
import net.minecraft.client.renderer.item.RangeSelectItemModel
import net.minecraft.client.renderer.special.ChestSpecialRenderer
import net.minecraft.core.Holder
import net.minecraft.data.PackOutput
import net.minecraft.resources.Identifier
import net.minecraft.world.item.FishingRodItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.MobBucketItem
import net.minecraft.world.level.block.Block
import java.util.stream.Stream

class ModDataProviderItemModel(packOutput: PackOutput) : ModelProvider(packOutput, BuiltConstants.MOD_ID) {
    override fun registerModels(
        blockModels: BlockModelGenerators,
        itemModels: ItemModelGenerators,
    ) {
        itemModels.apply {
            listOf(
                ModItems.getEntriesIsInstance<ItemLavaFish>(),
                ModItems.getEntriesIsInstance<ItemPromethiumArmor>(),
                ModItems.getEntriesIsInstance<ItemBullet>(),
                ModItems.getEntriesIsInstance<MobBucketItem>()
            ).flatten().forEach { registerForCommon(it) }

            // Food
            registerForCommon(ModItems.SPICY_FISH_FILLET.get())
            registerForCommon(ModItems.FISH_PASTE.get())

            // Misc
            registerForCommon(ModItems.PROMETHIUM_NUGGET.get())
            registerForCommon(ModItems.PROMETHIUM_INGOT.get())

            // Slingshot
            ModItems.getEntriesIsInstance<ItemSlingshot>()
                .forEach { registerForSlingshot(it) }
            // FishingRod
            ModItems.getEntriesIsInstance<FishingRodItem>()
                .forEach(::generateFishingRod)

            itemModels.declareCustomModelItem(ModItems.PROMETHIUM_BLOCK.get())
            itemModels.itemModelOutput.accept(
                ModItems.PROMETHEUS_BOUNTY.get(),
                ItemModelUtils.specialModel(
                    Identifier.withDefaultNamespace("item/chest"),
                    ChestSpecialRenderer.Unbaked(
                        Identifier.fromNamespaceAndPath(BuiltConstants.MOD_ID, "prometheus_bounty")
                    )
                )
            )
        }
    }

    private fun ItemModelGenerators.registerForCommon(item: Item) {
        this.generateFlatItem(item, ModelTemplates.FLAT_ITEM)
    }

    private fun ItemModelGenerators.registerForSlingshot(item: ItemSlingshot) {
        val base = ItemModelUtils.plainModel(this.createFlatItemModel(item, ModelTemplates.BOW))
        val pulling0 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_0", ModelTemplates.BOW))
        val pulling1 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_1", ModelTemplates.BOW))
        val pulling2 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_2", ModelTemplates.BOW))
        this.itemModelOutput.accept(
            item,
            ItemModelUtils.conditional(
                ItemModelUtils.isUsingItem(),
                ItemModelUtils.rangeSelect(
                    ItemPropertySlingshotPull(),
                    0.05f,
                    pulling0,
                    RangeSelectItemModel.Entry(0.65f, pulling1),
                    RangeSelectItemModel.Entry(0.9f, pulling2),
                ),
                base,
            ),
        )
    }

    override fun getKnownBlocks(): Stream<out Holder<Block>> = Stream.empty()
}
