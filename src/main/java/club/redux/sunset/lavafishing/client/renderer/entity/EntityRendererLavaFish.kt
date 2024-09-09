package club.redux.sunset.lavafishing.client.renderer.entity

import club.redux.sunset.lavafishing.client.model.ModelCrab
import club.redux.sunset.lavafishing.client.model.ModelFishTest
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.misc.LavaFishType
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
    private val crabModel = ModelCrab<EntityLavaFish>(context.bakeLayer(ModelCrab.LAYER_LOCATION))
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
            LavaFishType.SWORDFISH -> this.model = swordFishModel
            LavaFishType.EEL -> this.model = eelModel
            LavaFishType.CRAB -> this.model = crabModel
            LavaFishType.SNAIL -> this.model = snailModel
            else -> this.model = commonModel
        }
        super.render(fishEntity, entityYaw, partialTicks, matrixStack, buffer, i)
    }

    override fun getTextureLocation(fish: EntityLavaFish): ResourceLocation {
        return fish.getTexture(ModResourceLocation("textures/entity/fish/default.png"))
    }

    override fun setupRotations(
        fishEntity: EntityLavaFish,
        matrixStack: PoseStack,
        ageInTicks: Float,
        rotationYaw: Float,
        partialTicks: Float,
    ) {
        super.setupRotations(fishEntity, matrixStack, ageInTicks, rotationYaw, partialTicks)

        when (fishEntity.fishType) {
            LavaFishType.COMMON, LavaFishType.SWORDFISH -> {
                val fishRotation = 4.3f * Mth.sin(0.6f * ageInTicks)

                matrixStack.mulPose(Axis.YP.rotationDegrees(fishRotation))
                if (!EntityLavaFish.acceptedFluids.any { fishEntity.isInFluid(it) }) {
                    matrixStack.translate(0.1f, 0.1f, -0.1f)
                    matrixStack.mulPose(Axis.ZP.rotationDegrees(90f))
                }
            }

            LavaFishType.CRAB -> {
                matrixStack.mulPose(Axis.YP.rotationDegrees(90f))
            }

            else -> Unit
        }
    }

    override fun scale(fishEntity: EntityLavaFish, matrixStack: PoseStack, partialTickTime: Float) {
//        val location = ForgeRegistries.ENTITY_TYPES.getKey(fishEntity.type)
        var scale = 0.0f
//        if (location != null) {
//            when (location.path) {
//                "minnow" -> scale = 0.5f
//                "synodontis" -> scale = 0.8f
//                "brown_trout", "piranha" -> scale = 0.9f
//                "pollock" -> scale = 1.1f
//                "atlantic_cod", "blackfish", "catfish", "tambaqui" -> scale = 1.2f
//                "pacific_halibut", "atlantic_halibut", "capitaine", "largemouth_bass", "gar", "arapaima", "tuna" -> scale =
//                    1.4f
//            }
//        }
        if (fishEntity.fishType == LavaFishType.CRAB) {
            scale = 0.5f
        }

        if (scale > 0) {
            matrixStack.scale(scale, scale, scale)
        }
    }
}