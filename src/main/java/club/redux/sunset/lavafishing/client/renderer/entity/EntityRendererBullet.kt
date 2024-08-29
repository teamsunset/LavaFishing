package club.redux.sunset.lavafishing.client.renderer.entity

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.client.model.ModelBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.util.getTexture
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.neoforged.neoforge.client.event.EntityRenderersEvent

class EntityRendererBullet<T : EntityBullet>(
    context: EntityRendererProvider.Context,
) : EntityRenderer<T>(context) {

    private var model: ModelBullet = ModelBullet(context.bakeLayer(ModelBullet.LAYER_LOCATION))

    override fun getTextureLocation(pEntity: T): ResourceLocation {
        return pEntity.getTexture(LavaFishing.resourceLocation("textures/entity/bullet/default_bullet.png"))
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
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.yRot) - 90.0f))
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.xRot)))
        pPoseStack.translate(0.0, -1.4, 0.0)
        val vertexConsumer = pBuffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(pEntity)))
        model.renderToBuffer(
            pPoseStack,
            vertexConsumer,
            pPackedLight,
            OverlayTexture.NO_OVERLAY
        )
        pPoseStack.popPose()
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight)
    }

    companion object {
        @JvmStatic
        fun onRegisterRenderers(event: EntityRenderersEvent.RegisterRenderers) {
            ModEntityTypes.getEntriesByEntityParentClass(EntityBullet::class.java).map { it.get() }
                .forEach { entityType ->
                    event.registerEntityRenderer(entityType) { EntityRendererBullet(it) }
                }
        }
    }
}