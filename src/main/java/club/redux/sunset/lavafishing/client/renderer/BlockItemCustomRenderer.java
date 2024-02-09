package club.redux.sunset.lavafishing.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import club.redux.sunset.lavafishing.block.BlockEntity.BlockEntityPrometheusBounty;
import club.redux.sunset.lavafishing.block.BlockPrometheusBounty;
import club.redux.sunset.lavafishing.util.RegistryCollection.BlockCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;

public class BlockItemCustomRenderer extends BlockEntityWithoutLevelRenderer
{

    public BlockItemCustomRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(@Nonnull ItemStack stack, ItemDisplayContext itemDisplayContext, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int i, int i1) {
        Minecraft mc = Minecraft.getInstance();
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof BlockPrometheusBounty) {
                mc.getBlockEntityRenderDispatcher().renderItem(new BlockEntityPrometheusBounty(BlockPos.ZERO, BlockCollection.BLOCK_PROMETHEUS_BOUNTY.get().defaultBlockState()), matrixStack, buffer, i, i1);
            }
        }
    }
}

