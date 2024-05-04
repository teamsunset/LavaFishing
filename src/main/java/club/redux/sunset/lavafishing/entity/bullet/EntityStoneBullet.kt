package club.redux.sunset.lavafishing.entity.bullet

import club.redux.sunset.lavafishing.misc.ModResourceLocation
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class EntityStoneBullet(
    entityType: EntityType<out EntityStoneBullet>,
    world: Level,
) : EntityBullet(entityType, world) {
    override fun getTextureLocation(): ResourceLocation = ModResourceLocation("textures/entity/bullet/stone_bullet.png")
}