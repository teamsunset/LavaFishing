package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.registry.ModTags
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.EntityTypeTagsProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModDataProviderEntityTypeTags(
    packOutput: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    existingFileHelper: ExistingFileHelper,
) : EntityTypeTagsProvider(packOutput, lookupProvider, BuildConstants.MOD_ID, existingFileHelper) {
    override fun addTags(pProvider: HolderLookup.Provider) {
        super.addTags(pProvider)
        ModEntityTypes.getEntriesByEntityParentClass(EntityLavaFish::class.java).forEach {
            tag(ModTags.EntityTypes.LAVA_FISH).add(it.get())
        }
    }
}