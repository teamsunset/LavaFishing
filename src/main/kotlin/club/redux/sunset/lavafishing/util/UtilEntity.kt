package club.redux.sunset.lavafishing.util

import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.common.extensions.IForgeEntity
import net.minecraftforge.fluids.FluidType
import net.minecraftforge.registries.ForgeRegistries

fun IForgeEntity.isInFluid(fluid: Fluid): Boolean = this.isInFluidType(fluid.fluidType)

fun IForgeEntity.isInFluid(fluidType: FluidType): Boolean = this.isInFluidType(fluidType)

fun IForgeEntity.isInFluid(fluidTag: TagKey<Fluid>): Boolean {
    return ForgeRegistries.FLUIDS.values
        .filter { ForgeRegistries.FLUIDS.getHolder(it).map { holder -> holder.`is`(fluidTag) }.orElse(false) }
        .map { it.fluidType }
        .any { this.isInFluidType(it) }
}

fun Entity.getTexture(): ResourceLocation? {
    val location = ForgeRegistries.ENTITY_TYPES.getKey(this.type)
    return if (location != null) {
        ResourceLocation(location.namespace, "textures/entity/" + location.path + ".png")
    } else null
}

fun Entity.getTexture(alt: ResourceLocation): ResourceLocation = this.getTexture() ?: alt
