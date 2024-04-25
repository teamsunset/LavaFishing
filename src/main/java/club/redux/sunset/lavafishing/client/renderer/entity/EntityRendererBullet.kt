package club.redux.sunset.lavafishing.client.renderer.entity

import club.redux.sunset.lavafishing.client.model.ModelBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers

class EntityRendererBullet<T : EntityBullet>(
    context: EntityRendererProvider.Context,
) : EntityRenderer<T>(context) {

    private var model: ModelBullet = ModelBullet(context.bakeLayer(ModelBullet.LAYER_LOCATION))

    override fun getTextureLocation(pEntity: T): ResourceLocation {
        return pEntity.textureLocation
    }

    override fun render(
        pEntity: T,
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
            event.registerEntityRenderer(ModEntityTypes.BULLET.get()) { EntityRendererBullet(it) }
        }
    }
}