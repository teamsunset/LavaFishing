package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityNeptuniumBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.item.Tiers
import net.minecraft.world.level.Level
import net.neoforged.neoforge.registries.DeferredHolder
import kotlin.reflect.full.isSubclassOf

object ModEntityTypes {
    @JvmField val REGISTER = UtilRegister.create(BuiltInRegistries.ENTITY_TYPE, BuildConstants.MOD_ID)

    @JvmField
    val TYPE_MAP: MutableMap<DeferredHolder<EntityType<*>, out EntityType<*>>, Class<out Entity>> = mutableMapOf()

    // EntityTypes
    @JvmField
    val STONE_BULLET = registerBullet("stone_bullet") { entityType: EntityType<EntityBullet>, level: Level ->
        EntityBullet(entityType, level, Tiers.STONE)
    }

    @JvmField val IRON_BULLET = registerBullet("iron_bullet") { entityType: EntityType<EntityBullet>, level: Level ->
        EntityBullet(entityType, level, Tiers.IRON)
    }

    @JvmField val NEPTUNIUM_BULLET = registerBullet("neptunium_bullet", ::EntityNeptuniumBullet)

    @JvmField val PROMETHIUM_BULLET = registerBullet("promethium_bullet", ::EntityPromethiumBullet)

    @Suppress("UNCHECKED_CAST")
    fun <T : Entity> getEntriesByEntityParentClass(clazz: Class<T>): List<DeferredHolder<EntityType<*>, EntityType<T>>> {
        return REGISTER.entries.filter {
            TYPE_MAP[it]?.kotlin?.isSubclassOf(clazz.kotlin) ?: false
        } as List<DeferredHolder<EntityType<*>, EntityType<T>>>
    }

    inline fun <reified T : Entity> register(
        name: String,
        noinline supplier: () -> EntityType<T>,
    ): DeferredHolder<EntityType<*>, EntityType<T>> =
        REGISTER.registerKt(name, supplier).also { TYPE_MAP[it] = T::class.java }

    private inline fun <reified T : EntityBullet> registerBullet(
        name: String,
        noinline constructor: (EntityType<T>, Level) -> T,
    ): DeferredHolder<EntityType<*>, EntityType<T>> {
        return register(name) {
            EntityType.Builder.of(constructor, MobCategory.MISC)
                .sized(0.5f, 0.5f)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build(LavaFishing.resourceLocation(name).toString())
        }
    }
}
