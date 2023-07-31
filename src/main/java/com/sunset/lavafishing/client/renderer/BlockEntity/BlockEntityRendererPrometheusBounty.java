package com.sunset.lavafishing.client.renderer.BlockEntity;

import com.sunset.lavafishing.block.BlockEntity.BlockEntityPrometheusBounty;
import com.sunset.lavafishing.util.Reference;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.ChestType;

import javax.annotation.Nonnull;

public class BlockEntityRendererPrometheusBounty extends ChestRenderer<BlockEntityPrometheusBounty>
{
    public static BlockEntityRendererPrometheusBounty instance;

    public BlockEntityRendererPrometheusBounty(BlockEntityRendererProvider.Context context) {
        super(context);
        instance = this;
    }

    @Override
    @Nonnull
    protected Material getMaterial(@Nonnull BlockEntityPrometheusBounty blockEntity, @Nonnull ChestType chestType) {
        return new Material(Sheets.CHEST_SHEET, new ResourceLocation(Reference.MOD_ID, "entity/chest/prometheus_bounty"));
    }
}
