package club.redux.sunset.lavafishing.datagenerator.sub.loot

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.registry.ModBlockEntityTypes
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.tool.datagenerator.SubProviderLoot
import net.minecraft.advancements.critereon.LocationPredicate
import net.minecraft.world.item.Items
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.LootTableReference
import net.minecraft.world.level.storage.loot.functions.SetContainerContents
import net.minecraft.world.level.storage.loot.predicates.LocationCheck
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue

class ModSubProviderFishingLoot : SubProviderLoot() {
    override fun generate() {
        this.addAll(this.getFishingLavaTables())
        this.addAll(this.getFishingNetherTables())
    }

    private fun getFishingLavaTables(): List<TableBuilderEntry> {
        val junkLocation = this.createLocation(BuildConstants.MOD_ID, "gameplay/fishing/lava/junk")
        val junk = TableBuilderEntry(
            junkLocation,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.COAL).setWeight(50))
                    .add(LootItem.lootTableItem(Items.FLINT).setWeight(50))
            )
        )

        val fishLocation = this.createLocation(BuildConstants.MOD_ID, "gameplay/fishing/lava/fish")
        val fish = TableBuilderEntry(
            fishLocation,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.FLAME_SQUAT_LOBSTER.get()).setWeight(34))
                    .add(LootItem.lootTableItem(ModItems.OBSIDIAN_SWORD_FISH.get()).setWeight(33))
                    .add(LootItem.lootTableItem(ModItems.AROWANA_FISH.get()).setWeight(33))
            )
        )

        val treasureLocation = this.createLocation(BuildConstants.MOD_ID, "gameplay/fishing/lava/treasure")
        val treasure = TableBuilderEntry(
            treasureLocation,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.PROMETHIUM_NUGGET.get()).setWeight(50))
                    .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(50))
            )
        )

        val fishing = TableBuilderEntry(
            this.createLocation(BuildConstants.MOD_ID, "gameplay/fishing/lava/fishing"),
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(LootTableReference.lootTableReference(junkLocation).setWeight(10).setQuality(-2))
                    .add(LootTableReference.lootTableReference(fishLocation).setWeight(85).setQuality(-1))
                    .add(LootTableReference.lootTableReference(treasureLocation).setWeight(5).setQuality(2))
            )
        )

        return listOf(junk, fish, treasure, fishing)
    }

    private fun getFishingNetherTables(): List<TableBuilderEntry> {
        val junkLocation = this.createLocation(BuildConstants.MOD_ID, "gameplay/fishing/nether/junk")
        val lavaJunkLocation = this.createLocation(BuildConstants.MOD_ID, "gameplay/fishing/lava/junk")
        val junk = TableBuilderEntry(
            junkLocation,
            LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootTableReference.lootTableReference(lavaJunkLocation))
            )
        )

        val fishLocation = this.createLocation(BuildConstants.MOD_ID, "gameplay/fishing/nether/fish")
        val lavaFishLocation = this.createLocation(BuildConstants.MOD_ID, "gameplay/fishing/lava/fish")
        val fish = TableBuilderEntry(
            fishLocation,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(LootTableReference.lootTableReference(lavaFishLocation).setWeight(30))
                    .add(
                        LootItem.lootTableItem(ModItems.STEAM_FLYING_FISH.get()).setWeight(12)
                            .`when`(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.SOUL_SAND_VALLEY)))
                    )
                    .add(
                        LootItem.lootTableItem(ModItems.AGNI_FISH.get()).setWeight(10)
                            .`when`(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.CRIMSON_FOREST)))
                    )
                    .add(
                        LootItem.lootTableItem(ModItems.SCALY_FOOT_SNAIL.get()).setWeight(12)
                            .`when`(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.BASALT_DELTAS)))
                    )
                    .add(
                        LootItem.lootTableItem(ModItems.YETI_CRAB.get()).setWeight(12)
                            .`when`(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.BASALT_DELTAS)))
                    )
                    .add(LootItem.lootTableItem(ModItems.LAVA_LAMPREY.get()).setWeight(12))
                    .add(LootItem.lootTableItem(ModItems.QUARTZ_FISH.get()).setWeight(12))
            )
        )

        val treasureLocation = this.createLocation(BuildConstants.MOD_ID, "gameplay/fishing/nether/treasure")
        val lavaTreasureLocation = this.createLocation(BuildConstants.MOD_ID, "gameplay/fishing/lava/treasure")
        val prometheusBountyLocation = this.createLocation(BuildConstants.MOD_ID, "box/prometheus_bounty")
        val treasure = TableBuilderEntry(
            treasureLocation,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(LootTableReference.lootTableReference(lavaTreasureLocation).setWeight(65))
                    .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(25))
                    .add(
                        LootItem.lootTableItem(ModItems.PROMETHEUS_BOUNTY.get()).setWeight(10)
                            .apply(
                                SetContainerContents.setContents(ModBlockEntityTypes.PROMETHEUS_BOUNTY.get())
                                    .withEntry(LootTableReference.lootTableReference(prometheusBountyLocation))
                            )
                    )
            )
        )

        val fishing = TableBuilderEntry(
            this.createLocation(BuildConstants.MOD_ID, "gameplay/fishing/nether/fishing"),
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(LootTableReference.lootTableReference(junkLocation).setWeight(10).setQuality(-2))
                    .add(LootTableReference.lootTableReference(fishLocation).setWeight(85).setQuality(-1))
                    .add(LootTableReference.lootTableReference(treasureLocation).setWeight(5).setQuality(2))
            )
        )

        return listOf(junk, fish, treasure, fishing)
    }
}
