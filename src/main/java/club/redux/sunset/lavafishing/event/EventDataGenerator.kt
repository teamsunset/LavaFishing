package club.redux.sunset.lavafishing.event

import club.redux.sunset.lavafishing.datagenerator.*
import club.redux.sunset.lavafishing.datagenerator.sub.ModSubProviderBlockLoot
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.data.tags.TagsProvider
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.minecraftforge.data.event.GatherDataEvent
import java.util.*
import java.util.concurrent.CompletableFuture

object EventDataGenerator {
    @JvmStatic
    fun onGatherData(event: GatherDataEvent) {
        event.generator.apply {
            addProvider(true, ModDataProviderRecipe(packOutput))
            addProvider(
                event.includeServer(),
                ModDataProviderItemTags(
                    packOutput,
                    event.lookupProvider,
                    CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()),
                    event.existingFileHelper
                )
            )
            addProvider(
                event.includeServer(),
                ModDataProviderBlockTags(packOutput, event.lookupProvider, event.existingFileHelper)
            )
            addProvider(
                event.includeServer(),
                ModDataProviderLootTable(
                    packOutput,
                    listOf(LootTableProvider.SubProviderEntry(::ModSubProviderBlockLoot, LootContextParamSets.BLOCK)),
                )
            )
            addProvider(event.includeClient(), ModDataProviderItemModel(packOutput, event.existingFileHelper))
            addProvider(true, ModDataProviderLanguage(packOutput, Locale.PRC))
            addProvider(true, ModDataProviderLanguage(packOutput, Locale.US))
            addProvider(true, ModDataProviderBiomeModifier(packOutput))
            addProvider(true, ModDataProviderEntityTypeTags(packOutput, event.lookupProvider, event.existingFileHelper))
        }
    }
}