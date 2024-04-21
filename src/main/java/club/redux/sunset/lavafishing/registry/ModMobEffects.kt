package club.redux.sunset.lavafishing.registry

import club.asynclab.web.BuildConstants
import club.redux.sunset.lavafishing.effect.EffectBlessed
import club.redux.sunset.lavafishing.effect.EffectEndlessFlame
import club.redux.sunset.lavafishing.effect.EffectLavaWalker
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraftforge.registries.ForgeRegistries

object ModMobEffects {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.MOB_EFFECTS, BuildConstants.MOD_ID)

    @JvmField val BLESSED = REGISTER.registerKt("blessed") { EffectBlessed() }
    @JvmField val ENDLESS_FLAME = REGISTER.registerKt("endless_flame") { EffectEndlessFlame() }
    @JvmField val LAVA_WALKER = REGISTER.registerKt("lava_walker") { EffectLavaWalker() }
}
