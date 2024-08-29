package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.LavaFishing
import com.teammetallurgy.aquaculture.init.AquaLootTables
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.entries.LootTableReference
import net.neoforged.neoforge.event.LootTableLoadEvent

object ModLootTables {
    @JvmField val FISH: ResourceLocation = register("gameplay/fishing/fish")
    @JvmField val LAVA_FISHING: ResourceLocation = register("gameplay/fishing/lava/fishing")
    @JvmField val LAVA_FISH: ResourceLocation = register("gameplay/fishing/lava/fish")
    @JvmField val LAVA_JUNK: ResourceLocation = register("gameplay/fishing/lava/junk")
    @JvmField val LAVA_TREASURE: ResourceLocation = register("gameplay/fishing/lava/treasure")
    @JvmField val NETHER_FISHING: ResourceLocation = register("gameplay/fishing/nether/fishing")
    @JvmField val NETHER_FISH: ResourceLocation = register("gameplay/fishing/nether/fish")
    @JvmField val NETHER_JUNK: ResourceLocation = register("gameplay/fishing/nether/junk")
    @JvmField val NETHER_TREASURE: ResourceLocation = register("gameplay/fishing/nether/treasure")

    private fun register(path: String): ResourceLocation {
        return BuiltInLootTables.register(LavaFishing.resourceLocation(path))
    }

    fun onLootTableLoad(event: LootTableLoadEvent) {
        val lootTablesInjects = HashMap<ResourceLocation, ResourceLocation>().apply {
            put(AquaLootTables.LAVA_FISHING, LAVA_FISHING)
            put(AquaLootTables.LAVA_FISH, LAVA_FISH)
            put(AquaLootTables.LAVA_JUNK, LAVA_JUNK)
            put(AquaLootTables.LAVA_TREASURE, LAVA_TREASURE)
            put(AquaLootTables.NETHER_FISHING, NETHER_FISHING)
            put(AquaLootTables.NETHER_FISH, NETHER_FISH)
            put(AquaLootTables.NETHER_JUNK, NETHER_JUNK)
            put(AquaLootTables.NETHER_TREASURE, NETHER_TREASURE)
        }

        lootTablesInjects.filter { it.key == event.name }.forEach { (k, v) ->
            event.table.removePool("main")
            event.table.addPool(
                LootPool.Builder()
                    .add(LootTableReference.lootTableReference(v))
                    .name(BuildConstants.MOD_ID + "_inject")
                    .build()
            )
        }
    }
}