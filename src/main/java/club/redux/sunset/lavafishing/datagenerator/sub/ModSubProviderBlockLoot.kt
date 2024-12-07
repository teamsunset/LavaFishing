package club.redux.sunset.lavafishing.datagenerator.sub

import club.redux.sunset.lavafishing.registry.ModBlocks
import net.minecraft.core.HolderLookup
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.level.block.Block

class ModSubProviderBlockLoot(
    lookupProvider: HolderLookup.Provider,
) : BlockLootSubProvider(setOf(), FeatureFlags.REGISTRY.allFlags(), lookupProvider) {
    override fun generate() {
        this.dropSelf(ModBlocks.PROMETHIUM_BLOCK.get())
        this.dropSelf(ModBlocks.PROMETHEUS_BOUNTY.get())
    }

    override fun getKnownBlocks(): MutableIterable<Block> {
        return ModBlocks.REGISTER.entries.map { it.get() }.toMutableList()
    }
}