package club.redux.sunset.lavafishing.client.renderer

import club.redux.sunset.lavafishing.block.BlockPrometheusBounty
import club.redux.sunset.lavafishing.block.blockentity.BlockEntityPrometheusBounty
import club.redux.sunset.lavafishing.util.RegistryCollection.BlockCollection
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.model.geom.EntityModelSet
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher
import net.minecraft.core.BlockPos
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack

class BlockItemCustomRenderer(
    pBlockEntityRenderDispatcher: BlockEntityRenderDispatcher,
    pEntityModelSet: EntityModelSet,
) : BlockEntityWithoutLevelRenderer(pBlockEntityRenderDispatcher, pEntityModelSet) {
    override fun renderByItem(
        stack: ItemStack,
        itemDisplayContext: ItemDisplayContext,
        matrixStack: PoseStack,
        buffer: MultiBufferSource,
        i: Int,
        i1: Int,
    ) {
        val mc = Minecraft.getInstance()
        val item = stack.item
        if ((item as? BlockItem)?.block !is BlockPrometheusBounty) {
            return
        }

        mc.blockEntityRenderDispatcher.renderItem(
            BlockEntityPrometheusBounty(
                BlockPos.ZERO,
                BlockCollection.BLOCK_PROMETHEUS_BOUNTY.get().defaultBlockState()
            ), matrixStack, buffer, i, i1
        )
    }
}