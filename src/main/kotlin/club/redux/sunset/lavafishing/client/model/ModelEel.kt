package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.client.renderer.entity.state.LavaFishRenderState
import club.redux.sunset.lavafishing.tool.bedrock.BedrockLoader
import net.minecraft.client.animation.KeyframeAnimation
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.neoforged.neoforge.client.event.EntityRenderersEvent


class ModelEel(root: ModelPart) : EntityModel<LavaFishRenderState>(root) {
    private val swimAnimation: KeyframeAnimation = BedrockLoader.animations(ANIMATION)["swim"].bake(root)

    override fun setupAnim(state: LavaFishRenderState) {
        resetPose()
        swimAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 5.0f, Float.MAX_VALUE)
    }

    companion object {
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(LavaFishing.identifier("eel"), "main")

        private val GEOMETRY = LavaFishing.identifier("geo/eel.geo.json")
        private val ANIMATION = LavaFishing.identifier("animation/eel.animation.json")

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) { BedrockLoader.geometry(GEOMETRY) }
        }
    }
}
