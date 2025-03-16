package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.effect.EffectEndlessFlame
import club.redux.sunset.lavafishing.effect.EffectLavaWalker
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.effect.MobEffect

object ModMobEffects : Registrar<MobEffect>(BuiltInRegistries.MOB_EFFECT, BuiltConstants.MOD_ID) {
    val ENDLESS_FLAME by this.register { EffectEndlessFlame() }
    val LAVA_WALKER by this.register { EffectLavaWalker() }
}
