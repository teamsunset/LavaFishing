package club.redux.sunset.lavafishing.datagenerator.sub.loot

import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.loot.EntityLootSubProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.item.Items
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition

class ModSubProviderEntityLoot(
    lookupProvider: HolderLookup.Provider,
) : EntityLootSubProvider(FeatureFlags.REGISTRY.allFlags(), lookupProvider) {
    override fun generate() {
        ModEntityTypes.getHoldersByEntityClass<EntityLavaFish>()
            .forEach {
                this.add(
                    it.get(),
                    LootTable.lootTable().withPool(
                        LootPool.lootPool()
                            .add(LootItem.lootTableItem(BuiltInRegistries.ITEM.get(it.key!!.location())))
                    ).withPool(
                        LootPool.lootPool()
                            .add(LootItem.lootTableItem(Items.BONE_MEAL))
                            .`when`(LootItemRandomChanceCondition.randomChance(0.05f))
                    )
                )
            }
    }

    override fun getKnownEntityTypes() = ModEntityTypes.getEntries().stream()
}