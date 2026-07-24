package club.redux.sunset.lavafishing.client.renderer.entity.state

import club.redux.sunset.lavafishing.LavaFishing
import net.minecraft.client.renderer.entity.state.EntityRenderState

class RenderStateBullet : EntityRenderState() {
    var xRot = 0.0f
    var yRot = 0.0f
    var texture = DEFAULT_TEXTURE

    companion object {
        val DEFAULT_TEXTURE = LavaFishing.identifier("textures/entity/bullet/default_bullet.png")
    }
}
