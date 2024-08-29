package club.redux.sunset.lavafishing.util

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.material.Fluid
import net.neoforged.neoforge.common.extensions.IEntityExtension
import net.neoforged.neoforge.fluids.FluidType

object UtilEntity {
    @JvmStatic
    fun isInFluid(entity: IEntityExtension, fluid: Fluid): Boolean = entity.isInFluidType(fluid.fluidType)

    @JvmStatic
    fun isInFluid(entity: IEntityExtension, fluidType: FluidType): Boolean = entity.isInFluidType(fluidType)

    @JvmStatic
    fun isInFluid(entity: IEntityExtension, fluidTag: TagKey<Fluid>): Boolean {
        return BuiltInRegistries.FLUID
            .filter {
                BuiltInRegistries.FLUID.getHolder(BuiltInRegistries.FLUID.getKey(it))
                    .map { holder -> holder.`is`(fluidTag) }
                    .orElse(false)
            }
            .map { it.fluidType }
            .any { entity.isInFluidType(it) }
    }

    @JvmStatic
    fun getTexture(entity: Entity): ResourceLocation? {
        val location = BuiltInRegistries.ENTITY_TYPE.getKey(entity.type)
        return if (location != BuiltInRegistries.ENTITY_TYPE.defaultKey) {
            ResourceLocation.fromNamespaceAndPath(location.namespace, "textures/entity/" + location.path + ".png")
        } else null
    }

    @JvmStatic
    fun getTexture(entity: Entity, alt: ResourceLocation): ResourceLocation = getTexture(entity) ?: alt
}

fun IEntityExtension.isInFluid(fluid: Fluid): Boolean = UtilEntity.isInFluid(this, fluid)
fun IEntityExtension.isInFluid(fluidType: FluidType): Boolean = UtilEntity.isInFluid(this, fluidType)
fun IEntityExtension.isInFluid(fluidTag: TagKey<Fluid>): Boolean = UtilEntity.isInFluid(this, fluidTag)
fun Entity.getTexture(): ResourceLocation? = UtilEntity.getTexture(this)
fun Entity.getTexture(alt: ResourceLocation): ResourceLocation = UtilEntity.getTexture(this, alt)