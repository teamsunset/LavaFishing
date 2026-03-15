package club.redux.sunset.lavafishing.intergration.jei

import club.redux.sunset.lavafishing.item.recipe.RecipeDivisionTimes
import club.redux.sunset.lavafishing.misc.ModResourceLocation
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.constants.RecipeTypes
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.resources.ResourceLocation

@JeiPlugin
class LavaFishingJEIPlugin : IModPlugin {
    override fun getPluginUid(): ResourceLocation = ModResourceLocation("jei_support")

    override fun registerRecipes(registration: IRecipeRegistration) {
        registration.addRecipes(RecipeTypes.CRAFTING, RecipeDivisionTimes.createJeiRecipe())
    }
}
