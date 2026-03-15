package club.redux.sunset.lavafishing.datagenerator.sub.loot

import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import net.minecraft.data.loot.EntityLootSubProvider
import net.minecraft.world.entity.EntityType
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.item.Items
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
import net.minecraftforge.registries.ForgeRegistries
import java.util.stream.Stream

class ModSubProviderEntityLoot : EntityLootSubProvider(FeatureFlags.REGISTRY.allFlags()) {
    override fun generate() {
        ModEntityTypes.getEntriesByEntityParentClass<EntityLavaFish>()
            .forEach { entityTypeRegistryObject ->
                val entityType = entityTypeRegistryObject.get()
                val location = ForgeRegistries.ENTITY_TYPES.getKey(entityType) ?: return@forEach
                val fishItem = ForgeRegistries.ITEMS.getValue(location) ?: return@forEach
                this.add(
                    entityType,
                    LootTable.lootTable()
                        .withPool(
                            LootPool.lootPool()
                                .add(LootItem.lootTableItem(fishItem))
                        )
                        .withPool(
                            LootPool.lootPool()
                                .add(LootItem.lootTableItem(Items.BONE_MEAL))
                                .`when`(LootItemRandomChanceCondition.randomChance(0.05f))
                        )
                )
            }
    }

    override fun getKnownEntityTypes(): Stream<EntityType<*>> {
        return ModEntityTypes.getEntriesByEntityParentClass<EntityLavaFish>()
            .stream()
            .map { it.get() as EntityType<*> }
    }
}
