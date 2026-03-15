package club.redux.sunset.lavafishing.datagenerator.sub.loot

import club.redux.sunset.lavafishing.registry.ModBlocks
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.level.block.Block

class ModSubProviderBlockLoot : BlockLootSubProvider(setOf(), FeatureFlags.REGISTRY.allFlags()) {
    override fun generate() {
        listOf(
            ModBlocks.PROMETHIUM_BLOCK.get(),
            ModBlocks.PROMETHEUS_BOUNTY.get(),
        ).forEach(this::dropSelf)
    }

    override fun getKnownBlocks(): Iterable<Block> {
        return ModBlocks.getEntries().toMutableList()
    }
}
