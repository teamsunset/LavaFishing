package club.redux.sunset.lavafishing.client.renderer

import club.redux.sunset.lavafishing.client.renderlayer.RenderLayerJellyfish
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.util.isInFluid
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import com.teammetallurgy.aquaculture.Aquaculture
import com.teammetallurgy.aquaculture.client.ClientHandler
import com.teammetallurgy.aquaculture.client.renderer.entity.model.*
import com.teammetallurgy.aquaculture.entity.FishType
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.TropicalFishModelB
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraftforge.registries.ForgeRegistries

class EntityRendererLavaFish(
    context: EntityRendererProvider.Context,
    isJellyfish: Boolean,
) : MobRenderer<EntityLavaFish, EntityModel<EntityLavaFish>>(
    context,
    FishMediumModel(context.bakeLayer(ClientHandler.MEDIUM_MODEL)),
    0.35f
) {
    private val tropicalFishBModel =
        TropicalFishModelB<EntityLavaFish>(context.bakeLayer(ModelLayers.TROPICAL_FISH_LARGE))
    private val smallModel = FishSmallModel<EntityLavaFish>(context.bakeLayer(ClientHandler.SMALL_MODEL))
    private val mediumModel = FishMediumModel<EntityLavaFish>(context.bakeLayer(ClientHandler.MEDIUM_MODEL))
    private val largeModel = FishLargeModel<EntityLavaFish>(context.bakeLayer(ClientHandler.LARGE_MODEL))
    private val longnoseModel = FishLongnoseModel<EntityLavaFish>(context.bakeLayer(ClientHandler.LONGNOSE_MODEL))
    private val catfishModel = FishCathfishModel<EntityLavaFish>(context.bakeLayer(ClientHandler.CATFISH_MODEL))
    private val jellyfishModel = JellyfishModel<EntityLavaFish>(context.bakeLayer(ClientHandler.JELLYFISH_MODEL))

    init {
        if (isJellyfish) {
            this.addLayer(RenderLayerJellyfish(this, context.modelSet))
        }
    }

    override fun render(
        fishEntity: EntityLavaFish,
        entityYaw: Float,
        partialTicks: Float,
        matrixStack: PoseStack,
        buffer: MultiBufferSource,
        i: Int,
    ) {
        when (fishEntity.fishType) {
            FishType.SMALL -> this.model = smallModel
            FishType.LARGE -> this.model = largeModel
            FishType.LONGNOSE -> this.model = longnoseModel
            FishType.CATFISH -> this.model = catfishModel
            FishType.JELLYFISH -> this.model = jellyfishModel
            FishType.HALIBUT -> this.model = tropicalFishBModel
            else -> this.model = mediumModel
        }
        super.render(fishEntity, entityYaw, partialTicks, matrixStack, buffer, i)
    }

    override fun getTextureLocation(fishEntity: EntityLavaFish): ResourceLocation {
        val location = ForgeRegistries.ENTITY_TYPES.getKey(fishEntity.type)
        if (location != null) {
            return ResourceLocation(Aquaculture.MOD_ID, "textures/entity/fish/" + location.path + ".png")
        }
        return DEFAULT_LOCATION
    }

    override fun setupRotations(
        fishEntity: EntityLavaFish,
        matrixStack: PoseStack,
        ageInTicks: Float,
        rotationYaw: Float,
        partialTicks: Float,
    ) {
        super.setupRotations(fishEntity, matrixStack, ageInTicks, rotationYaw, partialTicks)
        val fishType = fishEntity.fishType
        if (fishType != FishType.JELLYFISH) {
            var salmonRotation = 1.0f
            var salmonMultiplier = 1.0f
            if (fishType == FishType.LONGNOSE) {
                if (!fishEntity.isInWater) {
                    salmonRotation = 1.3f
                    salmonMultiplier = 1.7f
                }
            }
            val fishRotation =
                if (fishType == FishType.LONGNOSE) salmonRotation * 4.3f * Mth.sin(salmonMultiplier * 0.6f * ageInTicks) else 4.3f * Mth.sin(
                    0.6f * ageInTicks
                )

            matrixStack.mulPose(Axis.YP.rotationDegrees(fishRotation))
            if (fishType == FishType.LONGNOSE) {
                matrixStack.translate(0.0f, 0.0f, -0.4f)
            }
            if (!EntityLavaFish.acceptedFluids.any { fishEntity.isInFluid(it) } && fishType != FishType.HALIBUT) {
                if (fishType == FishType.MEDIUM || fishType == FishType.LARGE || fishType == FishType.CATFISH) {
                    matrixStack.translate(0.1f, 0.1f, -0.1f)
                } else {
                    matrixStack.translate(0.2f, 0.1f, 0.0f)
                }
                matrixStack.mulPose(Axis.ZP.rotationDegrees(90f))
            }
            if (fishType == FishType.HALIBUT) {
                matrixStack.translate(-0.4f, 0.1f, 0.0f)
                matrixStack.mulPose(Axis.ZP.rotationDegrees(-90f))
            }
        }
    }

    override fun scale(fishEntity: EntityLavaFish, matrixStack: PoseStack, partialTickTime: Float) {
        val location = ForgeRegistries.ENTITY_TYPES.getKey(fishEntity.type)
        var scale = 0.0f
        if (location != null) {
            when (location.path) {
                "minnow" -> scale = 0.5f
                "synodontis" -> scale = 0.8f
                "brown_trout", "piranha" -> scale = 0.9f
                "pollock" -> scale = 1.1f
                "atlantic_cod", "blackfish", "catfish", "tambaqui" -> scale = 1.2f
                "pacific_halibut", "atlantic_halibut", "capitaine", "largemouth_bass", "gar", "arapaima", "tuna" -> scale =
                    1.4f
            }
        }
        if (scale > 0) {
            matrixStack.pushPose()
            matrixStack.scale(scale, scale, scale)
            matrixStack.popPose()
        }
    }

    companion object {
        private val DEFAULT_LOCATION = ResourceLocation(Aquaculture.MOD_ID, "textures/entity/fish/atlantic_cod.png")
    }
}