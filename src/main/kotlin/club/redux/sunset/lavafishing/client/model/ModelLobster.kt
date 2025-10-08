package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.tool.bedrock.BedrockLoader
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.HierarchicalModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.neoforged.neoforge.client.event.EntityRenderersEvent


class ModelLobster<T : Entity>(val root: ModelPart) : HierarchicalModel<T>() {
    private val whole: ModelPart = root.getChild("whole")

    override fun renderToBuffer(
        poseStack: PoseStack,
        vertexConsumer: VertexConsumer,
        packedLight: Int,
        packedOverlay: Int,
        color: Int,
    ) {
        this.whole.render(poseStack, vertexConsumer, packedLight, packedOverlay, color)
    }

    override fun root(): ModelPart = this.root
    override fun setupAnim(
        pEntity: T,
        pLimbSwing: Float,
        pLimbSwingAmount: Float,
        pAgeInTicks: Float,
        pNetHeadYaw: Float,
        pHeadPitch: Float,
    ) {
        if (pEntity is LivingEntity) {
            this.root().allParts.forEach(ModelPart::resetPose)
            if (pEntity.onGround()) {
                animateWalk(
                    ANIMATIONS["walk"],
                    pLimbSwing,
                    pLimbSwingAmount,
                    10F,
                    Float.MAX_VALUE
                )
            } else if (pEntity.isInLava || pEntity.isInWater) {
                animateWalk(
                    ANIMATIONS["swim"],
                    pLimbSwing,
                    pLimbSwingAmount,
                    10F,
                    Float.MAX_VALUE
                )
            }
        }
    }

    companion object {
        @JvmField val LAYER_LOCATION: ModelLayerLocation =
            ModelLayerLocation(LavaFishing.resourceLocation("lobster"), "main")

        val ANIMATIONS = BedrockLoader.loadAnimations("/assets/lavafishing/animation/lobster.animation.json")

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) {
                BedrockLoader.loadGeometry("/assets/lavafishing/geo/lobster.geo.json")
            }
        }
    }
}