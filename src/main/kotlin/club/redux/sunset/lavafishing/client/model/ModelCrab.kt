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
    private val whole: ModelPart = root.getChild("whole")

    override fun renderToBuffer(
        poseStack: PoseStack, vertexConsumer: VertexConsumer, packedLight: Int, packedOverlay: Int,
        red: Float, green: Float, blue: Float, alpha: Float,
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
                AnimatedEntityAnimationCrab.WALK,
                pLimbSwing,
                pLimbSwingAmount,
                10F,
                Float.MAX_VALUE
            )
        }
    }

    companion object {
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(ModResourceLocation("crab"), "main")

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) { createBodyLayer() }
        }

        fun createBodyLayer(): LayerDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.root

            val whole =
                partdefinition.addOrReplaceChild("whole", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, 0.0f))

            val right_claw = whole.addOrReplaceChild(
                "right_claw",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(6.4464f, -4.6251f, -4.5099f, 0.0f, -0.2618f, 0.0f)
            )

            val right17 = right_claw.addOrReplaceChild(
                "right17",
                CubeListBuilder.create().texOffs(34, 6)
                    .addBox(-3.0f, -8.0f, -1.0f, 3.0f, 1.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(54, 0).addBox(-4.0f, -7.0f, -1.0f, 5.0f, 7.0f, 5.0f, CubeDeformation(0.0f)),
                PartPose.offset(1.75f, 3.0f, -4.5f)
            )

            val right16 = right_claw.addOrReplaceChild(
                "right16",
                CubeListBuilder.create().texOffs(37, 6)
                    .addBox(0.5f, -7.5f, -0.5f, 5.0f, 8.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(0, 22).addBox(-0.5f, -8.5f, -7.5f, 7.0f, 9.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(0, 22).addBox(-0.5f, -8.5f, -7.5f, 7.0f, 9.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(30, 77).addBox(1.5f, -8.5f, 0.5f, 3.0f, 2.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(55, 48).addBox(0.5f, -8.5f, -0.5f, 5.0f, 2.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(9, 71).addBox(-0.5f, -6.5f, 0.5f, 1.0f, 5.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(77, 85).addBox(-0.5f, -7.5f, -0.5f, 1.0f, 8.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(67, 70).addBox(5.5f, -6.5f, 0.5f, 1.0f, 5.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(0, 84).addBox(5.5f, -7.5f, -0.5f, 1.0f, 8.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(-2.75f, 2.5f, -12.0f)
            )

            val right15 = right_claw.addOrReplaceChild(
                "right15",
                CubeListBuilder.create().texOffs(48, 66)
                    .addBox(2.5f, -2.0f, -6.5f, 1.0f, 3.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(82, 39).addBox(-2.5f, -3.0f, -6.5f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 18).addBox(-1.5f, -3.0f, -1.5f, 3.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 44).addBox(-2.5f, -2.0f, -7.5f, 5.0f, 4.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(34, 64).addBox(-3.5f, -2.0f, -6.5f, 1.0f, 3.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(81, 56).addBox(-2.5f, 0.0f, -0.5f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.25f, 3.0f, -16.0f)
            )

            val right14 = right15.addOrReplaceChild(
                "right14",
                CubeListBuilder.create().texOffs(18, 51)
                    .addBox(-4.0f, -4.0f, 0.0f, 5.0f, 4.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(6, 81).addBox(-5.0f, -2.0f, 0.0f, 1.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(62, 80).addBox(1.0f, -2.0f, 0.0f, 1.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(79, 74).addBox(-4.0f, -2.0f, -1.0f, 5.0f, 4.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(56, 29).addBox(-4.0f, 0.0f, 0.0f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(1.5f, -7.0f, -8.5f)
            )

            val left_claw = whole.addOrReplaceChild(
                "left_claw",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-4.5536f, -4.6251f, -4.5099f, 0.0f, 0.2618f, 0.0f)
            )

            val left15 = left_claw.addOrReplaceChild(
                "left15",
                CubeListBuilder.create().texOffs(83, 79)
                    .addBox(-3.0f, -7.0f, -1.0f, 3.0f, 1.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(59, 58).addBox(-4.0f, -6.0f, -1.0f, 5.0f, 6.0f, 5.0f, CubeDeformation(0.0f)),
                PartPose.offset(-0.25f, 3.0f, -4.5f)
            )

            val left16 = left_claw.addOrReplaceChild(
                "left16",
                CubeListBuilder.create().texOffs(28, 22)
                    .addBox(-0.5f, -7.5f, -7.5f, 7.0f, 8.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(21, 37).addBox(0.5f, -6.5f, -0.5f, 5.0f, 7.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(39, 63).addBox(1.5f, -7.5f, 0.5f, 3.0f, 1.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(83, 63).addBox(0.5f, -7.5f, -0.5f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(74, 54).addBox(-0.5f, -5.5f, 0.5f, 1.0f, 4.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(64, 86).addBox(-0.5f, -6.5f, -0.5f, 1.0f, 7.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(21, 71).addBox(5.5f, -5.5f, 0.5f, 1.0f, 4.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(0, 43).addBox(5.5f, -6.5f, -0.5f, 1.0f, 7.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(-4.75f, 2.5f, -12.0f)
            )

            val left17 = left_claw.addOrReplaceChild(
                "left17",
                CubeListBuilder.create().texOffs(71, 21)
                    .addBox(2.5f, -2.0f, -6.5f, 1.0f, 3.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(83, 67).addBox(-2.5f, -3.0f, -6.5f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(28, 18).addBox(-1.5f, -3.0f, -1.5f, 3.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(38, 44).addBox(-2.5f, -2.0f, -7.5f, 5.0f, 4.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(3, 68).addBox(-3.5f, -2.0f, -6.5f, 1.0f, 3.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(82, 48).addBox(-2.5f, 0.0f, -0.5f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(50, 31).addBox(-2.5f, -11.0f, -8.5f, 5.0f, 4.0f, 6.0f, CubeDeformation(0.0f)),
                PartPose.offset(-1.75f, 3.0f, -16.0f)
            )

            val left18 = left17.addOrReplaceChild(
                "left18",
                CubeListBuilder.create().texOffs(81, 50)
                    .addBox(-5.0f, -2.0f, 0.0f, 1.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(45, 81).addBox(1.0f, -2.0f, 0.0f, 1.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(79, 74).addBox(-4.0f, -2.0f, -1.0f, 5.0f, 4.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(83, 65).addBox(-4.0f, 0.0f, 0.0f, 5.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(1.5f, -7.0f, -8.5f)
            )

            val body = whole.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0f, -4.0f, 8.0f))

            val bone = body.addOrReplaceChild(
                "bone",
                CubeListBuilder.create().texOffs(15, 61)
                    .addBox(4.0f, -3.0f, -11.0f, 1.0f, 3.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(60, 48).addBox(-9.0f, -3.0f, -11.0f, 1.0f, 3.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-8.0f, -3.0f, -13.0f, 12.0f, 3.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(0, 13).addBox(-7.0f, -4.0f, -12.0f, 10.0f, 1.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(24, 61).addBox(-6.0f, -5.0f, -10.0f, 1.0f, 1.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(15, 38).addBox(1.0f, -5.0f, -10.0f, 1.0f, 1.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(49, 21).addBox(-5.0f, -5.0f, -11.0f, 6.0f, 1.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(55, 41).addBox(-4.0f, -6.0f, -11.0f, 4.0f, 1.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(0, 6).addBox(-3.0f, -6.0f, -14.0f, 2.0f, 1.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(61, 18).addBox(-4.0f, -5.0f, -4.0f, 4.0f, 1.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(74, 4).addBox(-5.0f, -4.0f, -4.0f, 6.0f, 1.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(0, 38).addBox(-6.0f, -3.0f, -3.0f, 8.0f, 3.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(75, 44).addBox(-5.0f, -3.0f, -1.0f, 6.0f, 3.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(2.0f, 0.0f, 0.0f)
            )

            val bone2 = body.addOrReplaceChild(
                "bone2",
                CubeListBuilder.create().texOffs(74, 70)
                    .addBox(-2.0f, -8.0f, -10.0f, 4.0f, 1.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(69, 0).addBox(-3.0f, -8.0f, -13.0f, 6.0f, 1.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(22, 38).addBox(-1.0f, -8.0f, -14.0f, 2.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(41, 55).addBox(-3.0f, -7.0f, -13.0f, 6.0f, 4.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(49, 23).addBox(-3.0f, -6.0f, -15.0f, 2.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(49, 21).addBox(1.0f, -6.0f, -15.0f, 2.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 22).addBox(-1.0f, -6.0f, -15.0f, 2.0f, 3.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(82, 33).addBox(4.0f, -6.0f, -13.0f, 1.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(24, 82).addBox(-5.0f, -6.0f, -13.0f, 1.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(8, 65).addBox(-3.0f, -4.0f, -14.0f, 6.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(66, 35).addBox(-4.0f, -7.0f, -14.0f, 8.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(34, 55).addBox(-2.0f, -7.0f, -15.0f, 4.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(22, 40).addBox(-1.0f, -7.0f, -16.0f, 2.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(52, 75).addBox(3.0f, -7.0f, -13.0f, 2.0f, 1.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(74, 64).addBox(-5.0f, -7.0f, -13.0f, 2.0f, 1.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(0, 74).addBox(-3.0f, -7.0f, -9.0f, 6.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 3.0f, -4.0f)
            )

            val bone10 = body.addOrReplaceChild(
                "bone10",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-2.5536f, -1.6251f, -22.5099f, 0.0f, 0.2618f, 0.0f)
            )

            val bone4 =
                bone10.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.5f, 0.1251f, 4.0063f))

            val cube_r1 = bone4.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create().texOffs(16, 71)
                    .addBox(-0.5f, -0.5f, -2.0f, 1.0f, 1.0f, 4.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-0.5f, -0.0217f, -1.1655f, -0.4363f, 0.0f, 0.0f)
            )

            val bone3 = bone10.addOrReplaceChild(
                "bone3",
                CubeListBuilder.create().texOffs(4, 87)
                    .addBox(-0.95f, -0.5f, -2.9f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.45f, -0.8749f, 0.9063f)
            )

            val bone5 = bone3.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(-0.95f, 0.5f, -1.9f))

            val cube_r2 = bone5.addOrReplaceChild(
                "cube_r2",
                CubeListBuilder.create().texOffs(0, 120)
                    .addBox(0.0f, -1.0f, -6.0f, 1.0f, 1.0f, 7.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.5f, -1.0f, 0.7854f, 0.0f, 0.0f)
            )

            val bone11 = body.addOrReplaceChild(
                "bone11",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(2.9464f, -1.6251f, -22.5099f, 0.0f, -0.3054f, 0.0f)
            )

            val bone6 =
                bone11.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0f, 0.3534f, 4.8409f))

            val cube_r3 = bone6.addOrReplaceChild(
                "cube_r3",
                CubeListBuilder.create().texOffs(55, 82)
                    .addBox(-0.5f, -0.5f, -2.0f, 1.0f, 1.0f, 4.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, -0.25f, -2.0f, -0.4363f, 0.0f, 0.0f)
            )

            val bone7 = bone11.addOrReplaceChild(
                "bone7",
                CubeListBuilder.create().texOffs(87, 11)
                    .addBox(-0.5f, -0.9783f, -3.3345f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, -0.3966f, 1.3409f)
            )

            val bone8 =
                bone7.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(-0.5f, 0.5217f, -3.3345f))

            val cube_r4 = bone8.addOrReplaceChild(
                "cube_r4",
                CubeListBuilder.create().texOffs(0, 120)
                    .addBox(0.0f, -1.0f, -6.0f, 1.0f, 1.0f, 7.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.7854f, 0.0f, 0.0f)
            )

            val bone9 = body.addOrReplaceChild(
                "bone9",
                CubeListBuilder.create().texOffs(0, 26)
                    .addBox(-2.0f, -1.0f, 0.0f, 2.0f, 2.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(32, 87).addBox(-8.0f, -1.0f, 0.0f, 2.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(4.0f, -2.0f, -18.0f)
            )

            val left_legs =
                whole.addOrReplaceChild("left_legs", CubeListBuilder.create(), PartPose.offset(5.0f, -4.0f, -4.0f))

            val left12 =
                left_legs.addOrReplaceChild("left12", CubeListBuilder.create(), PartPose.offset(-9.0f, -2.0f, 11.0f))

            val left10 =
                left12.addOrReplaceChild("left10", CubeListBuilder.create(), PartPose.offset(-3.987f, 0.8405f, -0.5f))

            val cube_r5 = left10.addOrReplaceChild(
                "cube_r5",
                CubeListBuilder.create().texOffs(18, 86)
                    .addBox(1.0f, 0.5f, -1.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-3.25f, 6.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r6 = left10.addOrReplaceChild(
                "cube_r6",
                CubeListBuilder.create().texOffs(69, 83)
                    .addBox(1.0f, -0.5f, -1.5f, 1.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-2.25f, 5.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r7 = left10.addOrReplaceChild(
                "cube_r7",
                CubeListBuilder.create().texOffs(76, 79)
                    .addBox(0.0f, -1.5f, -1.5f, 2.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.25f, 3.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val left11 =
                left12.addOrReplaceChild("left11", CubeListBuilder.create(), PartPose.offset(-7.487f, 0.8405f, 0.5f))

            val cube_r8 = left11.addOrReplaceChild(
                "cube_r8",
                CubeListBuilder.create().texOffs(76, 15)
                    .addBox(-2.0f, -1.5f, -1.5f, 4.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(2.75f, -0.5f, -1.0f, 0.0f, 0.0f, -1.1781f)
            )

            val left9 =
                left12.addOrReplaceChild("left9", CubeListBuilder.create(), PartPose.offset(-3.487f, -0.1595f, 0.5f))

            val cube_r9 = left9.addOrReplaceChild(
                "cube_r9",
                CubeListBuilder.create().texOffs(56, 69)
                    .addBox(-3.0f, -1.5f, -1.5f, 5.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(2.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.48f)
            )

            val left8 =
                left_legs.addOrReplaceChild("left8", CubeListBuilder.create(), PartPose.offset(-10.0f, -2.0f, 6.5f))

            val left7 =
                left8.addOrReplaceChild("left7", CubeListBuilder.create(), PartPose.offset(-6.487f, 0.8405f, 0.0f))

            val cube_r10 = left7.addOrReplaceChild(
                "cube_r10",
                CubeListBuilder.create().texOffs(86, 58)
                    .addBox(1.0f, 0.5f, -1.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-3.25f, 6.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r11 = left7.addOrReplaceChild(
                "cube_r11",
                CubeListBuilder.create().texOffs(83, 82)
                    .addBox(1.0f, -0.5f, -1.5f, 1.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-2.25f, 5.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r12 = left7.addOrReplaceChild(
                "cube_r12",
                CubeListBuilder.create().texOffs(18, 80)
                    .addBox(0.0f, -1.5f, -1.5f, 2.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.25f, 3.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val left6 =
                left8.addOrReplaceChild("left6", CubeListBuilder.create(), PartPose.offset(-6.487f, 0.8405f, 0.0f))

            val cube_r13 = left6.addOrReplaceChild(
                "cube_r13",
                CubeListBuilder.create().texOffs(28, 70)
                    .addBox(-3.0f, -1.5f, -1.5f, 5.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.9163f)
            )

            val left5 =
                left8.addOrReplaceChild("left5", CubeListBuilder.create(), PartPose.offset(-2.487f, -0.1595f, 0.0f))

            val cube_r14 = left5.addOrReplaceChild(
                "cube_r14",
                CubeListBuilder.create().texOffs(61, 12)
                    .addBox(-4.0f, -1.5f, -1.5f, 6.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3491f)
            )

            val left4 =
                left_legs.addOrReplaceChild("left4", CubeListBuilder.create(), PartPose.offset(-11.0f, -2.0f, 1.5f))

            val left3 =
                left4.addOrReplaceChild("left3", CubeListBuilder.create(), PartPose.offset(-5.487f, 0.8405f, 0.0f))

            val cube_r15 = left3.addOrReplaceChild(
                "cube_r15",
                CubeListBuilder.create().texOffs(87, 0)
                    .addBox(1.0f, 0.5f, -1.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-3.25f, 6.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r16 = left3.addOrReplaceChild(
                "cube_r16",
                CubeListBuilder.create().texOffs(13, 84)
                    .addBox(1.0f, -0.5f, -1.5f, 1.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-2.25f, 5.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r17 = left3.addOrReplaceChild(
                "cube_r17",
                CubeListBuilder.create().texOffs(82, 21)
                    .addBox(0.0f, -1.5f, -1.5f, 2.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.25f, 3.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val left2 =
                left4.addOrReplaceChild("left2", CubeListBuilder.create(), PartPose.offset(-5.487f, 0.8405f, 0.0f))

            val cube_r18 = left2.addOrReplaceChild(
                "cube_r18",
                CubeListBuilder.create().texOffs(38, 38)
                    .addBox(-2.0f, -1.5f, -1.5f, 4.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.9163f)
            )

            val left =
                left4.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.offset(-1.487f, -0.1595f, 0.0f))

            val cube_r19 = left.addOrReplaceChild(
                "cube_r19",
                CubeListBuilder.create().texOffs(41, 73)
                    .addBox(-3.0f, -1.5f, -1.5f, 5.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3491f)
            )

            val right_legs =
                whole.addOrReplaceChild("right_legs", CubeListBuilder.create(), PartPose.offset(5.0f, -4.0f, -4.0f))

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

            val cube_r20 = right6.addOrReplaceChild(
                "cube_r20",
                CubeListBuilder.create().texOffs(85, 69)
                    .addBox(1.0f, 0.5f, -1.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.75f, 5.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r21 = right6.addOrReplaceChild(
                "cube_r21",
                CubeListBuilder.create().texOffs(34, 82)
                    .addBox(1.0f, -0.5f, -1.5f, 1.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-0.75f, 4.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r22 = right6.addOrReplaceChild(
                "cube_r22",
                CubeListBuilder.create().texOffs(39, 79)
                    .addBox(0.0f, -1.5f, -1.5f, 2.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.25f, 2.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val right7 =
                right5.addOrReplaceChild("right7", CubeListBuilder.create(), PartPose.offset(-3.237f, 0.3405f, 1.5f))

            val cube_r23 = right7.addOrReplaceChild(
                "cube_r23",
                CubeListBuilder.create().texOffs(76, 8)
                    .addBox(-2.0f, -1.5f, -1.5f, 4.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.75f, 1.0f, -1.0f, 0.0f, 0.0f, -1.0908f)
            )

            val right8 =
                right5.addOrReplaceChild("right8", CubeListBuilder.create(), PartPose.offset(0.513f, 0.8405f, 4.5f))

            val cube_r24 = right8.addOrReplaceChild(
                "cube_r24",
                CubeListBuilder.create().texOffs(69, 48)
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

            val cube_r25 = right2.addOrReplaceChild(
                "cube_r25",
                CubeListBuilder.create().texOffs(39, 85)
                    .addBox(1.0f, 0.5f, -1.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-3.25f, 5.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r26 = right2.addOrReplaceChild(
                "cube_r26",
                CubeListBuilder.create().texOffs(28, 13)
                    .addBox(1.0f, -0.5f, -1.5f, 1.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-2.25f, 4.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r27 = right2.addOrReplaceChild(
                "cube_r27",
                CubeListBuilder.create().texOffs(0, 78)
                    .addBox(0.0f, -1.5f, -1.5f, 2.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.25f, 2.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val right3 =
                right.addOrReplaceChild("right3", CubeListBuilder.create(), PartPose.offset(-2.987f, -5.1595f, 0.0f))

            val cube_r28 = right3.addOrReplaceChild(
                "cube_r28",
                CubeListBuilder.create().texOffs(69, 38)
                    .addBox(-3.0f, -1.5f, -1.5f, 5.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-0.5f, 1.75f, 0.0f, 0.0f, 0.0f, -1.3963f)
            )

            val right4 =
                right.addOrReplaceChild("right4", CubeListBuilder.create(), PartPose.offset(-1.487f, -0.1595f, 0.0f))

            val cube_r29 = right4.addOrReplaceChild(
                "cube_r29",
                CubeListBuilder.create().texOffs(34, 0)
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

            val cube_r30 = right11.addOrReplaceChild(
                "cube_r30",
                CubeListBuilder.create().texOffs(51, 81)
                    .addBox(1.0f, 0.5f, -1.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.75f, 5.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r31 = right11.addOrReplaceChild(
                "cube_r31",
                CubeListBuilder.create().texOffs(0, 13)
                    .addBox(1.0f, -0.5f, -1.5f, 1.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-0.75f, 4.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val cube_r32 = right11.addOrReplaceChild(
                "cube_r32",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(0.0f, -1.5f, -1.5f, 2.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.25f, 2.0f, 0.0f, 0.0f, 0.0f, -1.5708f)
            )

            val right12 =
                right10.addOrReplaceChild("right12", CubeListBuilder.create(), PartPose.offset(-5.487f, 0.8405f, 0.25f))

            val cube_r33 = right12.addOrReplaceChild(
                "cube_r33",
                CubeListBuilder.create().texOffs(21, 22)
                    .addBox(-2.0f, -1.5f, -1.5f, 4.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.75f, -0.75f, 0.0f, 0.0f, 0.0f, -1.1781f)
            )

            val right13 = right10.addOrReplaceChild(
                "right13",
                CubeListBuilder.create(),
                PartPose.offset(-1.487f, -0.1595f, 0.25f)
            )

            val cube_r34 = right13.addOrReplaceChild(
                "cube_r34",
                CubeListBuilder.create().texOffs(66, 29)
                    .addBox(-3.0f, -1.5f, -1.5f, 5.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.6109f)
            )

            return LayerDefinition.create(meshdefinition, 128, 128)
        }

    }
}