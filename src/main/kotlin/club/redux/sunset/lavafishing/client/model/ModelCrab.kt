package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.client.renderer.entity.state.RenderStateLavaFish
import club.redux.sunset.lavafishing.tool.bedrock.BedrockLoader
import net.minecraft.client.animation.KeyframeAnimation
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.neoforged.neoforge.client.event.EntityRenderersEvent


class ModelCrab(root: ModelPart) : EntityModel<RenderStateLavaFish>(root) {
    private val walkAnimation: KeyframeAnimation = BedrockLoader.animations(ANIMATION)["walk"].bake(root)

    override fun setupAnim(state: RenderStateLavaFish) {
        resetPose()
        walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 10.0f, Float.MAX_VALUE)
    }

    companion object {
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(LavaFishing.identifier("crab"), "main")

        private val GEOMETRY = LavaFishing.identifier("geo/crab.geo.json")
        private val ANIMATION = LavaFishing.identifier("animation/crab.animation.json")

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) { BedrockLoader.geometry(GEOMETRY) }
        }
    }
}
