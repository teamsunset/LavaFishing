package club.redux.sunset.lavafishing.block

import club.redux.sunset.lavafishing.block.blockentity.BlockEntityPrometheusBounty
import club.redux.sunset.lavafishing.registry.RegistryBlockEntityType
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.ChestType
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.material.MapColor
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.function.Supplier

class BlockPrometheusBounty : ChestBlock(
    Properties.of().mapColor(MapColor.DIAMOND).strength(2.5f).sound(SoundType.METAL),
    Supplier<BlockEntityType<out ChestBlockEntity>> { RegistryBlockEntityType.PROMETHEUS_BOUNTY.get() }
) {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return BlockEntityPrometheusBounty(pos, state)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val fluidState = context.level.getFluidState(context.clickedPos)
        return defaultBlockState()
            .setValue(TYPE, ChestType.SINGLE)
            .setValue(FACING, context.horizontalDirection.opposite)
            .setValue(WATERLOGGED, fluidState.type === Fluids.WATER)
    }

    @Deprecated("Deprecated in Java")
    override fun updateShape(
        state: BlockState,
        direction: Direction,
        facingState: BlockState,
        world: LevelAccessor,
        currentPos: BlockPos,
        facingPos: BlockPos,
    ): BlockState {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world))
        }
        return state
    }

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(
        stack: ItemStack,
        pLevel: BlockGetter?,
        tooltip: MutableList<Component>,
        flag: TooltipFlag,
    ) {
        super.appendHoverText(stack, pLevel, tooltip, flag)
        val tag = stack.getTagElement("BlockEntityTag")
        if (tag != null) {
            if (tag.contains("Items", 9)) {
                tooltip.add(Component.literal("???????").withStyle(ChatFormatting.ITALIC))
            }
        }
    }
}
