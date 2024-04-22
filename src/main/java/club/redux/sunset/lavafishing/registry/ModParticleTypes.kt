package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.registries.ForgeRegistries

object ModParticleTypes {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.PARTICLE_TYPES, BuildConstants.MOD_ID)

    @JvmField val FIRE_PUNCH = REGISTER.registerKt("fire_punch") { SimpleParticleType(false) }
}