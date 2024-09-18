package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.neoforged.neoforge.client.event.EntityRenderersEvent


class ModelBullet(
    root: ModelPart,
) : EntityModel<EntityPromethiumBullet>() {
    private var whole: ModelPart = root.getChild("whole")

    override fun setupAnim(
        entity: EntityPromethiumBullet,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float,
    ) {
    }

    override fun renderToBuffer(
        poseStack: PoseStack,
        vertexConsumer: VertexConsumer,
        packedLight: Int,
        packedOverlay: Int,
        color: Int,
    ) {
        this.whole.render(poseStack, vertexConsumer, packedLight, packedOverlay, color)
    }

    companion object {
        @JvmField val LAYER_LOCATION: ModelLayerLocation =
            ModelLayerLocation(LavaFishing.resourceLocation("bullet"), "main")

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) { createBodyLayer() }
        }

        fun createBodyLayer(): LayerDefinition {
            val meshDefinition = MeshDefinition()
            val partDefinition = meshDefinition.root

            val whole = partDefinition.addOrReplaceChild(
                "whole",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-1.0f, -1.0f, -1.0f, 2.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 23.0f, 0.0f)
            )

            return LayerDefinition.create(meshDefinition, 16, 16)
        }
    }
}