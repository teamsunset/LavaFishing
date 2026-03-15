package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.effect.EffectEndlessFlame
import club.redux.sunset.lavafishing.effect.EffectLavaWalker
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.world.effect.MobEffect
import net.minecraftforge.registries.ForgeRegistries

object ModMobEffects : Registrar<MobEffect>(ForgeRegistries.MOB_EFFECTS, BuiltConstants.MOD_ID) {
    val ENDLESS_FLAME by this.register { EffectEndlessFlame() }
    val LAVA_WALKER by this.register { EffectLavaWalker() }
}
