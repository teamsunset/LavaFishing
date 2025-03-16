package club.redux.sunset.lavafishing.datagenerator.sub.loot

import club.redux.sunset.lavafishing.registry.ModBlocks
import net.minecraft.core.HolderLookup
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.flag.FeatureFlags

class ModSubProviderBlockLoot(
    lookupProvider: HolderLookup.Provider,
) : BlockLootSubProvider(setOf(), FeatureFlags.REGISTRY.allFlags(), lookupProvider) {
    override fun generate() {
        listOf(
            ModBlocks.PROMETHIUM_BLOCK.get(),
            ModBlocks.PROMETHEUS_BOUNTY.get()
        ).forEach(this::dropSelf)
    }

    override fun getKnownBlocks() = ModBlocks.getEntries().toMutableList()
}