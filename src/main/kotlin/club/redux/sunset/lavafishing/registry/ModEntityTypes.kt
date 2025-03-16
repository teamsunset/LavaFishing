package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityNeptuniumBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.item.Tiers
import net.minecraft.world.level.Level
import net.neoforged.neoforge.registries.DeferredHolder
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

object ModEntityTypes : Registrar<EntityType<*>>(BuiltInRegistries.ENTITY_TYPE, BuiltConstants.MOD_ID) {
    val TYPE_MAP: MutableMap<DeferredHolder<EntityType<*>, out EntityType<*>>, KClass<out Entity>> = mutableMapOf()

    // EntityTypes
    val STONE_BULLET by this.registerBullet { entityType, level -> EntityBullet(entityType, level, Tiers.STONE) }
    val IRON_BULLET by this.registerBullet { entityType, level -> EntityBullet(entityType, level, Tiers.IRON) }
    val NEPTUNIUM_BULLET by this.registerBullet(::EntityNeptuniumBullet)
    val PROMETHIUM_BULLET by this.registerBullet(::EntityPromethiumBullet)

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Entity> getHoldersByEntityClass() = this.getHolders().filter {
        TYPE_MAP[it]?.isSubclassOf(T::class) ?: false
    } as List<DeferredHolder<EntityType<*>, EntityType<T>>>

    inline fun <reified T : Entity> getEntitiesByEntityClass() = this.getHoldersByEntityClass<T>().map { it.get() }

    private inline fun <reified T : Entity> registerWithMap(
        noinline supplier: (String) -> EntityType<T>,
    ) = this.register(supplier).post { TYPE_MAP[it] = T::class }

    inline fun <reified T : Entity> registerWithMap(
        name: String,
        noinline supplier: () -> EntityType<T>,
    ) = this.register(name, supplier).also { TYPE_MAP[it] = T::class }

    private inline fun <reified T : EntityBullet> registerBullet(
        noinline constructor: (EntityType<T>, Level) -> T,
    ): Delegator<EntityType<T>> {
        return this.registerWithMap {
            EntityType.Builder.of(constructor, MobCategory.MISC)
                .sized(0.5f, 0.5f)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build(LavaFishing.resourceLocation(it).toString())
        }
    }
}
