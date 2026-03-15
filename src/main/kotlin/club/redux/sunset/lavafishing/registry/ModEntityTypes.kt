package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityNeptuniumBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.item.Tiers
import net.minecraft.world.level.Level
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object ModEntityTypes : Registrar<EntityType<*>>(ForgeRegistries.ENTITY_TYPES, BuildConstants.MOD_ID) {
    private val typeMap = mutableMapOf<RegistryObject<EntityType<*>>, Class<out Entity>>()

    val STONE_BULLET by this.registerBullet { entityType, level -> EntityBullet(entityType, level, Tiers.STONE) }
    val IRON_BULLET by this.registerBullet { entityType, level -> EntityBullet(entityType, level, Tiers.IRON) }
    val NEPTUNIUM_BULLET by this.registerBullet(::EntityNeptuniumBullet)
    val PROMETHIUM_BULLET by this.registerBullet(::EntityPromethiumBullet)

    @Suppress("UNCHECKED_CAST")
    fun <T : Entity> getEntriesByEntityParentClass(entityClass: Class<T>): List<RegistryObject<EntityType<T>>> {
        return this.getHolders()
            .filter { holder -> typeMap[holder]?.let(entityClass::isAssignableFrom) == true }
            .map { it as RegistryObject<EntityType<T>> }
    }

    inline fun <reified T : Entity> getEntriesByEntityParentClass(): List<RegistryObject<EntityType<T>>> {
        return this.getEntriesByEntityParentClass(T::class.java)
    }

    fun <T : Entity> register(name: String, entityClass: Class<T>, supplier: () -> EntityType<T>): RegistryObject<EntityType<T>> {
        return this.register(name, supplier).also { typeMap[it as RegistryObject<EntityType<*>>] = entityClass }
    }

    private inline fun <reified T : EntityBullet> registerBullet(
        noinline constructor: (EntityType<T>, Level) -> T,
    ) = this.register {
        EntityType.Builder.of(constructor, MobCategory.MISC)
            .sized(0.2f, 0.2f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build(LavaFishing.resourceLocation(it).toString())
    }.post { typeMap[it as RegistryObject<EntityType<*>>] = T::class.java }
}
