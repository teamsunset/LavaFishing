package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.potion.PotionLavaWalker
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.alchemy.Potions
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent


object ModPotions {
    @JvmField val REGISTER = UtilRegister.create(BuiltInRegistries.POTION, BuildConstants.MOD_ID)

    @JvmField val LAVA_WALKER = REGISTER.registerKt("lava_walker") { PotionLavaWalker() }

    @JvmStatic
    fun onRegisterBrewingRecipes(event: RegisterBrewingRecipesEvent) {
        event.builder.addMix(Potions.AWKWARD, ModItems.AROWANA_FISH.get(), Potions.LUCK)
        event.builder.addMix(Potions.AWKWARD, ModItems.FLAME_SQUAT_LOBSTER.get(), Potions.FIRE_RESISTANCE)
        event.builder.addMix(Potions.AWKWARD, ModItems.STEAM_FLYING_FISH.get(), LAVA_WALKER)
    }
}