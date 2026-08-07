package club.redux.sunset.lavafishing.client.renderer.entity

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.client.model.*
import club.redux.sunset.lavafishing.client.renderer.entity.state.RenderStateLavaFish
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.misc.LavaFishType
import club.redux.sunset.lavafishing.util.UtilEntity.getTexture
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.client.renderer.state.level.CameraRenderState
import net.minecraft.resources.Identifier
import net.minecraft.util.Mth

class EntityRendererLavaFish(
    context: EntityRendererProvider.Context,
) : MobRenderer<EntityLavaFish, RenderStateLavaFish, EntityModel<RenderStateLavaFish>>(
    context,
    ModelCommonFish(context.bakeLayer(ModelCommonFish.LAYER_LOCATION)),
    0.35f,
) {
    private val commonModel = ModelCommonFish(context.bakeLayer(ModelCommonFish.LAYER_LOCATION))
    private val swordFishModel = ModelSwordFish(context.bakeLayer(ModelSwordFish.LAYER_LOCATION))
    private val eelModel = ModelEel(context.bakeLayer(ModelEel.LAYER_LOCATION))
    private val crabModel = ModelCrab(context.bakeLayer(ModelCrab.LAYER_LOCATION))
    private val snailModel = ModelSnail(context.bakeLayer(ModelSnail.LAYER_LOCATION))
    private val lobsterModel = ModelLobster(context.bakeLayer(ModelLobster.LAYER_LOCATION))

    override fun createRenderState() = RenderStateLavaFish()

    override fun extractRenderState(entity: EntityLavaFish, state: RenderStateLavaFish, partialTicks: Float) {
        super.extractRenderState(entity, state, partialTicks)
        state.fishType = entity.fishType
        state.isInLava = entity.isInLava
        state.onGround = entity.onGround()
        state.texture = entity.getTexture(DEFAULT_TEXTURE)
    }

    override fun getTextureLocation(state: RenderStateLavaFish): Identifier = state.texture

    override fun submit(
        state: RenderStateLavaFish,
        poseStack: PoseStack,
        submitNodeCollector: SubmitNodeCollector,
        camera: CameraRenderState,
    ) {
        this.model = when (state.fishType) {
            LavaFishType.SWORDFISH -> this.swordFishModel
            LavaFishType.EEL -> this.eelModel
            LavaFishType.CRAB -> this.crabModel
            LavaFishType.SNAIL -> this.snailModel
            LavaFishType.LOBSTER -> this.lobsterModel
            LavaFishType.COMMON -> this.commonModel
        }
        super.submit(state, poseStack, submitNodeCollector, camera)
    }

    override fun setupRotations(
        state: RenderStateLavaFish,
        poseStack: PoseStack,
        bodyRot: Float,
        entityScale: Float,
    ) {
        super.setupRotations(state, poseStack, bodyRot, entityScale)
        when (state.fishType) {
            LavaFishType.COMMON, LavaFishType.SWORDFISH -> {
                val fishRotation = (4.3 * Mth.sin(0.6 * state.ageInTicks)).toFloat()
                poseStack.mulPose(Axis.YP.rotationDegrees(fishRotation))
                if (!state.isInLava) {
                    poseStack.translate(0.1f, 0.1f, -0.1f)
                    poseStack.mulPose(Axis.ZP.rotationDegrees(90f))
                }
            }

            LavaFishType.CRAB -> poseStack.mulPose(Axis.YP.rotationDegrees(90f))
            else -> Unit
        }
    }

    override fun scale(state: RenderStateLavaFish, poseStack: PoseStack) {
        val scale = when (state.fishType) {
            LavaFishType.CRAB, LavaFishType.EEL, LavaFishType.SNAIL -> 0.5f
            LavaFishType.LOBSTER -> 0.3f
            else -> 1.0f
        }
        poseStack.scale(scale, scale, scale)
    }

    companion object {
        private val DEFAULT_TEXTURE = LavaFishing.identifier("textures/entity/fish/default.png")
    }
}
