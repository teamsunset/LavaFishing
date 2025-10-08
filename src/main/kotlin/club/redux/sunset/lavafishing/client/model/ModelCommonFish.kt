package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.tool.bedrock.BedrockLoader
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.neoforged.neoforge.client.event.EntityRenderersEvent


class ModelCommonFish<T : Entity>(root: ModelPart) : EntityModel<T>() {
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

    override fun setupAnim(
        pEntity: T,
        pLimbSwing: Float,
        pLimbSwingAmount: Float,
        pAgeInTicks: Float,
        pNetHeadYaw: Float,
        pHeadPitch: Float,
    ) {
        var movement = 1.0f
        if (!pEntity.isInLava) {
            movement = 1.5f
        }

        this.whole.getChild("tail").yRot = -movement * 0.45f * Mth.sin(0.6f * pAgeInTicks)
    }

    companion object {
        @JvmField val LAYER_LOCATION: ModelLayerLocation =
            ModelLayerLocation(LavaFishing.resourceLocation("common_fish"), "main")

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) {
                BedrockLoader.loadGeometry(
                    LavaFishing.javaClass.getResourceAsStream("/assets/lavafishing/geo/common_fish.geo.json")
                        ?: throw RuntimeException("cannot find geo file")
                )
            }
        }
    }
}