package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.BuildConstants
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
import net.minecraft.resources.ResourceLocation


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
        red: Float,
        green: Float,
        blue: Float,
        alpha: Float,
    ) {
        this.whole.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
    }

    companion object {
        @JvmField val LAYER_LOCATION: ModelLayerLocation =
            ModelLayerLocation(ResourceLocation(BuildConstants.MOD_ID, "bullet"), "main")

        fun createBodyLayer(): LayerDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.root

            val whole = partdefinition.addOrReplaceChild(
                "whole",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-1.0f, -1.0f, -1.0f, 2.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 23.0f, 0.0f)
            )

            return LayerDefinition.create(meshdefinition, 16, 16)
        }
    }
}