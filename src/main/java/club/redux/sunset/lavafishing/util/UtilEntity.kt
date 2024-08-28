package club.redux.sunset.lavafishing.util

import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.common.extensions.IForgeEntity
import net.minecraftforge.fluids.FluidType
import net.minecraftforge.registries.ForgeRegistries

object UtilEntity {
    @JvmStatic
    fun isInFluid(entity: IForgeEntity, fluid: Fluid): Boolean = entity.isInFluidType(fluid.fluidType)

    @JvmStatic
    fun isInFluid(entity: IForgeEntity, fluidType: FluidType): Boolean = entity.isInFluidType(fluidType)

    @JvmStatic
    fun isInFluid(entity: IForgeEntity, fluidTag: TagKey<Fluid>): Boolean {
        return ForgeRegistries.FLUIDS.values
            .filter { ForgeRegistries.FLUIDS.getHolder(it).map { holder -> holder.`is`(fluidTag) }.orElse(false) }
            .map { it.fluidType }
            .any { entity.isInFluidType(it) }
    }

    @JvmStatic
    fun getTexture(entity: Entity): ResourceLocation? {
        val location = ForgeRegistries.ENTITY_TYPES.getKey(entity.type)
        return if (location != null) {
            ResourceLocation(location.namespace, "textures/entity/" + location.path + ".png")
        } else null
    }

    @JvmStatic
    fun getTexture(entity: Entity, alt: ResourceLocation): ResourceLocation = getTexture(entity) ?: alt
}

fun IForgeEntity.isInFluid(fluid: Fluid): Boolean = UtilEntity.isInFluid(this, fluid)
fun IForgeEntity.isInFluid(fluidType: FluidType): Boolean = UtilEntity.isInFluid(this, fluidType)
fun IForgeEntity.isInFluid(fluidTag: TagKey<Fluid>): Boolean = UtilEntity.isInFluid(this, fluidTag)
fun Entity.getTexture(): ResourceLocation? = UtilEntity.getTexture(this)
fun Entity.getTexture(alt: ResourceLocation): ResourceLocation = UtilEntity.getTexture(this, alt)