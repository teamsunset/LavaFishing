package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.potion.PotionLavaWalker
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraftforge.registries.ForgeRegistries

object ModPotions {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.POTIONS, BuildConstants.MOD_ID)

    @JvmField val LAVA_WALKER = REGISTER.registerKt("lava_walker") { PotionLavaWalker() }
}