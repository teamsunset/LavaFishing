package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityNeptuniumBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.misc.ModResourceLocation
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.Level
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object ModEntityTypes {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.ENTITY_TYPES, BuildConstants.MOD_ID)

    @JvmField val BULLET_ENTITY_TYPES: MutableList<RegistryObject<out EntityType<out EntityBullet>>> = mutableListOf()

    @JvmField val STONE_BULLET = registerBullet("stone_bullet", ::EntityBullet)

    @JvmField val IRON_BULLET = registerBullet("iron_bullet", ::EntityBullet)

    @JvmField val NEPTUNIUM_BULLET = registerBullet("neptunium_bullet", ::EntityNeptuniumBullet)

    @JvmField val PROMETHIUM_BULLET = registerBullet("promethium_bullet", ::EntityPromethiumBullet)

    private fun <T : EntityBullet> registerBullet(
        name: String,
        constructor: (EntityType<T>, Level) -> T,
    ): RegistryObject<EntityType<T>> {
        return REGISTER.registerKt(name) {
            EntityType.Builder.of(constructor, MobCategory.MISC)
                .sized(0.5f, 0.5f)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build(ModResourceLocation(name).toString())
        }.apply { BULLET_ENTITY_TYPES.add(this) }
    }
}
