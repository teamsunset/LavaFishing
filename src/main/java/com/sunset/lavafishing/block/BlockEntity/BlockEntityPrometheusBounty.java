package com.sunset.lavafishing.block.BlockEntity;

import com.sunset.lavafishing.util.RegistryCollection.BlockEntityTypeCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class BlockEntityPrometheusBounty extends ChestBlockEntity
{
    public BlockEntityPrometheusBounty(BlockPos pos, BlockState state) {
        super(BlockEntityTypeCollection.BLOCK_ENTITY_PROMETHEUS_BOUNTY.get(), pos, state);
    }

    @Override
    @Nonnull
    public Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }
}
