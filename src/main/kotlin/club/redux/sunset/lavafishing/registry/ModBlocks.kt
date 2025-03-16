package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.block.BlockPrometheusBounty
import club.redux.sunset.lavafishing.misc.ModBlockProperties
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block

object ModBlocks : Registrar<Block>(BuiltInRegistries.BLOCK, BuiltConstants.MOD_ID) {
    val PROMETHIUM_BLOCK by this.register { Block(ModBlockProperties.PROMETHIUM) }
    val PROMETHEUS_BOUNTY by this.register { BlockPrometheusBounty() }
}
