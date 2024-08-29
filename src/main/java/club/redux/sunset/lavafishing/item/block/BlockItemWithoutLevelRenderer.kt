package club.redux.sunset.lavafishing.item.block

import club.redux.sunset.lavafishing.registry.ModItems
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent

class BlockItemWithoutLevelRenderer(
    pBlock: Block,
    pProperties: Properties,
    val blockEntityProvider: () -> BlockEntity,
) : BlockItem(pBlock, pProperties) {
    companion object {
        fun onRegisterClientExtensions(event: RegisterClientExtensionsEvent) {
            event.registerItem(
                object : IClientItemExtensions {
                    override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
                        return object : BlockEntityWithoutLevelRenderer(
                            Minecraft.getInstance().blockEntityRenderDispatcher,
                            Minecraft.getInstance().entityModels
                        ) {
                            override fun renderByItem(
                                stack: ItemStack,
                                itemDisplayContext: ItemDisplayContext,
                                matrixStack: PoseStack,
                                buffer: MultiBufferSource,
                                i: Int,
                                i1: Int,
                            ) {
                                Minecraft.getInstance().blockEntityRenderDispatcher.renderItem(
                                    ModItems.PROMETHEUS_BOUNTY.get().blockEntityProvider(), matrixStack, buffer, i, i1
                                )
                            }
                        }
                    }
                }, ModItems.PROMETHEUS_BOUNTY.get()
            )
        }
    }
}