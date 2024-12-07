package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.misc.ModResourceLocation
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import net.minecraft.data.PackOutput
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
import net.minecraftforge.registries.ForgeRegistries

class ModDataProviderLootTable(
    pOutput: PackOutput,
    subProviders: List<SubProviderEntry>,
) : LootTableProvider(pOutput, emptySet(), subProviders) {

    override fun getTables(): MutableList<SubProviderEntry> {
        val tables = super.getTables().toMutableList()

        val build =
            { resourceLocation: ResourceLocation, builder: LootTable.Builder, lootContextParamSet: LootContextParamSet ->
                SubProviderEntry(
                    { LootTableSubProvider { it.accept(resourceLocation, builder) } },
                    lootContextParamSet
                )
            }

        ModEntityTypes.getEntriesByEntityParentClass(EntityLavaFish::class.java)
            .forEach { entityTypeRegistryObject ->
                val location = entityTypeRegistryObject.key!!.location()
                build(
                    ModResourceLocation("entities/${location.path}"),
                    LootTable.lootTable().withPool(
                        LootPool.lootPool()
                            .add(LootItem.lootTableItem(ForgeRegistries.ITEMS.getValue(location)!!))
                    ).withPool(
                        LootPool.lootPool()
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .`when`(LootItemRandomChanceCondition.randomChance(0.05f))
                    ),
                    LootContextParamSets.ENTITY
                ).let { tables.add(it) }
            }

        return tables
    }
}