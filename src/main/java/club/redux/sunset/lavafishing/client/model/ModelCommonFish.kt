package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.LavaFishing
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
            event.registerLayerDefinition(LAYER_LOCATION) { createBodyLayer() }
        }

        fun createBodyLayer(): LayerDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.root

            val whole = partdefinition.addOrReplaceChild(
                "whole",
                CubeListBuilder.create(),
                PartPose.offset(-0.75f, 22.75f, 0.25f)
            )

            val right_fin = whole.addOrReplaceChild(
                "right_fin",
                CubeListBuilder.create().texOffs(0, 2)
                    .addBox(0.0f, -0.75f, -1.75f, 0.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(0.0f, -0.75f, 0.25f, 0.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2618f)
            )

            val left_fin = whole.addOrReplaceChild(
                "left_fin",
                CubeListBuilder.create().texOffs(0, 7)
                    .addBox(0.0f, -0.75f, -1.75f, 0.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(2, 0).addBox(0.0f, -0.75f, 0.25f, 0.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(1.5f, 0.0f, 0.0f, 0.0f, 0.0f, -0.2618f)
            )

            val tail = whole.addOrReplaceChild(
                "tail",
                CubeListBuilder.create().texOffs(4, 7)
                    .addBox(0.0f, -1.0f, -0.0333f, 0.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(8, 2).addBox(0.0f, -2.0f, 1.9667f, 0.0f, 3.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(4, 0).addBox(0.0f, -2.0f, 2.9667f, 0.0f, 2.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.75f, -0.75f, 3.2833f)
            )

            val body = whole.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-1.5f, -2.0f, -2.5f, 1.0f, 3.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(8, 0).addBox(-1.5f, -1.0f, -3.5f, 1.0f, 2.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(1.75f, -0.75f, -0.25f)
            )

            val anal_fin = whole.addOrReplaceChild(
                "anal_fin",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(0.0f, -0.5f, -1.0f, 0.0f, 1.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.75f, 0.25f, 1.75f)
            )

            val dorsal_fin = whole.addOrReplaceChild(
                "dorsal_fin",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-0.5f, 0.5f, -1.0f, 0.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offset(1.25f, -4.25f, 0.25f)
            )

            return LayerDefinition.create(meshdefinition, 16, 16)
        }
    }
}