package club.redux.sunset.lavafishing.client.renderer.entity

import club.redux.sunset.lavafishing.entity.EntityPromethiumBullet
import club.redux.sunset.lavafishing.registry.RegistryEntity
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers

class EntityRendererPromethiumBullet(
    context: EntityRendererProvider.Context,
) : EntityRenderer<EntityPromethiumBullet>(context) {

    private val textureLocation: ResourceLocation = ResourceLocation("minecraft", "textures/item/egg.png")

    override fun getTextureLocation(pEntity: EntityPromethiumBullet): ResourceLocation {
        return textureLocation
    }

    companion object {
        @JvmStatic
        fun onRegisterRenderers(event: RegisterRenderers) {
            event.registerEntityRenderer(RegistryEntity.PROMETHIUM_BULLET.get()) { EntityRendererPromethiumBullet(it) }
        }
    }
}