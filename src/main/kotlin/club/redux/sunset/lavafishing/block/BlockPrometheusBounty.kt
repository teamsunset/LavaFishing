package club.redux.sunset.lavafishing.block

import club.redux.sunset.lavafishing.block.blockentity.BlockEntityPrometheusBounty
import club.redux.sunset.lavafishing.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.RandomSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.ScheduledTickAccess
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.ChestType
import net.minecraft.world.level.material.Fluids
import java.util.function.Supplier

class BlockPrometheusBounty(properties: Properties) : ChestBlock(
    Supplier<BlockEntityType<out ChestBlockEntity>> { ModBlockEntityTypes.PROMETHEUS_BOUNTY.get() },
    SoundEvents.CHEST_OPEN,
    SoundEvents.CHEST_CLOSE,
    properties,
) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return BlockEntityPrometheusBounty(pos, state)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val fluidState = context.level.getFluidState(context.clickedPos)
        return defaultBlockState()
            .setValue(TYPE, ChestType.SINGLE)
            .setValue(FACING, context.horizontalDirection.opposite)
            .setValue(WATERLOGGED, fluidState.type === Fluids.WATER)
    }

    override fun updateShape(
        state: BlockState,
        level: LevelReader,
        scheduledTickAccess: ScheduledTickAccess,
        currentPos: BlockPos,
        direction: Direction,
        facingPos: BlockPos,
        facingState: BlockState,
        random: RandomSource,
    ): BlockState {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level))
        }
        return super.updateShape(
            state,
            level,
            scheduledTickAccess,
            currentPos,
            direction,
            facingPos,
            facingState,
            random
        )
    }
}
