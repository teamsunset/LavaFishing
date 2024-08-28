package club.redux.sunset.lavafishing.client.renderer.entity

import club.redux.sunset.lavafishing.client.model.ModelFishTest
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.misc.ModResourceLocation
import club.redux.sunset.lavafishing.util.getTexture
import club.redux.sunset.lavafishing.util.isInFluid
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import com.teammetallurgy.aquaculture.client.ClientHandler
import com.teammetallurgy.aquaculture.client.renderer.entity.model.FishMediumModel
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth

class EntityRendererLavaFish(
    context: EntityRendererProvider.Context,
) : MobRenderer<EntityLavaFish, EntityModel<EntityLavaFish>>(
    context,
    FishMediumModel(context.bakeLayer(ClientHandler.MEDIUM_MODEL)),
    0.35f
) {
    private val commonModel = ModelFishTest<EntityLavaFish>(context.bakeLayer(ModelFishTest.LAYER_LOCATION))
    private val swordFishModel = ModelFishTest<EntityLavaFish>(context.bakeLayer(ModelFishTest.LAYER_LOCATION))
    private val eelModel = ModelFishTest<EntityLavaFish>(context.bakeLayer(ModelFishTest.LAYER_LOCATION))
    private val crabModel = ModelFishTest<EntityLavaFish>(context.bakeLayer(ModelFishTest.LAYER_LOCATION))
    private val snailModel = ModelFishTest<EntityLavaFish>(context.bakeLayer(ModelFishTest.LAYER_LOCATION))

    override fun render(
        fishEntity: EntityLavaFish,
        entityYaw: Float,
        partialTicks: Float,
        matrixStack: PoseStack,
        buffer: MultiBufferSource,
        i: Int,
    ) {
        when (fishEntity.fishType) {
            EntityLavaFish.Companion.FishType.SWORDFISH -> this.model = swordFishModel
            EntityLavaFish.Companion.FishType.EEL -> this.model = eelModel
            else -> this.model = commonModel
        }
        this.model = commonModel
        super.render(fishEntity, entityYaw, partialTicks, matrixStack, buffer, i)
    }

    override fun getTextureLocation(fish: EntityLavaFish): ResourceLocation {
        return fish.getTexture(ModResourceLocation("textures/entity/fish/atlantic_cod.png"))
    }

    override fun setupRotations(
        fishEntity: EntityLavaFish,
        matrixStack: PoseStack,
        ageInTicks: Float,
        rotationYaw: Float,
        partialTicks: Float,
    ) {
        super.setupRotations(fishEntity, matrixStack, ageInTicks, rotationYaw, partialTicks)
        val fishRotation = 4.3f * Mth.sin(0.6f * ageInTicks)

        matrixStack.mulPose(Axis.YP.rotationDegrees(fishRotation))
        if (!EntityLavaFish.acceptedFluids.any { fishEntity.isInFluid(it) }) {
            matrixStack.translate(0.1f, 0.1f, -0.1f)
            matrixStack.mulPose(Axis.ZP.rotationDegrees(90f))
        }
    }

    override fun scale(fishEntity: EntityLavaFish, matrixStack: PoseStack, partialTickTime: Float) {
    }
}