package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.block.BlockPrometheusBounty
import club.redux.sunset.lavafishing.misc.ModBlockProperties
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.registries.DeferredHolder

object ModBlocks {
    @JvmField val REGISTER = UtilRegister.create(BuiltInRegistries.BLOCK, BuildConstants.MOD_ID)
    @JvmField val ITEM_BLOCKS = mutableListOf<DeferredHolder<Block, Block>>()

    @JvmField val PROMETHIUM_BLOCK = registerItemBlock("promethium_block") { Block(ModBlockProperties.PROMETHIUM) }

    @JvmField val PROMETHEUS_BOUNTY = registerItemBlock("prometheus_bounty") { BlockPrometheusBounty() }

    private fun registerItemBlock(name: String, supplier: () -> Block): DeferredHolder<Block, Block> {
        return REGISTER.registerKt(name, supplier).also { ITEM_BLOCKS.add(it) }
    }
}
