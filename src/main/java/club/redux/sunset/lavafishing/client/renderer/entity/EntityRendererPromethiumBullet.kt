package club.redux.sunset.lavafishing.client.renderer.entity

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.client.model.ModelPromethiumBullet
import club.redux.sunset.lavafishing.entity.EntityPromethiumBullet
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers

class EntityRendererPromethiumBullet(
    context: EntityRendererProvider.Context,
) : EntityRenderer<EntityPromethiumBullet>(context) {

    private var model: ModelPromethiumBullet =
        ModelPromethiumBullet(context.bakeLayer(ModelPromethiumBullet.LAYER_LOCATION))

    private val textureLocation: ResourceLocation =
        ResourceLocation(BuildConstants.MOD_ID, "textures/entity/promethium_bullet.png")

    override fun getTextureLocation(pEntity: EntityPromethiumBullet): ResourceLocation {
        return this.textureLocation
    }

    override fun render(
        pEntity: EntityPromethiumBullet,
        pEntityYaw: Float,
        pPartialTicks: Float,
        pPoseStack: PoseStack,
        pBuffer: MultiBufferSource,
        pPackedLight: Int,
    ) {
        pPoseStack.pushPose()
        pPoseStack.translate(0.0, -1.5, 0.0)
        val vertexConsumer = pBuffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(pEntity)))
        model.renderToBuffer(
            pPoseStack,
            vertexConsumer,
            pPackedLight,
            OverlayTexture.NO_OVERLAY,
            1.0f,
            1.0f,
            1.0f,
            1.0f
        )
        pPoseStack.popPose()
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight)
    }

    companion object {
        @JvmStatic
        fun onRegisterRenderers(event: RegisterRenderers) {
            event.registerEntityRenderer(ModEntityTypes.PROMETHIUM_BULLET.get()) { EntityRendererPromethiumBullet(it) }
        }
    }
}