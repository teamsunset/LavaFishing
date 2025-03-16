package club.redux.sunset.lavafishing.datagenerator.sub.loot

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.tool.datagenerator.SubProviderLoot
import net.minecraft.advancements.critereon.LocationPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Items
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.levelgen.structure.BuiltinStructures
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.NestedLootTable
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.LocationCheck
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator

class ModSubProviderBoxLoot(lookupProvider: HolderLookup.Provider) : SubProviderLoot(lookupProvider) {
    private val biomeLookup = lookupProvider.lookupOrThrow(Registries.BIOME)
    private val structureLookup = lookupProvider.lookupOrThrow(Registries.STRUCTURE)

    override fun generate() {
        this.addAll(this.getBoxTables())
    }

    private fun getBoxTables(): List<TableBuilderEntry> {
        val junkKey = this.createKey(BuiltConstants.MOD_ID, "box/prometheus_bounty/junk")
        val junk = TableBuilderEntry(
            junkKey,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(UniformGenerator.between(2f, 8f))
                    .add(LootItem.lootTableItem(Items.AIR).setWeight(15))
                    .add(
                        LootItem.lootTableItem(Items.WEEPING_VINES).setWeight(5).`when`(
                            LocationCheck.checkLocation(
                                LocationPredicate.Builder.inBiome(this.biomeLookup.getOrThrow(Biomes.CRIMSON_FOREST))
                            )
                        )
                    )
                    .add(
                        LootItem.lootTableItem(Items.TWISTING_VINES).setWeight(5).`when`(
                            LocationCheck.checkLocation(
                                LocationPredicate.Builder.inBiome(this.biomeLookup.getOrThrow(Biomes.WARPED_FOREST))
                            )
                        )
                    )
                    .add(
                        LootItem.lootTableItem(Items.CRIMSON_FUNGUS).setWeight(5).`when`(
                            LocationCheck.checkLocation(
                                LocationPredicate.Builder.inBiome(this.biomeLookup.getOrThrow(Biomes.CRIMSON_FOREST))
                            )
                        )
                    )
                    .add(
                        LootItem.lootTableItem(Items.WARPED_FUNGUS).setWeight(5).`when`(
                            LocationCheck.checkLocation(
                                LocationPredicate.Builder.inBiome(this.biomeLookup.getOrThrow(Biomes.WARPED_FOREST))
                            )
                        )
                    )
                    .add(LootItem.lootTableItem(Items.BONE_MEAL).setWeight(5))
                    .add(
                        LootItem.lootTableItem(Items.MAGMA_BLOCK).setWeight(5).`when`(
                            LocationCheck.checkLocation(
                                LocationPredicate.Builder.inBiome(this.biomeLookup.getOrThrow(Biomes.BASALT_DELTAS))
                            )
                        )
                    )
                    .add(
                        LootItem.lootTableItem(Items.NETHER_WART).setWeight(5).`when`(
                            LocationCheck.checkLocation(
                                LocationPredicate.Builder.inStructure(this.structureLookup.getOrThrow(BuiltinStructures.FORTRESS))
                            )
                        )
                    )
            )
        )

        val treasureKey = this.createKey(BuiltConstants.MOD_ID, "box/prometheus_bounty/treasure")
        val treasure = TableBuilderEntry(
            treasureKey,
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

        val prometheusBountyKey = this.createKey(BuiltConstants.MOD_ID, "box/prometheus_bounty")
        val prometheusBounty = TableBuilderEntry(
            prometheusBountyKey,
            LootTable.lootTable()
                .withPool(
                    LootPool.lootPool().add(NestedLootTable.lootTableReference(junkKey).setWeight(100))
                )
                .withPool(
                    LootPool.lootPool().add(NestedLootTable.lootTableReference(treasureKey).setWeight(100))
                )
                .withPool(
                    LootPool.lootPool().add(NestedLootTable.lootTableReference(junkKey).setWeight(100))
                )
        )

        return listOf(junk, treasure, prometheusBounty)
    }
}