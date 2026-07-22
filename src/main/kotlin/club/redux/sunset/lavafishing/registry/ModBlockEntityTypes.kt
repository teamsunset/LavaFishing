package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.block.blockentity.BlockEntityPrometheusBounty
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntityType

object ModBlockEntityTypes : Registrar<BlockEntityType<*>>(BuiltInRegistries.BLOCK_ENTITY_TYPE, BuiltConstants.MOD_ID) {

    val PROMETHEUS_BOUNTY by this.register {
        BlockEntityType(::BlockEntityPrometheusBounty, ModBlocks.PROMETHEUS_BOUNTY.get())
    }
}
