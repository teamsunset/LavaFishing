package club.redux.sunset.lavafishing.event

import club.redux.sunset.lavafishing.datagenerator.*
import club.redux.sunset.lavafishing.datagenerator.sub.loot.ModSubProviderBlockLoot
import club.redux.sunset.lavafishing.datagenerator.sub.loot.ModSubProviderBoxLoot
import club.redux.sunset.lavafishing.datagenerator.sub.loot.ModSubProviderEntityLoot
import club.redux.sunset.lavafishing.datagenerator.sub.loot.ModSubProviderFishingLoot
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry
import net.minecraft.data.tags.TagsProvider
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.neoforged.neoforge.data.event.GatherDataEvent
import java.util.*
import java.util.concurrent.CompletableFuture


object EventDataGenerator {
    @JvmStatic
    fun onGatherData(event: GatherDataEvent) {
        val generator = event.generator
        val packOutput = event.generator.packOutput
        val existingFileHelper = event.existingFileHelper
        val lookupProvider = event.lookupProvider

        generator.addProvider(
            event.includeServer(),
            ModDataProviderItemTags(
                packOutput,
                lookupProvider,
                CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()),
                existingFileHelper
            )
        )
        generator.addProvider(
            event.includeServer(),
            ModDataProviderEntityTypeTags(packOutput, lookupProvider, existingFileHelper)
        )
        generator.addProvider(
            event.includeServer(),
            LootTableProvider(
                packOutput,
                setOf(),
                listOf(
                    SubProviderEntry(::ModSubProviderBlockLoot, LootContextParamSets.BLOCK),
                    SubProviderEntry(::ModSubProviderEntityLoot, LootContextParamSets.ENTITY),
                    SubProviderEntry(::ModSubProviderBoxLoot, LootContextParamSets.CHEST),
                    SubProviderEntry(::ModSubProviderFishingLoot, LootContextParamSets.FISHING),
                ),
                lookupProvider
            )
        )
        generator.addProvider(
            event.includeServer(),
            ModDataProviderBlockTags(packOutput, lookupProvider, existingFileHelper)
        )
        generator.addProvider(event.includeServer(), ModDataProviderBiomeModifier(packOutput))
        generator.addProvider(event.includeServer(), ModDataProviderRecipe(packOutput, lookupProvider))
        generator.addProvider(event.includeClient(), ModDataProviderItemModel(packOutput, existingFileHelper))
        generator.addProvider(true, ModDataProviderLanguage(packOutput, Locale.PRC))
        generator.addProvider(true, ModDataProviderLanguage(packOutput, Locale.US))
    }
}

// **VANILLA EXAMPLES**
//
//        val ADD_SPAWNS_EXAMPLE = ResourceKey.create<BiomeModifier>(
//            NeoForgeRegistries.Keys.BIOME_MODIFIERS,  // The registry this key is for
//            ResourceLocation.fromNamespaceAndPath(BuiltConstants.MOD_ID, "add_spawns_example") // The registry name
//        )
//
//        val builder = RegistrySetBuilder().add(NeoForgeRegistries.Keys.BIOME_MODIFIERS) { bootstrap ->
//            // Lookup any necessary registries.
//            // Static registries only need to be looked up if you need to grab the tag data.
//            val biomes: HolderGetter<Biome> = bootstrap.lookup(Registries.BIOME)
//
//            // Register the biome modifiers.
//            bootstrap.register(
//                ADD_SPAWNS_EXAMPLE,
//                AddSpawnsBiomeModifier( // The biome(s) to spawn the mobs within
//                    HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS)),  // The spawners of the entities to add
//                    listOf(
//                        SpawnerData(EntityType.GHAST, 1, 5, 10)
//                    )
//                )
//            )
//        }
//
//        event.generator.addProvider(
//            event.includeServer(), DatapackBuiltinEntriesProvider(
//                event.generator.packOutput,
//                event.lookupProvider,
//                builder,
//                mutableSetOf(event.modContainer.modId)
//            )
//        )