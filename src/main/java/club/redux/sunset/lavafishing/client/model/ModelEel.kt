package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.client.animation.AnimatedAnimationEel
import club.redux.sunset.lavafishing.misc.ModResourceLocation
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.HierarchicalModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.client.event.EntityRenderersEvent


class ModelEel<T : Entity>(val root: ModelPart) : HierarchicalModel<T>() {
    private val whole: ModelPart = root.getChild("whole")

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
                AnimatedAnimationEel.WALK,
                pLimbSwing,
                pLimbSwingAmount,
                10F,
                Float.MAX_VALUE
            )
        }
    }

    companion object {
        @JvmField val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(ModResourceLocation("eel"), "main")
        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) { createBodyLayer() }
        }

        fun createBodyLayer(): LayerDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.root

            val whole =
                partdefinition.addOrReplaceChild("whole", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, 0.0f))

            val mouth = whole.addOrReplaceChild(
                "mouth",
                CubeListBuilder.create().texOffs(0, 30)
                    .addBox(-4.0f, -4.0f, -1.0f, 2.0f, 8.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(14, 30).addBox(2.0f, -2.0f, -1.0f, 2.0f, 4.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(36, 19).addBox(-2.0f, -4.0f, -1.0f, 6.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(20, 19).addBox(-2.0f, 2.0f, -1.0f, 6.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(42, 23).addBox(-3.0f, -4.0f, 1.0f, 6.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(40, 44).addBox(3.0f, -3.0f, 1.0f, 1.0f, 6.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(12, 44).addBox(-4.0f, -3.0f, 1.0f, 1.0f, 6.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(32, 0).addBox(-3.0f, 3.0f, 1.0f, 6.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(38, 11).addBox(-3.0f, -5.0f, -1.0f, 6.0f, 1.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(32, 41).addBox(-3.0f, 4.0f, -1.0f, 6.0f, 1.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(6, 44).addBox(4.0f, -3.0f, -1.0f, 1.0f, 6.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(16, 48).addBox(3.0f, -3.0f, -2.0f, 1.0f, 6.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 42).addBox(-3.0f, -4.0f, -2.0f, 6.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(32, 2).addBox(-3.0f, 3.0f, -2.0f, 6.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(20, 2).addBox(-3.0f, 1.0f, -2.0f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(6, 17).addBox(1.0f, -3.0f, -2.0f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 17).addBox(2.0f, -2.0f, -2.0f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(6, 15).addBox(-2.0f, -3.0f, -2.0f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 15).addBox(-3.0f, -2.0f, -2.0f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(20, 0).addBox(-2.0f, 2.0f, -2.0f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(6, 2).addBox(-2.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(6, 0).addBox(-2.0f, -2.0f, 0.0f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 2).addBox(1.0f, -2.0f, 0.0f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(4, 8).addBox(2.0f, 1.0f, -2.0f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 8).addBox(1.0f, 2.0f, -2.0f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(44, 44).addBox(-4.0f, -3.0f, -2.0f, 1.0f, 6.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 44).addBox(-5.0f, -3.0f, -1.0f, 1.0f, 6.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, -5.0f, -29.0f)
            )

            val head = whole.addOrReplaceChild(
                "head",
                CubeListBuilder.create().texOffs(23, 23)
                    .addBox(-3.0f, -3.0f, 0.0f, 6.0f, 6.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(0, 15).addBox(-4.0f, -2.0f, 1.0f, 1.0f, 4.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(32, 36).addBox(-2.0f, -4.0f, 1.0f, 4.0f, 1.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(20, 0).addBox(-2.0f, 3.0f, 1.0f, 4.0f, 1.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(3.0f, -2.0f, 1.0f, 1.0f, 4.0f, 4.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, -5.0f, -28.0f)
            )

            val body = whole.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0f, -5.0f, -21.0f))

            val bone9 = body.addOrReplaceChild(
                "bone9",
                CubeListBuilder.create().texOffs(0, 15)
                    .addBox(-2.5f, -2.5f, 0.0f, 5.0f, 5.0f, 10.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            val B3 = body.addOrReplaceChild("B3", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 10.0f))

            val B4 = B3.addOrReplaceChild(
                "B4",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-2.5f, -2.5f, 0.0f, 5.0f, 5.0f, 10.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            val M = B3.addOrReplaceChild("M", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 10.0f))

            val bone11 = M.addOrReplaceChild(
                "bone11",
                CubeListBuilder.create().texOffs(20, 5)
                    .addBox(-2.5f, -1.5f, 0.0f, 4.0f, 4.0f, 10.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.5f, -0.5f, 0.0f)
            )

            val B6 = M.addOrReplaceChild("B6", CubeListBuilder.create(), PartPose.offset(0.5f, -0.5f, 10.0f))

            val bone12 = B6.addOrReplaceChild(
                "bone12",
                CubeListBuilder.create().texOffs(16, 36)
                    .addBox(-2.5f, -1.5f, 0.0f, 4.0f, 4.0f, 8.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            val T = B6.addOrReplaceChild("T", CubeListBuilder.create(), PartPose.offset(-0.5f, 0.5f, 8.0f))

            val bone13 = T.addOrReplaceChild(
                "bone13",
                CubeListBuilder.create().texOffs(38, 0)
                    .addBox(-1.5f, -1.5f, 0.0f, 3.0f, 3.0f, 8.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            val bone8 = T.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 8.0f))

            val bone14 = bone8.addOrReplaceChild(
                "bone14",
                CubeListBuilder.create().texOffs(0, 30)
                    .addBox(-1.0f, -1.0f, 0.0f, 2.0f, 2.0f, 10.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            return LayerDefinition.create(meshdefinition, 64, 64)
        }

    }
}