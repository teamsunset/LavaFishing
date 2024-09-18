package club.redux.sunset.lavafishing.event

import club.redux.sunset.lavafishing.datagenerator.*
import net.minecraft.data.tags.TagsProvider
import net.neoforged.neoforge.data.event.GatherDataEvent
import java.util.*
import java.util.concurrent.CompletableFuture

object EventDataGenerator {
    @JvmStatic
    fun onGatherData(event: GatherDataEvent) {
        event.generator.apply {
            addProvider(true, ModDataProviderRecipe(packOutput, event.lookupProvider))
            addProvider(
                event.includeServer(),
                ModDataProviderItemTags(
                    packOutput,
                    event.lookupProvider,
                    CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()),
                    event.existingFileHelper
                )
            )
            addProvider(event.includeServer(), ModDataProviderLootTable(packOutput, event.lookupProvider))
            addProvider(event.includeClient(), ModDataProviderItemModel(packOutput, event.existingFileHelper))
            addProvider(true, ModDataProviderLanguage(packOutput, Locale.PRC))
            addProvider(true, ModDataProviderLanguage(packOutput, Locale.US))
            addProvider(true, ModDataProviderBiomeModifier(packOutput))
            addProvider(true, ModDataProviderEntityTypeTags(packOutput, event.lookupProvider, event.existingFileHelper))
        }
    }
}