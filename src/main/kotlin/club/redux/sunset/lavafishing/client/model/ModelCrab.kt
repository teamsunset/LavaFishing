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


class ModelCrab<T : Entity>(val root: ModelPart) : HierarchicalModel<T>() {
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
            animateWalk(
                ANIMATIONS["walk"] ?: throw RuntimeException("cannot find animation"),
                pLimbSwing,
                pLimbSwingAmount,
                10F,
                Float.MAX_VALUE
            )
        }
    }

    companion object {
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(LavaFishing.resourceLocation("crab"), "main")

        val ANIMATIONS = BedrockLoader.loadAnimations(
            LavaFishing.javaClass.getResourceAsStream("/assets/lavafishing/animation/crab.animation.json")
                ?: throw RuntimeException("cannot find animation file")
        )

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) {
                BedrockLoader.loadGeometry(
                    LavaFishing.javaClass.getResourceAsStream("/assets/lavafishing/geo/crab.geo.json")
                        ?: throw RuntimeException("cannot find geo file")
                )
            }
        }
    }
}