package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.Potions
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent


object ModPotions : Registrar<Potion>(BuiltInRegistries.POTION, BuiltConstants.MOD_ID) {
    val LAVA_WALKER by this.register { Potion(MobEffectInstance(ModMobEffects.LAVA_WALKER, 4800)) }

    fun onRegisterBrewingRecipes(event: RegisterBrewingRecipesEvent) {
        event.builder.apply {
            addMix(Potions.AWKWARD, ModItems.AROWANA_FISH.get(), Potions.LUCK)
            addMix(Potions.AWKWARD, ModItems.FLAME_SQUAT_LOBSTER.get(), Potions.FIRE_RESISTANCE)
            addMix(Potions.AWKWARD, ModItems.STEAM_FLYING_FISH.get(), LAVA_WALKER)
        }
    }
}