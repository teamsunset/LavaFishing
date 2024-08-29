package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.block.blockentity.BlockEntityPrometheusBounty
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

object ModBlockEntityTypes {
    @JvmField val REGISTER = UtilRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BuildConstants.MOD_ID)

    @JvmField val PROMETHEUS_BOUNTY = REGISTER.registerKt("prometheus_bounty") {
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


