package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.client.renderer.entity.state.RenderStateLavaFish
import club.redux.sunset.lavafishing.tool.bedrock.BedrockLoader
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.util.Mth
import net.neoforged.neoforge.client.event.EntityRenderersEvent


class ModelSwordFish(root: ModelPart) : EntityModel<RenderStateLavaFish>(root) {
    private val whole: ModelPart = root.getChild("whole")

    override fun setupAnim(state: RenderStateLavaFish) {
        resetPose()
        val movement = if (state.isInLava) 1.0f else 1.5f
        whole.getChild("tail").yRot = (-movement * 0.45 * Mth.sin(0.6 * state.ageInTicks)).toFloat()
    }

    companion object {
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(LavaFishing.identifier("sword_fish"), "main")

        private val GEOMETRY = LavaFishing.identifier("geo/sword_fish.geo.json")

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) { BedrockLoader.geometry(GEOMETRY) }
        }
    }
}
