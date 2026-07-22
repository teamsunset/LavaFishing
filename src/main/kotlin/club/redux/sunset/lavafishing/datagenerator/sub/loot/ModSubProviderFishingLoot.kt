package club.redux.sunset.lavafishing.datagenerator.sub.loot

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.tool.datagenerator.SubProviderLoot
import net.minecraft.advancements.criterion.LocationPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Items
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.storage.loot.ContainerComponentManipulators
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.NestedLootTable
import net.minecraft.world.level.storage.loot.functions.SetContainerContents
import net.minecraft.world.level.storage.loot.predicates.LocationCheck
import net.neoforged.neoforge.common.Tags

class ModSubProviderFishingLoot(lookupProvider: HolderLookup.Provider) : SubProviderLoot(lookupProvider) {
    private val biomeLookup = lookupProvider.lookupOrThrow(Registries.BIOME)

    override fun generate() {
        this.addAll(this.getFishingLavaTables())
        this.addAll(this.getFishingNetherTables())
    }

    private fun getFishingLavaTables(): List<TableBuilderEntry> {
        val junkKey = this.createKey(BuiltConstants.MOD_ID, "gameplay/fishing/lava/junk")
        val junk = TableBuilderEntry(
            junkKey,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.COAL).setWeight(50))
                    .add(LootItem.lootTableItem(Items.FLINT).setWeight(50))
            )
        )

        val fishKey = this.createKey(BuiltConstants.MOD_ID, "gameplay/fishing/lava/fish")
        val fish = TableBuilderEntry(
            fishKey,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.FLAME_SQUAT_LOBSTER.get()).setWeight(34))
                    .add(LootItem.lootTableItem(ModItems.OBSIDIAN_SWORD_FISH.get()).setWeight(33))
                    .add(LootItem.lootTableItem(ModItems.AROWANA_FISH.get()).setWeight(33))
            )
        )

        val treasureKey = this.createKey(BuiltConstants.MOD_ID, "gameplay/fishing/lava/treasure")
        val treasure = TableBuilderEntry(
            treasureKey,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.PROMETHIUM_NUGGET.get()).setWeight(50))
                    .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(50))
            )
        )

        val fishing = TableBuilderEntry(
            this.createKey(BuiltConstants.MOD_ID, "gameplay/fishing/lava/fishing"),
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(NestedLootTable.lootTableReference(junkKey).setWeight(10).setQuality(-2))
                    .add(NestedLootTable.lootTableReference(fishKey).setWeight(85).setQuality(-1))
                    .add(NestedLootTable.lootTableReference(treasureKey).setWeight(5).setQuality(2))
            )
        )

        return listOf(junk, fish, treasure, fishing)
    }

    private fun getFishingNetherTables(): List<TableBuilderEntry> {
        val junkKey = this.createKey(BuiltConstants.MOD_ID, "gameplay/fishing/nether/junk")
        val lavaJunkKey = this.createKey(BuiltConstants.MOD_ID, "gameplay/fishing/lava/junk")
        val junk = TableBuilderEntry(
            junkKey,
            LootTable.lootTable().withPool(
                LootPool.lootPool().add(NestedLootTable.lootTableReference(lavaJunkKey))
            )
        )

        val fishKey = this.createKey(BuiltConstants.MOD_ID, "gameplay/fishing/nether/fish")
        val lavaFishKey = this.createKey(BuiltConstants.MOD_ID, "gameplay/fishing/lava/fish")
        val fish = TableBuilderEntry(
            fishKey,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(NestedLootTable.lootTableReference(lavaFishKey).setWeight(30))
                    .add(
                        LootItem.lootTableItem(ModItems.STEAM_FLYING_FISH.get()).setWeight(12).`when`(
                            LocationCheck.checkLocation(
                                LocationPredicate.Builder.inBiome(this.biomeLookup.getOrThrow(Biomes.SOUL_SAND_VALLEY))
                            )
                        )
                    )
                    .add(
                        LootItem.lootTableItem(ModItems.AGNI_FISH.get()).setWeight(10).`when`(
                            LocationCheck.checkLocation(
                                LocationPredicate.Builder.location()
                                    .setBiomes(this.biomeLookup.getOrThrow(Tags.Biomes.IS_NETHER_FOREST))
                            )
                        )
                    )
                    .add(
                        LootItem.lootTableItem(ModItems.SCALY_FOOT_SNAIL.get()).setWeight(12).`when`(
                            LocationCheck.checkLocation(
                                LocationPredicate.Builder.inBiome(this.biomeLookup.getOrThrow(Biomes.BASALT_DELTAS))
                            )
                        )
                    )
                    .add(
                        LootItem.lootTableItem(ModItems.YETI_CRAB.get()).setWeight(12).`when`(
                            LocationCheck.checkLocation(
                                LocationPredicate.Builder.inBiome(this.biomeLookup.getOrThrow(Biomes.BASALT_DELTAS))
                            )
                        )
                    )
                    .add(LootItem.lootTableItem(ModItems.LAVA_LAMPREY.get()).setWeight(12))
                    .add(LootItem.lootTableItem(ModItems.QUARTZ_FISH.get()).setWeight(12))
            )
        )

        val treasureKey = this.createKey(BuiltConstants.MOD_ID, "gameplay/fishing/nether/treasure")
        val lavaTreasureKey = this.createKey(BuiltConstants.MOD_ID, "gameplay/fishing/lava/treasure")
        val prometheusBountyKey = this.createKey(BuiltConstants.MOD_ID, "box/prometheus_bounty")
        val treasure = TableBuilderEntry(
            treasureKey,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(NestedLootTable.lootTableReference(lavaTreasureKey).setWeight(65))
                    .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(25))
                    .add(
                        LootItem.lootTableItem(ModItems.PROMETHEUS_BOUNTY.get()).setWeight(10)
                            .apply(
                                SetContainerContents.setContents(ContainerComponentManipulators.CONTAINER)
                                    .withEntry(NestedLootTable.lootTableReference(prometheusBountyKey))
                            )
                    )
            )
        )

        val fishing = TableBuilderEntry(
            this.createKey(BuiltConstants.MOD_ID, "gameplay/fishing/nether/fishing"),
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(NestedLootTable.lootTableReference(junkKey).setWeight(10).setQuality(-2))
                    .add(NestedLootTable.lootTableReference(fishKey).setWeight(85).setQuality(-1))
                    .add(NestedLootTable.lootTableReference(treasureKey).setWeight(5).setQuality(2))
            )
        )

        return listOf(junk, fish, treasure, fishing)
    }
}
