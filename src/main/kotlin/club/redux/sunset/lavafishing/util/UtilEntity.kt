package club.redux.sunset.lavafishing.util

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import net.minecraft.world.entity.Entity

object UtilEntity {
    fun Entity.getTexture(): Identifier? {
        val location = BuiltInRegistries.ENTITY_TYPE.getKey(this.type)
        return if (location != BuiltInRegistries.ENTITY_TYPE.defaultKey) {
            Identifier.fromNamespaceAndPath(location.namespace, "textures/entity/" + location.path + ".png")
        } else null
    }

    fun Entity.getTexture(alt: Identifier): Identifier = this.getTexture() ?: alt
}
