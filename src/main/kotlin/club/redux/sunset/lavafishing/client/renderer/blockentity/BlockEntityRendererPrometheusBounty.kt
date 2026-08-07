package club.redux.sunset.lavafishing.client.renderer.blockentity

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.block.blockentity.BlockEntityPrometheusBounty
import club.redux.sunset.lavafishing.registry.ModBlockEntityTypes
import net.minecraft.client.renderer.Sheets
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.blockentity.ChestRenderer
import net.minecraft.client.renderer.blockentity.state.ChestRenderState
import net.minecraft.client.resources.model.sprite.SpriteId
import net.neoforged.neoforge.client.event.EntityRenderersEvent

class BlockEntityRendererPrometheusBounty(
    context: BlockEntityRendererProvider.Context,
) : ChestRenderer<BlockEntityPrometheusBounty>(context) {
    override fun getCustomSprite(blockEntity: BlockEntityPrometheusBounty, state: ChestRenderState) =
        SpriteId(Sheets.CHEST_SHEET, LavaFishing.identifier("entity/chest/prometheus_bounty"))

    companion object {
        fun onRegisterRenderers(event: EntityRenderersEvent.RegisterRenderers) {
            event.registerBlockEntityRenderer(
                ModBlockEntityTypes.PROMETHEUS_BOUNTY.get(),
                ::BlockEntityRendererPrometheusBounty
            )
        }
    }
}
