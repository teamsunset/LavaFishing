package club.redux.sunset.lavafishing.event

import club.redux.sunset.lavafishing.datagenerator.*
import net.minecraft.data.tags.TagsProvider
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
        generator.addProvider(event.includeServer(), ModDataProviderLootTable(packOutput, lookupProvider))
        generator.addProvider(event.includeClient(), ModDataProviderItemModel(packOutput, existingFileHelper))
        generator.addProvider(event.includeServer(), ModDataProviderBiomeModifier(packOutput))
        generator.addProvider(event.includeServer(), ModDataProviderRecipe(packOutput, lookupProvider))
        generator.addProvider(true, ModDataProviderLanguage(packOutput, Locale.PRC))
        generator.addProvider(true, ModDataProviderLanguage(packOutput, Locale.US))
    }
}