package club.redux.sunset.lavafishing.registry

import club.asynclab.web.BuildConstants
import club.redux.sunset.lavafishing.block.blockentity.BlockEntityPrometheusBounty
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.registries.ForgeRegistries

object RegistryBlockEntityType {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BuildConstants.MOD_ID)

    @JvmField val PROMETHEUS_BOUNTY = REGISTER.registerKt("prometheus_bounty") {
        BlockEntityType.Builder.of(
            { pos: BlockPos, state: BlockState -> BlockEntityPrometheusBounty(pos, state) },
            RegistryBlock.PROMETHEUS_BOUNTY.get()
        ).build()
    }

    @Suppress("Type_mismatch")
    fun <T : BlockEntity> BlockEntityType.Builder<T>.build(): BlockEntityType<T> {
        return this.build(null)
    }
}


