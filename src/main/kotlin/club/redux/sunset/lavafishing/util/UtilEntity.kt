package club.redux.sunset.lavafishing.util

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.material.Fluid
import net.neoforged.neoforge.common.extensions.IEntityExtension
import net.neoforged.neoforge.fluids.FluidType

object UtilEntity {
    fun IEntityExtension.isInFluid(fluid: Fluid): Boolean = this.isInFluidType(fluid.fluidType)
    fun IEntityExtension.isInFluid(fluidType: FluidType): Boolean = this.isInFluidType(fluidType)

    fun IEntityExtension.isInFluid(fluidTag: TagKey<Fluid>): Boolean {
        return BuiltInRegistries.FLUID
            .filter {
                BuiltInRegistries.FLUID.getHolder(BuiltInRegistries.FLUID.getKey(it))
                    .map { holder -> holder.`is`(fluidTag) }
                    .orElse(false)
            }
            .map { it.fluidType }
            .any { this.isInFluidType(it) }
    }

    fun Entity.getTexture(): ResourceLocation? {
        val location = BuiltInRegistries.ENTITY_TYPE.getKey(this.type)
        return if (location != BuiltInRegistries.ENTITY_TYPE.defaultKey) {
            ResourceLocation.fromNamespaceAndPath(location.namespace, "textures/entity/" + location.path + ".png")
        } else null
    }

    fun Entity.getTexture(alt: ResourceLocation): ResourceLocation = this.getTexture() ?: alt
}
