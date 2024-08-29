package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.misc.ModResourceLocation
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.*
import net.minecraft.world.entity.Entity
import net.minecraftforge.client.event.EntityRenderersEvent


class ModelFishTest<T : Entity>(val root: ModelPart) : EntityModel<T>() {
    private val body: ModelPart = root.getChild("body")
    private val tail: ModelPart = root.getChild("tail")
    private val finTop: ModelPart = root.getChild("finTop")
    private val finSide: ModelPart = root.getChild("finSide")

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
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
        tail.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
        finTop.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
        finSide.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
    }

    override fun setupAnim(
        pEntity: T,
        pLimbSwing: Float,
        pLimbSwingAmount: Float,
        pAgeInTicks: Float,
        pNetHeadYaw: Float,
        pHeadPitch: Float,
    ) {

    }

    companion object {

        @JvmField val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(ModResourceLocation("fish"), "main")
        fun createBodyLayer(): LayerDefinition {
            val meshDefinition = MeshDefinition()
            val partDefinition = meshDefinition.root

            val body: PartDefinition = partDefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(0, 2)
                    .addBox(-3.5f, -2.0f, -1.0f, 7.0f, 4.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 19.0f, 0.0f, 0.0f, 1.5708f, 0.0f)
            )

            val tail: PartDefinition = partDefinition.addOrReplaceChild(
                "tail",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0f, 24.0f, 0.0f, 0.0f, 1.5708f, 0.0f)
            )

            val bone7 = tail.addOrReplaceChild(
                "bone7",
                CubeListBuilder.create().texOffs(0, 1)
                    .addBox(-0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(-2.5f, -4.0f, 0.0f)
            )

            val bone6 = tail.addOrReplaceChild(
                "bone6",
                CubeListBuilder.create().texOffs(0, 1)
                    .addBox(-4.0f, -3.0f, -0.5f, 4.0f, 3.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(-3.0f, -3.5f, 0.0f)
            )

            val finTop: PartDefinition = partDefinition.addOrReplaceChild(
                "finTop",
                CubeListBuilder.create().texOffs(0, 1)
                    .addBox(-2.0f, -1.0f, -0.5f, 4.0f, 2.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 16.5f, 0.0f, 0.0f, 1.5708f, 0.0f)
            )

            val finSide: PartDefinition = partDefinition.addOrReplaceChild(
                "finSide",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0f, 24.0f, 0.0f, 0.0f, 1.5708f, 0.0f)
            )

            val bone8 = finSide.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(0.5f, -3.0f, 1.5f))

            val cube_r1 = bone8.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create().texOffs(0, 1)
                    .addBox(-1.5f, -1.0f, -0.5f, 3.0f, 2.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.3927f, 0.0f)
            )

            val bone9 =
                finSide.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(0.5f, -3.0f, -1.5f))

            val cube_r2 = bone9.addOrReplaceChild(
                "cube_r2",
                CubeListBuilder.create().texOffs(0, 1)
                    .addBox(-1.5f, -1.0f, -0.5f, 3.0f, 2.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, -0.3927f, 0.0f)
            )
            return LayerDefinition.create(meshDefinition, 16, 16)
        }

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) { createBodyLayer() }
        }
    }
}