package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityNeptuniumBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.misc.ModResourceLocation
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.Level
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import kotlin.reflect.full.isSubclassOf

object ModEntityTypes {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.ENTITY_TYPES, BuildConstants.MOD_ID)

    @JvmField val TYPE_MAP: MutableMap<RegistryObject<out EntityType<out Entity>>, Class<out Entity>> = mutableMapOf()

    // EntityTypes
    @JvmField
    val STONE_BULLET = registerBullet("stone_bullet", ::EntityBullet)

    @JvmField val IRON_BULLET = registerBullet("iron_bullet", ::EntityBullet)

    @JvmField val NEPTUNIUM_BULLET = registerBullet("neptunium_bullet", ::EntityNeptuniumBullet)

    @JvmField val PROMETHIUM_BULLET = registerBullet("promethium_bullet", ::EntityPromethiumBullet)

    @Suppress("UNCHECKED_CAST")
    fun <T : Entity> getEntriesByEntityParentClass(clazz: Class<T>): List<RegistryObject<EntityType<T>>> {
        return REGISTER.entries.filter {
            TYPE_MAP[it]?.kotlin?.isSubclassOf(clazz.kotlin) ?: false
        } as List<RegistryObject<EntityType<T>>>
    }

    inline fun <reified T : Entity> register(
        name: String,
        noinline supplier: () -> EntityType<T>,
    ): RegistryObject<EntityType<T>> = REGISTER.registerKt(name, supplier).also { TYPE_MAP[it] = T::class.java }

    private inline fun <reified T : EntityBullet> registerBullet(
        name: String,
        noinline constructor: (EntityType<T>, Level) -> T,
    ): RegistryObject<EntityType<T>> {
        return register(name) {
            EntityType.Builder.of(constructor, MobCategory.MISC)
                .sized(0.5f, 0.5f)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build(ModResourceLocation(name).toString())
        }
    }
}
