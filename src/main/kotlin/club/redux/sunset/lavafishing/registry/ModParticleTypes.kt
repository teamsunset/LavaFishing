package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.core.registries.BuiltInRegistries

object ModParticleTypes : Registrar<ParticleType<*>>(BuiltInRegistries.PARTICLE_TYPE, BuiltConstants.MOD_ID) {
    val FIRE_PUNCH by this.register { SimpleParticleType(false) }
}
