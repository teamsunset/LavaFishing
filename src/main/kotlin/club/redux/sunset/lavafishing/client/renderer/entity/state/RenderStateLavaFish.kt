package club.redux.sunset.lavafishing.client.renderer.entity.state

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.misc.LavaFishType
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState

class RenderStateLavaFish : LivingEntityRenderState() {
    var fishType = LavaFishType.COMMON
    var isInLava = false
    var onGround = false
    var texture = DEFAULT_TEXTURE

    companion object {
        val DEFAULT_TEXTURE = LavaFishing.identifier("textures/entity/fish/default.png")
    }
}
