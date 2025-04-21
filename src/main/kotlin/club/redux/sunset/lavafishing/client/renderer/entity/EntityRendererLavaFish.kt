package club.redux.sunset.lavafishing.client.renderer.entity

import club.redux.sunset.lavafishing.client.model.*
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.misc.LavaFishType
import club.redux.sunset.lavafishing.misc.ModResourceLocation
import club.redux.sunset.lavafishing.util.getTexture
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
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
    ModelCommonFish(context.bakeLayer(ModelCommonFish.LAYER_LOCATION)),
    0.35f
) {
    private val commonModel = ModelCommonFish<EntityLavaFish>(context.bakeLayer(ModelCommonFish.LAYER_LOCATION))
    private val swordFishModel = ModelSwordFish<EntityLavaFish>(context.bakeLayer(ModelSwordFish.LAYER_LOCATION))
    private val eelModel = ModelEel<EntityLavaFish>(context.bakeLayer(ModelEel.LAYER_LOCATION))
    private val crabModel = ModelCrab<EntityLavaFish>(context.bakeLayer(ModelCrab.LAYER_LOCATION))
    private val snailModel = ModelSnail<EntityLavaFish>(context.bakeLayer(ModelSnail.LAYER_LOCATION))
    private val lobsterModel = ModelLobster<EntityLavaFish>(context.bakeLayer(ModelLobster.LAYER_LOCATION))

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
            LavaFishType.LOBSTER -> this.model = lobsterModel
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
                if (!fishEntity.isInLava) {
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
        val scale = when (fishEntity.fishType) {
            LavaFishType.CRAB, LavaFishType.EEL, LavaFishType.SNAIL -> 0.5f
            LavaFishType.LOBSTER -> 0.3f
            else -> 0f
        }

        if (scale > 0) {
            matrixStack.scale(scale, scale, scale)
        }
    }
}