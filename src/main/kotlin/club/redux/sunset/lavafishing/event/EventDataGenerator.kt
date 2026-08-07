package club.redux.sunset.lavafishing.event

import club.redux.sunset.lavafishing.datagenerator.*
import club.redux.sunset.lavafishing.datagenerator.sub.loot.ModSubProviderBlockLoot
import club.redux.sunset.lavafishing.datagenerator.sub.loot.ModSubProviderBoxLoot
import club.redux.sunset.lavafishing.datagenerator.sub.loot.ModSubProviderEntityLoot
import club.redux.sunset.lavafishing.datagenerator.sub.loot.ModSubProviderFishingLoot
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.neoforged.neoforge.data.event.GatherDataEvent
import java.util.*


object EventDataGenerator {
    @JvmStatic
    fun onGatherClientData(event: GatherDataEvent.Client) {
        val packOutput = event.generator.packOutput

        event.addProvider(ModDataProviderItemModel(packOutput))
        event.addProvider(ModDataProviderLanguage(packOutput, Locale.PRC))
        event.addProvider(ModDataProviderLanguage(packOutput, Locale.US))
    }

    @JvmStatic
    fun onGatherServerData(event: GatherDataEvent.Server) {
        val packOutput = event.generator.packOutput
        val lookupProvider = event.lookupProvider

        event.addProvider(ModDataProviderItemTags(packOutput, lookupProvider))
        event.addProvider(ModDataProviderEntityTypeTags(packOutput, lookupProvider))
        event.addProvider(
            LootTableProvider(
                packOutput,
                setOf(),
                listOf(
                    SubProviderEntry(::ModSubProviderBlockLoot, LootContextParamSets.BLOCK),
                    SubProviderEntry(::ModSubProviderEntityLoot, LootContextParamSets.ENTITY),
                    SubProviderEntry(::ModSubProviderBoxLoot, LootContextParamSets.CHEST),
                    SubProviderEntry(::ModSubProviderFishingLoot, LootContextParamSets.FISHING),
                ),
                lookupProvider,
            )
        )
        event.addProvider(ModDataProviderBlockTags(packOutput, lookupProvider))
        event.addProvider(ModDataProviderBiomeModifier(packOutput))
        event.addProvider(ModDataProviderRecipe.Runner(packOutput, lookupProvider))
    }
}

// **VANILLA EXAMPLES**
//
//        val addSpawnsExample = ResourceKey.create<BiomeModifier>(
//            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
//            Identifier.fromNamespaceAndPath(BuiltConstants.MOD_ID, "add_spawns_example"),
//        )
//
//        val builder = RegistrySetBuilder().add(NeoForgeRegistries.Keys.BIOME_MODIFIERS) { bootstrap ->
//            val biomes: HolderGetter<Biome> = bootstrap.lookup(Registries.BIOME)
//
//            bootstrap.register(
//                addSpawnsExample,
//                AddSpawnsBiomeModifier.singleSpawn(
//                    HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS)),
//                    Weighted(SpawnerData(EntityType.GHAST, 5, 10), 1),
//                ),
//            )
//        }
//
//        event.addProvider(
//            DatapackBuiltinEntriesProvider(
//                packOutput,
//                lookupProvider,
//                builder,
//                setOf(BuiltConstants.MOD_ID),
//            ),
//        )
