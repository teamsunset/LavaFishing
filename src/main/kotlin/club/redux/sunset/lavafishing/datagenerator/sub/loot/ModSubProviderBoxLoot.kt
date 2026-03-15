package club.redux.sunset.lavafishing.datagenerator.sub.loot

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.registry.ModBlockEntityTypes
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.tool.datagenerator.SubProviderLoot
import net.minecraft.advancements.critereon.LocationPredicate
import net.minecraft.world.item.Items
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.levelgen.structure.BuiltinStructures
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.LootTableReference
import net.minecraft.world.level.storage.loot.functions.SetContainerContents
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.LocationCheck
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator

class ModSubProviderBoxLoot : SubProviderLoot() {
    override fun generate() {
        this.addAll(this.getBoxTables())
    }

    private fun getBoxTables(): List<TableBuilderEntry> {
        val junkLocation = this.createLocation(BuildConstants.MOD_ID, "box/prometheus_bounty/junk")
        val junk = TableBuilderEntry(
            junkLocation,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(UniformGenerator.between(2f, 8f))
                    .add(LootItem.lootTableItem(Items.AIR).setWeight(15))
                    .add(
                        LootItem.lootTableItem(Items.WEEPING_VINES).setWeight(5)
                            .`when`(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.CRIMSON_FOREST)))
                    )
                    .add(
                        LootItem.lootTableItem(Items.TWISTING_VINES).setWeight(5)
                            .`when`(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.WARPED_FOREST)))
                    )
                    .add(
                        LootItem.lootTableItem(Items.CRIMSON_FUNGUS).setWeight(5)
                            .`when`(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.CRIMSON_FOREST)))
                    )
                    .add(
                        LootItem.lootTableItem(Items.WARPED_FUNGUS).setWeight(5)
                            .`when`(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.WARPED_FOREST)))
                    )
                    .add(LootItem.lootTableItem(Items.BONE_MEAL).setWeight(5))
                    .add(
                        LootItem.lootTableItem(Items.MAGMA_BLOCK).setWeight(5)
                            .`when`(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.BASALT_DELTAS)))
                    )
                    .add(
                        LootItem.lootTableItem(Items.NETHER_WART).setWeight(5)
                            .`when`(LocationCheck.checkLocation(LocationPredicate.Builder.location().setStructure(BuiltinStructures.FORTRESS)))
                    )
            )
        )

        val treasureLocation = this.createLocation(BuildConstants.MOD_ID, "box/prometheus_bounty/treasure")
        val treasure = TableBuilderEntry(
            treasureLocation,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1f))
                    .add(
                        LootItem.lootTableItem(ModItems.PROMETHIUM_INGOT.get()).setWeight(2)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 4f)))
                    )
                    .add(LootItem.lootTableItem(ModItems.PROMETHIUM_HELMET.get()).setWeight(1))
                    .add(LootItem.lootTableItem(ModItems.PROMETHIUM_CHESTPLATE.get()).setWeight(1))
                    .add(LootItem.lootTableItem(ModItems.PROMETHIUM_LEGGINGS.get()).setWeight(1))
                    .add(LootItem.lootTableItem(ModItems.PROMETHIUM_BOOTS.get()).setWeight(1))
            )
        )

        val prometheusBountyLocation = this.createLocation(BuildConstants.MOD_ID, "box/prometheus_bounty")
        val prometheusBounty = TableBuilderEntry(
            prometheusBountyLocation,
            LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(junkLocation)).setRolls(ConstantValue.exactly(1f)))
                .withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(treasureLocation)).setRolls(ConstantValue.exactly(1f)))
                .withPool(LootPool.lootPool().add(LootTableReference.lootTableReference(junkLocation)).setRolls(ConstantValue.exactly(1f)))
        )

        return listOf(junk, treasure, prometheusBounty)
    }
}
