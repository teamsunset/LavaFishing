package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.client.animation.AnimatedEntityAnimationCrab
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


class ModelCrab<T : Entity>(val root: ModelPart) : HierarchicalModel<T>() {
    private val rightLegs: ModelPart = root.getChild("right_legs")
    private val leftLegs: ModelPart = root.getChild("left_legs")
    private val body: ModelPart = root.getChild("body")
    private val leftClaw: ModelPart = root.getChild("left_claw")
    private val rightClaw: ModelPart = root.getChild("right_claw")

    override fun renderToBuffer(
        poseStack: PoseStack, vertexConsumer: VertexConsumer, packedLight: Int, packedOverlay: Int,
        red: Float, green: Float, blue: Float, alpha: Float,
    ) {
        rightLegs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
        leftLegs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
        leftClaw.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
        rightClaw.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
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
                AnimatedEntityAnimationCrab.WALK,
                pLimbSwing,
                pLimbSwingAmount,
                10F,
                Float.MAX_VALUE
            )
        }
    }

    companion object {
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(ModResourceLocation("crab"), "main")
        fun createBodyLayer(): LayerDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.root

            val right_legs = partdefinition.addOrReplaceChild(
                "right_legs",
                CubeListBuilder.create(),
                PartPose.offset(5.0f, 20.0f, -4.0f)
            )

            val right5 = right_legs.addOrReplaceChild(
                "right5",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-1.0f, -2.5f, 11.0f, 0.0f, 3.1416f, 0.0f)
            )

            val right6 = right5.addOrReplaceChild(
                "right6",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-5.737f, 2.3405f, 0.5f, 0.0f, 0.0f, 0.1745f)
            )

            val cube_r1 = right6.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create().texOffs(79, 63)
                    .addBox(1.0f, 0.5f, -1.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.75f, 5.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r2 = right6.addOrReplaceChild(
                "cube_r2",
                CubeListBuilder.create().texOffs(0, 79)
                    .addBox(1.0f, -0.5f, -1.5f, 1.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-0.75f, 4.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r3 = right6.addOrReplaceChild(
                "cube_r3",
                CubeListBuilder.create().texOffs(72, 64)
                    .addBox(0.0f, -1.5f, -1.5f, 2.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.25f, 2.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val right7 =
                right5.addOrReplaceChild("right7", CubeListBuilder.create(), PartPose.offset(-3.237f, 0.3405f, 1.5f))

            val cube_r4 = right7.addOrReplaceChild(
                "cube_r4",
                CubeListBuilder.create().texOffs(67, 47)
                    .addBox(-2.0f, -1.5f, -1.5f, 4.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.75f, 1.0f, -1.0f, 0.0f, 0.0f, -1.0908f)
            )

            val right8 =
                right5.addOrReplaceChild("right8", CubeListBuilder.create(), PartPose.offset(0.513f, 0.8405f, 4.5f))

            val cube_r5 = right8.addOrReplaceChild(
                "cube_r5",
                CubeListBuilder.create().texOffs(59, 61)
                    .addBox(-3.0f, -1.5f, -1.5f, 5.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-2.0f, -0.5f, -4.0f, 0.0f, 0.0f, 0.1745f)
            )

            val right = right_legs.addOrReplaceChild(
                "right",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(2.0f, 0.0f, 6.5f, 0.0f, 3.1416f, 0.0f)
            )

            val right2 =
                right.addOrReplaceChild("right2", CubeListBuilder.create(), PartPose.offset(-2.487f, -0.1595f, 0.0f))

            val cube_r6 = right2.addOrReplaceChild(
                "cube_r6",
                CubeListBuilder.create().texOffs(48, 29)
                    .addBox(1.0f, 0.5f, -1.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-3.25f, 5.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r7 = right2.addOrReplaceChild(
                "cube_r7",
                CubeListBuilder.create().texOffs(18, 65)
                    .addBox(1.0f, -0.5f, -1.5f, 1.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-2.25f, 4.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r8 = right2.addOrReplaceChild(
                "cube_r8",
                CubeListBuilder.create().texOffs(23, 71)
                    .addBox(0.0f, -1.5f, -1.5f, 2.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.25f, 2.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val right3 =
                right.addOrReplaceChild("right3", CubeListBuilder.create(), PartPose.offset(-2.987f, -5.1595f, 0.0f))

            val cube_r9 = right3.addOrReplaceChild(
                "cube_r9",
                CubeListBuilder.create().texOffs(16, 37)
                    .addBox(-3.0f, -1.5f, -1.5f, 5.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-0.5f, 1.75f, 0.0f, 0.0f, 0.0f, -1.3963f)
            )

            val right4 =
                right.addOrReplaceChild("right4", CubeListBuilder.create(), PartPose.offset(-1.487f, -0.1595f, 0.0f))

            val cube_r10 = right4.addOrReplaceChild(
                "cube_r10",
                CubeListBuilder.create().texOffs(35, 54)
                    .addBox(-5.0f, -1.5f, -1.5f, 7.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(1.0f, -1.25f, 0.0f, 0.0f, 0.0f, 1.2217f)
            )

            val right10 = right_legs.addOrReplaceChild(
                "right10",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(1.0f, -1.5f, 1.25f, 0.0f, 3.1416f, 0.0f)
            )

            val right11 = right10.addOrReplaceChild(
                "right11",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-5.737f, 1.8405f, 0.25f, 0.0f, 0.0f, 0.3054f)
            )

            val cube_r11 = right11.addOrReplaceChild(
                "cube_r11",
                CubeListBuilder.create().texOffs(28, 13)
                    .addBox(1.0f, 0.5f, -1.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.75f, 5.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r12 = right11.addOrReplaceChild(
                "cube_r12",
                CubeListBuilder.create().texOffs(0, 13)
                    .addBox(1.0f, -0.5f, -1.5f, 1.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-0.75f, 4.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r13 = right11.addOrReplaceChild(
                "cube_r13",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(0.0f, -1.5f, -1.5f, 2.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.25f, 2.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val right12 =
                right10.addOrReplaceChild("right12", CubeListBuilder.create(), PartPose.offset(-5.487f, 0.8405f, 0.25f))

            val cube_r14 = right12.addOrReplaceChild(
                "cube_r14",
                CubeListBuilder.create().texOffs(17, 22)
                    .addBox(-2.0f, -1.5f, -1.5f, 4.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.75f, -0.75f, 0.0f, 0.0f, 0.0f, -1.1781f)
            )

            val right13 = right10.addOrReplaceChild(
                "right13",
                CubeListBuilder.create(),
                PartPose.offset(-1.487f, -0.1595f, 0.25f)
            )

            val cube_r15 = right13.addOrReplaceChild(
                "cube_r15",
                CubeListBuilder.create().texOffs(56, 55)
                    .addBox(-3.0f, -1.5f, -1.5f, 5.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.6109f)
            )

            val left_legs = partdefinition.addOrReplaceChild(
                "left_legs",
                CubeListBuilder.create(),
                PartPose.offset(5.0f, 20.0f, -4.0f)
            )

            val left12 =
                left_legs.addOrReplaceChild("left12", CubeListBuilder.create(), PartPose.offset(-9.0f, -2.0f, 11.0f))

            val left10 =
                left12.addOrReplaceChild("left10", CubeListBuilder.create(), PartPose.offset(-3.987f, 0.8405f, -0.5f))

            val cube_r16 = left10.addOrReplaceChild(
                "cube_r16",
                CubeListBuilder.create().texOffs(79, 67)
                    .addBox(1.0f, 0.5f, -1.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-3.25f, 6.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r17 = left10.addOrReplaceChild(
                "cube_r17",
                CubeListBuilder.create().texOffs(15, 79)
                    .addBox(1.0f, -0.5f, -1.5f, 1.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-2.25f, 5.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r18 = left10.addOrReplaceChild(
                "cube_r18",
                CubeListBuilder.create().texOffs(72, 70)
                    .addBox(0.0f, -1.5f, -1.5f, 2.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.25f, 3.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val left11 =
                left12.addOrReplaceChild("left11", CubeListBuilder.create(), PartPose.offset(-7.487f, 0.8405f, 0.5f))

            val cube_r19 = left11.addOrReplaceChild(
                "cube_r19",
                CubeListBuilder.create().texOffs(61, 67)
                    .addBox(-2.0f, -1.5f, -1.5f, 4.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(2.75f, -0.5f, -1.0f, 0.0f, 0.0f, -1.1781f)
            )

            val left9 =
                left12.addOrReplaceChild("left9", CubeListBuilder.create(), PartPose.offset(-3.487f, -0.1595f, 0.5f))

            val cube_r20 = left9.addOrReplaceChild(
                "cube_r20",
                CubeListBuilder.create().texOffs(0, 62)
                    .addBox(-3.0f, -1.5f, -1.5f, 5.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(2.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.48f)
            )

            val left8 =
                left_legs.addOrReplaceChild("left8", CubeListBuilder.create(), PartPose.offset(-10.0f, -2.0f, 6.5f))

            val left7 =
                left8.addOrReplaceChild("left7", CubeListBuilder.create(), PartPose.offset(-6.487f, 0.8405f, 0.0f))

            val cube_r21 = left7.addOrReplaceChild(
                "cube_r21",
                CubeListBuilder.create().texOffs(80, 25)
                    .addBox(1.0f, 0.5f, -1.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-3.25f, 6.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r22 = left7.addOrReplaceChild(
                "cube_r22",
                CubeListBuilder.create().texOffs(23, 79)
                    .addBox(1.0f, -0.5f, -1.5f, 1.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-2.25f, 5.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r23 = left7.addOrReplaceChild(
                "cube_r23",
                CubeListBuilder.create().texOffs(58, 73)
                    .addBox(0.0f, -1.5f, -1.5f, 2.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.25f, 3.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val left6 =
                left8.addOrReplaceChild("left6", CubeListBuilder.create(), PartPose.offset(-6.487f, 0.8405f, 0.0f))

            val cube_r24 = left6.addOrReplaceChild(
                "cube_r24",
                CubeListBuilder.create().texOffs(67, 33)
                    .addBox(-3.0f, -1.5f, -1.5f, 5.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.9163f)
            )

            val left5 =
                left8.addOrReplaceChild("left5", CubeListBuilder.create(), PartPose.offset(-2.487f, -0.1595f, 0.0f))

            val cube_r25 = left5.addOrReplaceChild(
                "cube_r25",
                CubeListBuilder.create().texOffs(54, 41)
                    .addBox(-4.0f, -1.5f, -1.5f, 6.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3491f)
            )

            val left4 =
                left_legs.addOrReplaceChild("left4", CubeListBuilder.create(), PartPose.offset(-11.0f, -2.0f, 1.5f))

            val left3 =
                left4.addOrReplaceChild("left3", CubeListBuilder.create(), PartPose.offset(-5.487f, 0.8405f, 0.0f))

            val cube_r26 = left3.addOrReplaceChild(
                "cube_r26",
                CubeListBuilder.create().texOffs(80, 36)
                    .addBox(1.0f, 0.5f, -1.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-3.25f, 6.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r27 = left3.addOrReplaceChild(
                "cube_r27",
                CubeListBuilder.create().texOffs(79, 50)
                    .addBox(1.0f, -0.5f, -1.5f, 1.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-2.25f, 5.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r28 = left3.addOrReplaceChild(
                "cube_r28",
                CubeListBuilder.create().texOffs(30, 76)
                    .addBox(0.0f, -1.5f, -1.5f, 2.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.25f, 3.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val left2 =
                left4.addOrReplaceChild("left2", CubeListBuilder.create(), PartPose.offset(-5.487f, 0.8405f, 0.0f))

            val cube_r29 = left2.addOrReplaceChild(
                "cube_r29",
                CubeListBuilder.create().texOffs(34, 70)
                    .addBox(-2.0f, -1.5f, -1.5f, 4.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.9163f)
            )

            val left =
                left4.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.offset(-1.487f, -0.1595f, 0.0f))

            val cube_r30 = left.addOrReplaceChild(
                "cube_r30",
                CubeListBuilder.create().texOffs(63, 20)
                    .addBox(-3.0f, -1.5f, -1.5f, 5.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3491f)
            )

            val body =
                partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0f, 20.0f, 8.0f))

            val bone = body.addOrReplaceChild(
                "bone",
                CubeListBuilder.create().texOffs(54, 22)
                    .addBox(4.0f, -3.0f, -11.0f, 1.0f, 3.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(54, 0).addBox(-9.0f, -3.0f, -11.0f, 1.0f, 3.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-8.0f, -3.0f, -13.0f, 12.0f, 3.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(0, 13).addBox(-7.0f, -4.0f, -12.0f, 10.0f, 1.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(22, 56).addBox(-6.0f, -5.0f, -10.0f, 1.0f, 1.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(49, 0).addBox(1.0f, -5.0f, -10.0f, 1.0f, 1.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(24, 37).addBox(-5.0f, -5.0f, -11.0f, 6.0f, 1.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(53, 48).addBox(-4.0f, -6.0f, -11.0f, 4.0f, 1.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(0, 6).addBox(-3.0f, -6.0f, -14.0f, 2.0f, 1.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(77, 9).addBox(-4.0f, -5.0f, -4.0f, 4.0f, 1.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(63, 0).addBox(-5.0f, -4.0f, -4.0f, 6.0f, 1.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(34, 0).addBox(-6.0f, -3.0f, -3.0f, 8.0f, 3.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(70, 15).addBox(-5.0f, -3.0f, -1.0f, 6.0f, 3.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(2.0f, 0.0f, 0.0f)
            )

            val bone2 = body.addOrReplaceChild(
                "bone2",
                CubeListBuilder.create().texOffs(69, 39)
                    .addBox(-2.0f, -8.0f, -10.0f, 4.0f, 1.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(28, 17).addBox(-3.0f, -8.0f, -13.0f, 6.0f, 1.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(18, 48).addBox(-1.0f, -8.0f, -14.0f, 2.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(44, 33).addBox(-4.0f, -7.0f, -13.0f, 8.0f, 4.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(63, 4).addBox(-3.0f, -6.0f, -15.0f, 2.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(36, 60).addBox(1.0f, -6.0f, -15.0f, 2.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 22).addBox(-1.0f, -6.0f, -15.0f, 2.0f, 3.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(74, 76).addBox(4.0f, -6.0f, -13.0f, 1.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(50, 76).addBox(-5.0f, -6.0f, -13.0f, 1.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(43, 41).addBox(-3.0f, -4.0f, -14.0f, 6.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 48).addBox(-4.0f, -7.0f, -14.0f, 8.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(56, 20).addBox(-2.0f, -7.0f, -15.0f, 4.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(14, 60).addBox(-1.0f, -7.0f, -16.0f, 2.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(65, 27).addBox(3.0f, -7.0f, -13.0f, 2.0f, 1.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(23, 65).addBox(-5.0f, -7.0f, -13.0f, 2.0f, 1.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(0, 60).addBox(-3.0f, -7.0f, -9.0f, 6.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 3.0f, -4.0f)
            )

            val LT = body.addOrReplaceChild(
                "LT",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-2.5536f, -1.6251f, -22.5099f, 0.0f, 0.2618f, 0.0f)
            )

            val bone4 = LT.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.5f, 0.1251f, 4.0063f))

            val cube_r31 = bone4.addOrReplaceChild(
                "cube_r31",
                CubeListBuilder.create().texOffs(34, 5)
                    .addBox(-0.5f, -0.5f, -2.0f, 1.0f, 1.0f, 4.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-0.5f, -0.0217f, -1.1655f, -0.4363f, 0.0f, 0.0f)
            )

            val bone3 = LT.addOrReplaceChild(
                "bone3",
                CubeListBuilder.create().texOffs(80, 73)
                    .addBox(-0.95f, -0.5f, -2.9f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.45f, -0.8749f, 0.9063f)
            )

            val bone5 = bone3.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(-0.95f, 0.5f, -1.9f))

            val cube_r32 = bone5.addOrReplaceChild(
                "cube_r32",
                CubeListBuilder.create().texOffs(9, 35)
                    .addBox(0.0f, -1.0f, 1.0f, 1.0f, 1.0f, -7.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.5f, -1.0f, 0.7854f, 0.0f, 0.0f)
            )

            val RT = body.addOrReplaceChild(
                "RT",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(2.9464f, -1.6251f, -22.5099f, 0.0f, -0.3054f, 0.0f)
            )

            val bone6 = RT.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0f, 0.3534f, 4.8409f))

            val cube_r33 = bone6.addOrReplaceChild(
                "cube_r33",
                CubeListBuilder.create().texOffs(34, 45)
                    .addBox(-0.5f, -0.5f, -2.0f, 1.0f, 1.0f, 4.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, -0.25f, -2.0f, -0.4363f, 0.0f, 0.0f)
            )

            val bone7 = RT.addOrReplaceChild(
                "bone7",
                CubeListBuilder.create().texOffs(64, 81)
                    .addBox(-0.5f, -0.9783f, -3.3345f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, -0.3966f, 1.3409f)
            );

            val bone8 =
                bone7.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(-0.5f, 0.5217f, -3.3345f))

            val cube_r34 = bone8.addOrReplaceChild(
                "cube_r34",
                CubeListBuilder.create().texOffs(9, 46)
                    .addBox(0.0f, -1.0f, 1.0f, 1.0f, 1.0f, -7.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.7854f, 0.0f, 0.0f)
            )

            val EYE = body.addOrReplaceChild(
                "EYE",
                CubeListBuilder.create().texOffs(0, 40)
                    .addBox(-2.0f, -1.0f, 0.0f, 2.0f, 2.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(29, 82).addBox(-8.0f, -1.0f, 0.0f, 2.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(4.0f, -2.0f, -18.0f)
            )

            val left_claw = partdefinition.addOrReplaceChild(
                "left_claw",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-4.5536f, 19.3749f, -4.5099f, 0.0f, 0.2618f, 0.0f)
            )

            val left15 = left_claw.addOrReplaceChild(
                "left15",
                CubeListBuilder.create().texOffs(79, 32)
                    .addBox(-3.0f, -7.0f, -1.0f, 3.0f, 1.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(19, 45).addBox(-4.0f, -6.0f, -1.0f, 5.0f, 6.0f, 5.0f, CubeDeformation(0.0f)),
                PartPose.offset(-0.25f, 3.0f, -4.5f)
            )

            val left16 = left_claw.addOrReplaceChild(
                "left16",
                CubeListBuilder.create().texOffs(23, 22)
                    .addBox(-0.5f, -7.5f, -7.5f, 7.0f, 8.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(24, 22).addBox(0.5f, -6.5f, -0.5f, 5.0f, 7.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(0, 72).addBox(1.5f, -7.5f, 0.5f, 3.0f, 1.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(76, 19).addBox(0.5f, -7.5f, -0.5f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(11, 65).addBox(-0.5f, -5.5f, 0.5f, 1.0f, 4.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(8, 81).addBox(-0.5f, -6.5f, -0.5f, 1.0f, 7.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(65, 5).addBox(5.5f, -5.5f, 0.5f, 1.0f, 4.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(44, 80).addBox(5.5f, -6.5f, -0.5f, 1.0f, 7.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(-4.75f, 2.5f, -12.0f)
            )

            val left17 = left_claw.addOrReplaceChild(
                "left17",
                CubeListBuilder.create().texOffs(28, 56)
                    .addBox(2.5f, -2.0f, -6.5f, 1.0f, 3.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(78, 46).addBox(-2.5f, -3.0f, -6.5f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(54, 10).addBox(-1.5f, -3.0f, -1.5f, 3.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(37, 6).addBox(-2.5f, -2.0f, -7.5f, 5.0f, 4.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(14, 56).addBox(-3.5f, -2.0f, -6.5f, 1.0f, 3.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(14, 77).addBox(-2.5f, 0.0f, -0.5f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(-1.75f, 3.0f, -16.0f)
            )

            val left18 = left17.addOrReplaceChild(
                "left18",
                CubeListBuilder.create().texOffs(-1, 50)
                    .addBox(-4.0f, -4.0f, 0.0f, 5.0f, 4.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(-1, 50).addBox(-4.0f, -4.0f, 0.0f, 5.0f, 4.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(64, 75).addBox(-5.0f, -2.0f, 0.0f, 1.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(8, 75).addBox(1.0f, -2.0f, 0.0f, 1.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(72, 58).addBox(-4.0f, -2.0f, -1.0f, 5.0f, 4.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(78, 0).addBox(-4.0f, 0.0f, 0.0f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(1.5f, -7.0f, -8.5f)
            )

            val right_claw = partdefinition.addOrReplaceChild(
                "right_claw",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(6.4464f, 19.3749f, -4.5099f, 0.0f, -0.2618f, 0.0f)
            )

            val right17 = right_claw.addOrReplaceChild(
                "right17",
                CubeListBuilder.create().texOffs(79, 22)
                    .addBox(-3.0f, -8.0f, -1.0f, 3.0f, 1.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(41, 17).addBox(-4.0f, -7.0f, -1.0f, 5.0f, 7.0f, 5.0f, CubeDeformation(0.0f)),
                PartPose.offset(1.75f, 3.0f, -4.5f)
            )

            val right16 = right_claw.addOrReplaceChild(
                "right16",
                CubeListBuilder.create().texOffs(0, 22)
                    .addBox(0.5f, -7.5f, -0.5f, 5.0f, 8.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(-1, 22).addBox(-0.5f, -8.5f, -7.5f, 7.0f, 9.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(70, 53).addBox(1.5f, -8.5f, 0.5f, 3.0f, 2.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(72, 43).addBox(0.5f, -8.5f, -0.5f, 5.0f, 2.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(49, 64).addBox(-0.5f, -6.5f, 0.5f, 1.0f, 5.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(60, 79).addBox(-0.5f, -7.5f, -0.5f, 1.0f, 8.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(37, 60).addBox(5.5f, -6.5f, 0.5f, 1.0f, 5.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(40, 76).addBox(5.5f, -7.5f, -0.5f, 1.0f, 8.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(-2.75f, 2.5f, -12.0f)
            )

            val right15 = right_claw.addOrReplaceChild(
                "right15",
                CubeListBuilder.create().texOffs(56, 11)
                    .addBox(2.5f, -2.0f, -6.5f, 1.0f, 3.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(77, 12).addBox(-2.5f, -3.0f, -6.5f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 18).addBox(-1.5f, -3.0f, -1.5f, 3.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 37).addBox(-2.5f, -2.0f, -7.5f, 5.0f, 4.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(48, 55).addBox(-3.5f, -2.0f, -6.5f, 1.0f, 3.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(0, 77).addBox(-2.5f, 0.0f, -0.5f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.25f, 3.0f, -16.0f)
            )

            val right14 = right15.addOrReplaceChild(
                "right14",
                CubeListBuilder.create().texOffs(38, 44)
                    .addBox(-4.0f, -4.0f, 0.0f, 5.0f, 4.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(44, 74).addBox(-5.0f, -2.0f, 0.0f, 1.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(74, 26).addBox(1.0f, -2.0f, 0.0f, 1.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(72, 4).addBox(-4.0f, -2.0f, -1.0f, 5.0f, 4.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 68).addBox(-4.0f, 0.0f, 0.0f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(1.5f, -7.0f, -8.5f)
            )

            return LayerDefinition.create(meshdefinition, 128, 128)
        }

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) { createBodyLayer() }
        }
    }
}