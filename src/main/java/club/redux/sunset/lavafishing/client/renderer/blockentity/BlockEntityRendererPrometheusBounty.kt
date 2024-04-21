package club.redux.sunset.lavafishing.client.renderer.blockentity

import club.asynclab.web.BuildConstants
import club.redux.sunset.lavafishing.block.blockentity.BlockEntityPrometheusBounty
import club.redux.sunset.lavafishing.registry.RegistryBlockEntityType
import net.minecraft.client.renderer.Sheets
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.blockentity.ChestRenderer
import net.minecraft.client.resources.model.Material
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.properties.ChestType
import net.minecraftforge.client.event.EntityRenderersEvent

class BlockEntityRendererPrometheusBounty(
    context: BlockEntityRendererProvider.Context,
) : ChestRenderer<BlockEntityPrometheusBounty>(context) {
    override fun getMaterial(blockEntity: BlockEntityPrometheusBounty, chestType: ChestType): Material {
        return Material(Sheets.CHEST_SHEET, ResourceLocation(BuildConstants.MOD_ID, "entity/chest/prometheus_bounty"))
    }

    companion object {
        @JvmStatic
        fun onRegisterRenderers(event: EntityRenderersEvent.RegisterRenderers) {
            event.registerBlockEntityRenderer(RegistryBlockEntityType.PROMETHEUS_BOUNTY.get()) {
                BlockEntityRendererPrometheusBounty(
                    it
                )
            }
        }
    }
}