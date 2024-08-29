package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.effect.EffectEndlessFlame
import club.redux.sunset.lavafishing.effect.EffectLavaWalker
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.core.registries.BuiltInRegistries

object ModMobEffects {
    @JvmField val REGISTER = UtilRegister.create(BuiltInRegistries.MOB_EFFECT, BuildConstants.MOD_ID)

    //    @JvmField val BLESSED = REGISTER.registerKt("blessed") { EffectBlessed() }
    @JvmField val ENDLESS_FLAME = REGISTER.registerKt("endless_flame") { EffectEndlessFlame() }
    @JvmField val LAVA_WALKER = REGISTER.registerKt("lava_walker") { EffectLavaWalker() }
}
