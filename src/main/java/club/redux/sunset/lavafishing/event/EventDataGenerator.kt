package club.redux.sunset.lavafishing.event

import club.redux.sunset.lavafishing.datagenerator.*
import net.minecraft.data.tags.TagsProvider
import net.minecraftforge.data.event.GatherDataEvent
import java.util.*
import java.util.concurrent.CompletableFuture

object EventDataGenerator {
    @JvmStatic
    fun onGatherData(event: GatherDataEvent) {
        event.generator.apply {
            addProvider(true, ModRecipeProvider(packOutput))
            addProvider(
                event.includeServer(),
                ModItemTagProvider(
                    packOutput,
                    event.lookupProvider,
                    CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()),
                    event.existingFileHelper
                )
            )
            addProvider(event.includeServer(), ModLootTableProvider(packOutput))
            addProvider(event.includeClient(), ModItemModelProvider(packOutput, event.existingFileHelper))
            addProvider(true, ModLanguageProvider(packOutput, Locale.PRC))
            addProvider(true, ModLanguageProvider(packOutput, Locale.US))
        }
    }
}