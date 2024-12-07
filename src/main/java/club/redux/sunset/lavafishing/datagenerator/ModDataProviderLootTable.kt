package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Items
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
import java.util.concurrent.CompletableFuture

class ModDataProviderLootTable(
    pOutput: PackOutput,
    subProviders: List<SubProviderEntry>,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
) : LootTableProvider(pOutput, emptySet(), subProviders, lookupProvider) {

    override fun getTables(): List<SubProviderEntry> {
        val tables = super.getTables().toMutableList()

        val build =
            { resourceKey: ResourceKey<LootTable>, builder: LootTable.Builder, lootContextParamSet: LootContextParamSet ->
                SubProviderEntry(
                    { LootTableSubProvider { it.accept(resourceKey, builder) } },
                    lootContextParamSet
                )
            }

        ModEntityTypes.getEntriesByEntityParentClass(EntityLavaFish::class.java)
            .forEach { entry ->
                val location = entry.key!!.location()
                build(
                    ResourceKey.create(
                        Registries.LOOT_TABLE,
                        LavaFishing.resourceLocation("entities/${location.path}")
                    ),
                    LootTable.lootTable().withPool(
                        LootPool.lootPool()
                            .add(LootItem.lootTableItem(BuiltInRegistries.ITEM.get(location)))
                    ).withPool(
                        LootPool.lootPool()
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .`when`(LootItemRandomChanceCondition.randomChance(0.05f))
                    ),
                    LootContextParamSets.ENTITY
                ).let { tables.add(it) }
            }

//        ModBlocks.ITEM_BLOCKS.forEach { entry ->
//            build(
//                ResourceKey.create(
//                    Registries.LOOT_TABLE,
//                    LavaFishing.resourceLocation("blocks/${entry.key!!.location().path}")
//                ),
//                LootTable.lootTable().withPool(
//                    LootPool.lootPool()
//                        .add(LootItem.lootTableItem(Item.byBlock(entry.get())))
//                ),
//                LootContextParamSets.BLOCK
//            ).let { tables.add(it) }
//        }

        return tables
    }

}