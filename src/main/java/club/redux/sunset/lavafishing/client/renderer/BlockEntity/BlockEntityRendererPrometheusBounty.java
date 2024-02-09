package club.redux.sunset.lavafishing.client.renderer.BlockEntity;

import club.redux.sunset.lavafishing.block.BlockEntity.BlockEntityPrometheusBounty;
import club.redux.sunset.lavafishing.util.Reference;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.ChestType;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockEntityRendererPrometheusBounty extends ChestRenderer<BlockEntityPrometheusBounty> {
    public static BlockEntityRendererPrometheusBounty instance;

    public BlockEntityRendererPrometheusBounty(BlockEntityRendererProvider.Context context) {
        super(context);
        instance = this;
    }

    @Override
    protected Material getMaterial(BlockEntityPrometheusBounty blockEntity, ChestType chestType) {
        return new Material(Sheets.CHEST_SHEET, new ResourceLocation(Reference.MOD_ID, "entity/chest/prometheus_bounty"));
    }
}
