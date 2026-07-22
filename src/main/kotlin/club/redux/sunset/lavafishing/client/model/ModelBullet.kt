package club.redux.sunset.lavafishing.client.model

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.client.renderer.entity.state.BulletRenderState
import club.redux.sunset.lavafishing.tool.bedrock.BedrockLoader
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.neoforged.neoforge.client.event.EntityRenderersEvent


class ModelBullet(
    root: ModelPart,
) : EntityModel<BulletRenderState>(root) {

    override fun setupAnim(state: BulletRenderState) = resetPose()

    companion object {
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(LavaFishing.identifier("bullet"), "main")

        private val GEOMETRY = LavaFishing.identifier("geo/bullet.geo.json")

        fun onRegisterLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
            event.registerLayerDefinition(LAYER_LOCATION) { BedrockLoader.geometry(GEOMETRY) }
        }
    }
}
