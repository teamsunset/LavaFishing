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


class ModelSwordFish<T : Entity>(root: ModelPart) : EntityModel<T>() {
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
            ModelLayerLocation(LavaFishing.resourceLocation("sword_fish"), "main")

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) { createBodyLayer() }
        }

        fun createBodyLayer(): LayerDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.root

            val whole = partdefinition.addOrReplaceChild(
                "whole",
                CubeListBuilder.create(),
                PartPose.offset(-0.75f, 20.75f, -0.75f)
            )

            val left_fin =
                whole.addOrReplaceChild("left_fin", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f))

            val cube_r1 = left_fin.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create().texOffs(6, 11)
                    .addBox(-0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, CubeDeformation(0.0f))
                    .texOffs(6, 6).addBox(-3.5f, -0.5f, 0.0f, 3.0f, 2.0f, 0.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(2.0f, 0.0f, 0.0f, -0.4363f, 0.0f, 0.0f)
            )

            val tail = whole.addOrReplaceChild(
                "tail",
                CubeListBuilder.create().texOffs(10, 8)
                    .addBox(0.0f, -1.0f, 0.0f, 2.0f, 2.0f, 0.0f, CubeDeformation(0.0f)),
                PartPose.offset(2.75f, -0.75f, 0.75f)
            )

            val bone4 = tail.addOrReplaceChild(
                "bone4",
                CubeListBuilder.create().texOffs(4, 11)
                    .addBox(-3.5f, 1.5f, 0.0f, 1.0f, 1.0f, 0.0f, CubeDeformation(0.0f))
                    .texOffs(0, 6).addBox(-2.5f, 0.5f, 0.0f, 3.0f, 2.0f, 0.0f, CubeDeformation(0.0f))
                    .texOffs(10, 0).addBox(0.5f, -0.5f, 0.0f, 1.0f, 4.0f, 0.0f, CubeDeformation(0.0f)),
                PartPose.offset(5.5f, -2.5f, 0.0f)
            )

            val right_fin =
                whole.addOrReplaceChild("right_fin", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 1.5f))

            val cube_r2 = right_fin.addOrReplaceChild(
                "cube_r2",
                CubeListBuilder.create().texOffs(11, 4)
                    .addBox(-1.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, CubeDeformation(0.0f))
                    .texOffs(0, 8).addBox(-4.5f, -0.5f, 0.0f, 3.0f, 2.0f, 0.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(3.0f, 0.0f, 0.0f, 0.4363f, 0.0f, 0.0f)
            )

            val body = whole.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(0, 4)
                    .addBox(-8.625f, 0.1309f, -0.6202f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(6, 8).addBox(1.375f, -0.8691f, -0.6202f, 1.0f, 2.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(3, 10).addBox(1.375f, -1.8691f, -0.1202f, 1.0f, 1.0f, 0.0f, CubeDeformation(0.0f))
                    .texOffs(10, 10).addBox(-0.625f, -2.8691f, -0.1202f, 2.0f, 1.0f, 0.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-2.625f, -1.8691f, -0.6202f, 4.0f, 3.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 10).addBox(-3.625f, -0.8691f, -0.6202f, 1.0f, 2.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.375f, -0.8809f, 0.8702f)
            )

            return LayerDefinition.create(meshdefinition, 16, 16)
        }

    }
}