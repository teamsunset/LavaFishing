package club.redux.sunset.lavafishing.client.renderer.entity

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.client.model.ModelBullet
import club.redux.sunset.lavafishing.client.renderer.entity.state.RenderStateBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.util.UtilEntity.getTexture
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.state.level.CameraRenderState
import net.minecraft.client.renderer.texture.OverlayTexture
import net.neoforged.neoforge.client.event.EntityRenderersEvent

class EntityRendererBullet<T : EntityBullet>(
    context: EntityRendererProvider.Context,
) : EntityRenderer<T, RenderStateBullet>(context) {

    private val model = ModelBullet(context.bakeLayer(ModelBullet.LAYER_LOCATION))

    override fun createRenderState() = RenderStateBullet()

    override fun extractRenderState(entity: T, state: RenderStateBullet, partialTicks: Float) {
        super.extractRenderState(entity, state, partialTicks)
        state.yRot = entity.getYRot(partialTicks)
        state.xRot = entity.getXRot(partialTicks)
        state.texture = entity.getTexture(DEFAULT_TEXTURE)
    }

    override fun submit(
        state: RenderStateBullet,
        poseStack: PoseStack,
        submitNodeCollector: SubmitNodeCollector,
        camera: CameraRenderState,
    ) {
        poseStack.pushPose()
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90.0f))
        poseStack.mulPose(Axis.ZP.rotationDegrees(state.xRot))
        poseStack.translate(0.0, -1.4, 0.0)
        submitNodeCollector.submitModel(
            model,
            state,
            poseStack,
            state.texture,
            state.lightCoords,
            OverlayTexture.NO_OVERLAY,
            state.outlineColor,
            null,
        )
        poseStack.popPose()
        super.submit(state, poseStack, submitNodeCollector, camera)
    }

    companion object {
        private val DEFAULT_TEXTURE = LavaFishing.identifier("textures/entity/bullet/default_bullet.png")

        @JvmStatic
        fun onRegisterRenderers(event: EntityRenderersEvent.RegisterRenderers) {
            ModEntityTypes.getHoldersByEntityClass<EntityBullet>()
                .forEach { entityType ->
                    event.registerEntityRenderer(entityType.get()) { EntityRendererBullet(it) }
                }
        }
    }
}
