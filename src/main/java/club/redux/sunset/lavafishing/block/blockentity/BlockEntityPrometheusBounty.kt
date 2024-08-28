package club.redux.sunset.lavafishing.block.blockentity

import club.redux.sunset.lavafishing.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockState

class BlockEntityPrometheusBounty(
    pos: BlockPos,
    state: BlockState,
) : ChestBlockEntity(ModBlockEntityTypes.PROMETHEUS_BOUNTY.get(), pos, state) {
    override fun getDefaultName(): Component = Component.translatable(this.blockState.block.descriptionId)
}
