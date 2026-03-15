package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.block.blockentity.BlockEntityPrometheusBounty
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

object ModBlockEntityTypes : Registrar<BlockEntityType<*>>(BuiltInRegistries.BLOCK_ENTITY_TYPE, BuiltConstants.MOD_ID) {

    val PROMETHEUS_BOUNTY by this.register {
        BlockEntityType.Builder.of(
            { pos: BlockPos, state: BlockState -> BlockEntityPrometheusBounty(pos, state) },
            ModBlocks.PROMETHEUS_BOUNTY.get()
        ).build()
    }

    @Suppress("Type_mismatch")
    fun <T : BlockEntity> BlockEntityType.Builder<T>.build(): BlockEntityType<T> {
        return this.build(null)
    }
}
