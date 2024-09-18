package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.client.animation.AnimatedEntityAnimationLobster
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
import net.neoforged.neoforge.client.event.EntityRenderersEvent


class ModelLobster<T : Entity>(val root: ModelPart) : HierarchicalModel<T>() {
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
            if (pEntity.onGround()) {
                animateWalk(
                    AnimatedEntityAnimationLobster.WALK,
                    pLimbSwing,
                    pLimbSwingAmount,
                    10F,
                    Float.MAX_VALUE
                )
            } else if (pEntity.isInLava || pEntity.isInWater) {
                animateWalk(
                    AnimatedEntityAnimationLobster.SWIM,
                    pLimbSwing,
                    pLimbSwingAmount,
                    10F,
                    Float.MAX_VALUE
                )
            }
        }
    }

    companion object {
        @JvmField val LAYER_LOCATION: ModelLayerLocation =
            ModelLayerLocation(LavaFishing.resourceLocation("lobster"), "main")

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) { createBodyLayer() }
        }

        fun createBodyLayer(): LayerDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.root

            val whole =
                partdefinition.addOrReplaceChild("whole", CubeListBuilder.create(), PartPose.offset(1.25f, 24.0f, 0.0f))

            val left_legs = whole.addOrReplaceChild(
                "left_legs",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-0.75f, 0.0f, 13.0f, 0.0f, 3.1416f, 0.0f)
            )

            val FR2 = left_legs.addOrReplaceChild(
                "FR2",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-3.0f, -5.0f, 3.0f, 0.0f, 0.0f, 0.48f)
            )

            val bone7 = FR2.addOrReplaceChild(
                "bone7",
                CubeListBuilder.create().texOffs(38, 57)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            val bone8 = FR2.addOrReplaceChild(
                "bone8",
                CubeListBuilder.create().texOffs(12, 56)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.8727f)
            )

            val bone15 = bone8.addOrReplaceChild(
                "bone15",
                CubeListBuilder.create().texOffs(55, 32)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(10, 50).addBox(-5.75f, -1.0f, -1.0f, 1.0f, 1.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0472f)
            )

            val MID2 = left_legs.addOrReplaceChild(
                "MID2",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-3.0f, -5.0f, 6.0f, 0.0f, 0.0f, -0.1309f)
            )

            val bone19 = MID2.addOrReplaceChild(
                "bone19",
                CubeListBuilder.create().texOffs(55, 28)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.8727f)
            )

            val bone20 = bone19.addOrReplaceChild(
                "bone20",
                CubeListBuilder.create().texOffs(54, 53)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.309f)
            )

            val bone21 = bone20.addOrReplaceChild(
                "bone21",
                CubeListBuilder.create().texOffs(28, 54)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(44, 0).addBox(-5.75f, -1.0f, -1.0f, 1.0f, 1.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.7854f)
            )

            val END2 =
                left_legs.addOrReplaceChild("END2", CubeListBuilder.create(), PartPose.offset(-3.0f, -5.0f, 10.0f))

            val bone22 = END2.addOrReplaceChild(
                "bone22",
                CubeListBuilder.create().texOffs(53, 18)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.6109f)
            )

            val bone23 = bone22.addOrReplaceChild(
                "bone23",
                CubeListBuilder.create().texOffs(52, 11)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.3526f)
            )

            val bone24 = bone23.addOrReplaceChild(
                "bone24",
                CubeListBuilder.create().texOffs(50, 49)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(39, 41).addBox(-5.75f, -1.0f, -1.0f, 1.0f, 1.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.3927f)
            )

            val right_legs =
                whole.addOrReplaceChild("right_legs", CubeListBuilder.create(), PartPose.offset(-1.25f, 0.0f, 0.0f))

            val FR = right_legs.addOrReplaceChild(
                "FR",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-3.0f, -5.0f, 3.0f, 0.0f, 0.0f, 0.829f)
            )

            val bone11 = FR.addOrReplaceChild(
                "bone11",
                CubeListBuilder.create().texOffs(32, 61)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            val bone9 = FR.addOrReplaceChild(
                "bone9",
                CubeListBuilder.create().texOffs(60, 59)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.3526f)
            )

            val bone10 = bone9.addOrReplaceChild(
                "bone10",
                CubeListBuilder.create().texOffs(60, 47)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(44, 61).addBox(-5.75f, -1.0f, -1.0f, 1.0f, 1.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.6981f)
            )

            val MID = right_legs.addOrReplaceChild("MID", CubeListBuilder.create(), PartPose.offset(-3.0f, -5.0f, 6.0f))

            val bone12 = MID.addOrReplaceChild(
                "bone12",
                CubeListBuilder.create().texOffs(60, 39)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.6545f)
            )

            val bone13 = bone12.addOrReplaceChild(
                "bone13",
                CubeListBuilder.create().texOffs(10, 60)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.3963f)
            )

            val bone14 = bone13.addOrReplaceChild(
                "bone14",
                CubeListBuilder.create().texOffs(58, 22)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(32, 51).addBox(-5.75f, -1.0f, -1.0f, 1.0f, 1.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.2182f)
            )

            val END =
                right_legs.addOrReplaceChild("END", CubeListBuilder.create(), PartPose.offset(-3.0f, -5.0f, 10.0f))

            val bone16 = END.addOrReplaceChild(
                "bone16",
                CubeListBuilder.create().texOffs(22, 58)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8727f)
            )

            val bone17 = bone16.addOrReplaceChild(
                "bone17",
                CubeListBuilder.create().texOffs(0, 58)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.7453f)
            )

            val bone18 = bone17.addOrReplaceChild(
                "bone18",
                CubeListBuilder.create().texOffs(50, 57)
                    .addBox(-4.75f, -1.0f, -1.0f, 4.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(26, 51).addBox(-5.75f, -1.0f, -1.0f, 1.0f, 1.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.2618f)
            )

            val tail = whole.addOrReplaceChild(
                "tail",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-1.0f, -10.0f, 9.0f, 0.2618f, 0.0f, 0.0f)
            )

            val bone1 = tail.addOrReplaceChild("bone1", CubeListBuilder.create(), PartPose.offset(-3.0f, 6.0f, 1.0f))

            val bone26 = bone1.addOrReplaceChild(
                "bone26",
                CubeListBuilder.create().texOffs(32, 29)
                    .addBox(-1.0f, -6.75f, 9.0f, 8.0f, 5.0f, 7.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, -10.0f)
            )

            val bone25 = bone1.addOrReplaceChild(
                "bone25",
                CubeListBuilder.create().texOffs(0, 40)
                    .addBox(-4.0f, -3.0f, 1.0f, 8.0f, 5.0f, 5.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(3.0f, -3.75f, 5.0f, -0.2182f, 0.0f, 0.0f)
            )

            val bone27 = bone25.addOrReplaceChild(
                "bone27",
                CubeListBuilder.create().texOffs(26, 0)
                    .addBox(-4.0f, -1.0f, 0.0f, 7.0f, 3.0f, 4.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.5f, -1.75f, 6.0f, -0.1309f, 0.0f, 0.0f)
            )

            val bone28 = bone27.addOrReplaceChild(
                "bone28",
                CubeListBuilder.create().texOffs(48, 0)
                    .addBox(-0.5f, -3.0f, 1.0f, 6.0f, 3.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-3.0f, 2.0f, 3.0f, -0.1745f, 0.0f, 0.0f)
            )

            val bone29 = bone28.addOrReplaceChild(
                "bone29",
                CubeListBuilder.create().texOffs(88, 0)
                    .addBox(-2.25f, -2.75f, -1.0f, 10.0f, 2.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(0, 117).addBox(-8.25f, -1.75f, 1.0f, 6.0f, 1.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(0, 117).addBox(7.75f, -1.75f, 1.0f, 6.0f, 1.0f, 10.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-0.25f, 0.0f, 5.0f, -0.2182f, 0.0f, 0.0f)
            )

            val right_tong = whole.addOrReplaceChild(
                "right_tong",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-7.0f, -7.4583f, -2.9167f, 0.0436f, 0.2182f, 0.0f)
            )

            val a = right_tong.addOrReplaceChild(
                "a",
                CubeListBuilder.create().texOffs(0, 29)
                    .addBox(0.0f, -1.5417f, 2.9167f, 2.0f, 4.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            val s = a.addOrReplaceChild(
                "s",
                CubeListBuilder.create().texOffs(0, 50)
                    .addBox(-1.0f, -1.5417f, -1.0833f, 3.0f, 4.0f, 4.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            val d = s.addOrReplaceChild(
                "d",
                CubeListBuilder.create().texOffs(26, 41)
                    .addBox(-1.0f, -5.5f, 1.0f, 4.0f, 5.0f, 5.0f, CubeDeformation(0.0f)),
                PartPose.offset(-1.0f, 3.4583f, -7.0833f)
            )

            val up = s.addOrReplaceChild(
                "up",
                CubeListBuilder.create().texOffs(14, 50)
                    .addBox(-1.0f, -3.75f, 1.0f, 4.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(21, 42).addBox(-1.0f, -1.75f, 1.0f, 4.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(26, 7).addBox(-1.0f, -3.0f, 0.0f, 4.0f, 2.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(-1.0f, 0.7083f, -11.0833f)
            )

            val dowm = s.addOrReplaceChild(
                "dowm",
                CubeListBuilder.create().texOffs(21, 40)
                    .addBox(-1.0f, -2.0f, 0.0f, 4.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(52, 6).addBox(-1.0f, -1.0f, 0.0f, 4.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offset(-1.0f, 2.4583f, -9.0833f)
            )

            val left_tong = whole.addOrReplaceChild(
                "left_tong",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(5.0f, -7.4583f, -2.9167f, 0.1309f, -0.1745f, 0.0f)
            )

            val a2 = left_tong.addOrReplaceChild(
                "a2",
                CubeListBuilder.create().texOffs(0, 17)
                    .addBox(-2.0f, -1.5417f, 2.9167f, 2.0f, 4.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            val s2 = a2.addOrReplaceChild(
                "s2",
                CubeListBuilder.create().texOffs(40, 49)
                    .addBox(-2.0f, -1.5417f, -1.0833f, 3.0f, 4.0f, 4.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            val d2 = s2.addOrReplaceChild(
                "d2",
                CubeListBuilder.create().texOffs(40, 18)
                    .addBox(-1.0f, -5.5f, 1.0f, 4.0f, 5.0f, 5.0f, CubeDeformation(0.0f)),
                PartPose.offset(-1.0f, 3.4583f, -7.0833f)
            )

            val up2 = s2.addOrReplaceChild(
                "up2",
                CubeListBuilder.create().texOffs(22, 29)
                    .addBox(-1.0f, -3.75f, 1.0f, 4.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(30, 25).addBox(-1.0f, -1.75f, 1.0f, 4.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 23).addBox(-1.0f, -3.0f, 0.0f, 4.0f, 2.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(-1.0f, 0.7083f, -11.0833f)
            )

            val dowm2 = s2.addOrReplaceChild(
                "dowm2",
                CubeListBuilder.create().texOffs(30, 23)
                    .addBox(-1.0f, -2.0f, 0.0f, 4.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(30, 18).addBox(-1.0f, -1.0f, 0.0f, 4.0f, 2.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offset(-1.0f, 2.4583f, -9.0833f)
            )

            val head = whole.addOrReplaceChild(
                "head",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-4.0f, -3.25f, -0.25f, 8.0f, 7.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-3.5f, -1.5f, -2.25f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 2).addBox(2.5f, -1.5f, -2.25f, 1.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(44, 41).addBox(-3.0f, -2.5f, -3.25f, 6.0f, 5.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offset(-1.0f, -8.5f, -0.75f)
            )

            val whiskers = whole.addOrReplaceChild(
                "whiskers",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-3.25f, -7.5f, -4.0f, -0.3927f, 0.0f, 0.0f)
            )

            val bone = whiskers.addOrReplaceChild(
                "bone",
                CubeListBuilder.create().texOffs(0, 5)
                    .addBox(-0.5f, -0.5f, -3.5f, 1.0f, 1.0f, 4.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.25f, 0.0f, -0.5f, 0.0f, 0.1745f, 0.0f)
            )

            val bone2 = bone.addOrReplaceChild(
                "bone2",
                CubeListBuilder.create().texOffs(22, 35)
                    .addBox(0.0f, -0.5f, -3.5f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-0.5f, 0.0f, -3.0f, 0.3927f, 0.0f, 0.0f)
            )

            val bone3 = bone2.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, -2.0f))

            val bone_r1 = bone3.addOrReplaceChild(
                "bone_r1",
                CubeListBuilder.create().texOffs(56, 61)
                    .addBox(-0.5f, -0.5f, -2.0f, 1.0f, 1.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.5f, 0.0f, -1.5f, 0.48f, 0.0f, 0.0f)
            )

            val huxu2 =
                whiskers.addOrReplaceChild("huxu2", CubeListBuilder.create(), PartPose.offset(4.25f, 0.0f, 0.0f))

            val bone4 = huxu2.addOrReplaceChild(
                "bone4",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-0.5f, -0.5f, -3.5f, 1.0f, 1.0f, 4.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, -0.5f, 0.0f, -0.2182f, 0.0f)
            )

            val bone5 = bone4.addOrReplaceChild(
                "bone5",
                CubeListBuilder.create().texOffs(0, 35)
                    .addBox(0.0f, -0.5f, -3.25f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-0.5f, 0.0f, -3.25f, 0.5236f, 0.0f, 0.0f)
            )

            val bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, -1.75f))

            val bone_r2 = bone6.addOrReplaceChild(
                "bone_r2",
                CubeListBuilder.create().texOffs(50, 61)
                    .addBox(-0.5f, -0.5f, -2.0f, 1.0f, 1.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.5f, 0.0f, -1.5f, 0.2618f, 0.0f, 0.0f)
            )

            return LayerDefinition.create(meshdefinition, 128, 128)
        }

    }
}