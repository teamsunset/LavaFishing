package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraftforge.registries.ForgeRegistries

object ModEntityTypes {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.ENTITY_TYPES, BuildConstants.MOD_ID)

    @JvmField val BULLET = REGISTER.registerKt("bullet") {
        EntityType.Builder.of({ type, world -> EntityPromethiumBullet(type, world) }, MobCategory.MISC)
            .sized(0.5f, 0.5f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build(ResourceLocation(BuildConstants.MOD_ID, "bullet").toString())
    }

    @JvmField val PROMETHIUM_BULLET = REGISTER.registerKt("promethium_bullet") {
        EntityType.Builder.of({ type, world -> EntityPromethiumBullet(type, world) }, MobCategory.MISC)
            .sized(0.5f, 0.5f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build(ResourceLocation(BuildConstants.MOD_ID, "promethium_bullet").toString())
    }
}
