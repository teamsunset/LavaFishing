package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.client.animation.AnimatedEntityAnimationSnail
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


class ModelSnail<T : Entity>(val root: ModelPart) : HierarchicalModel<T>() {
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
            animateWalk(
                AnimatedEntityAnimationSnail.WALK,
                pLimbSwing,
                pLimbSwingAmount,
                10F,
                Float.MAX_VALUE
            )
        }
    }

    companion object {
        @JvmField val LAYER_LOCATION: ModelLayerLocation =
            ModelLayerLocation(LavaFishing.resourceLocation("snail"), "main")

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) { createBodyLayer() }
        }

        fun createBodyLayer(): LayerDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.root

            val whole =
                partdefinition.addOrReplaceChild("whole", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, 0.0f))

            val shell = whole.addOrReplaceChild(
                "shell",
                CubeListBuilder.create().texOffs(36, 0)
                    .addBox(-4.0f, -16.0f, -3.0f, 8.0f, 2.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-6.0f, -14.0f, -5.0f, 12.0f, 2.0f, 12.0f, CubeDeformation(0.0f))
                    .texOffs(62, 43).addBox(-3.0f, -15.0f, 5.0f, 6.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(60, 36).addBox(-5.0f, -15.0f, -2.0f, 1.0f, 1.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(11, 44).addBox(4.0f, -15.0f, -2.0f, 1.0f, 1.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(21, 62).addBox(-3.0f, -15.0f, -4.0f, 6.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 27).addBox(-6.0f, -12.0f, -6.0f, 12.0f, 1.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(0, 36).addBox(-5.0f, -13.0f, -6.0f, 10.0f, 1.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(29, 14).addBox(-5.0f, -13.0f, -6.0f, 10.0f, 1.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(11, 51).addBox(-4.0f, -13.0f, -7.0f, 8.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(36, 10).addBox(-5.0f, -12.0f, -7.0f, 10.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(60, 6).addBox(-4.0f, -12.0f, -8.0f, 8.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(20, 44).addBox(-7.0f, -12.0f, -1.0f, 1.0f, 9.0f, 9.0f, CubeDeformation(0.0f))
                    .texOffs(0, 44).addBox(6.0f, -12.0f, -1.0f, 1.0f, 9.0f, 9.0f, CubeDeformation(0.0f))
                    .texOffs(55, 45).addBox(3.0f, -11.0f, 1.0f, 2.0f, 6.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(40, 52).addBox(-5.0f, -11.0f, 0.0f, 2.0f, 6.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(7, 62).addBox(-3.0f, -11.0f, 5.0f, 6.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(0, 27).addBox(5.0f, -11.0f, -4.0f, 1.0f, 3.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(0, 36).addBox(6.0f, -12.0f, -3.0f, 1.0f, 3.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(29, 14).addBox(-7.0f, -12.0f, -3.0f, 1.0f, 3.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(0, 14).addBox(-6.0f, -11.0f, -4.0f, 1.0f, 3.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(34, 41).addBox(-6.0f, -12.0f, 7.0f, 12.0f, 9.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(38, 22).addBox(-4.0f, -8.0f, 10.0f, 8.0f, 4.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(56, 28).addBox(-5.0f, -11.0f, 9.0f, 10.0f, 7.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 62).addBox(7.0f, -10.0f, 1.0f, 1.0f, 5.0f, 5.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-8.0f, -10.0f, 1.0f, 1.0f, 5.0f, 5.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 2.0f, 0.0f)
            )

            val body = whole.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(0, 14)
                    .addBox(-5.0f, 0.0f, -3.0f, 10.0f, 4.0f, 9.0f, CubeDeformation(0.0f))
                    .texOffs(31, 27).addBox(-4.0f, -5.0f, -6.0f, 8.0f, 5.0f, 9.0f, CubeDeformation(0.0f))
                    .texOffs(52, 58).addBox(-5.0f, -4.0f, -5.0f, 1.0f, 4.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(56, 16).addBox(4.0f, -4.0f, -5.0f, 1.0f, 4.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(60, 0).addBox(-4.0f, -3.0f, -9.0f, 8.0f, 3.0f, 3.0f, CubeDeformation(0.0f))
                    .texOffs(56, 10).addBox(-4.0f, 0.0f, -11.0f, 8.0f, 2.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(32, 27).addBox(-1.0f, -2.0f, -1.0f, 2.0f, 2.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, -4.0f, 0.0f)
            )

            return LayerDefinition.create(meshdefinition, 128, 128)
        }
    }
}