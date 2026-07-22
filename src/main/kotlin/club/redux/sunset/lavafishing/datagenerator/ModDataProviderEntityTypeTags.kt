package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.misc.ModTags
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import com.teammetallurgy.aquaculture.init.FishRegistry
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.EntityTypeTagsProvider
import net.minecraft.tags.EntityTypeTags
import java.util.concurrent.CompletableFuture

class ModDataProviderEntityTypeTags(
    packOutput: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
) : EntityTypeTagsProvider(packOutput, lookupProvider, BuiltConstants.MOD_ID) {
    override fun addTags(pProvider: HolderLookup.Provider) {
        super.addTags(pProvider)
        tag(ModTags.EntityType.LAVA_FISH).add(*ModEntityTypes.getEntitiesByEntityClass<EntityLavaFish>().toTypedArray())

        FishRegistry.fishEntities.forEach { tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER).add(it.get()) }
    }
}
