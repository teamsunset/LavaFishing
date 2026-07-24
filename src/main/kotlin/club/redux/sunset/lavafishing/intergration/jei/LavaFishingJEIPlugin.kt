package club.redux.sunset.lavafishing.intergration.jei

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.item.recipe.RecipeDivisionTimes
import club.redux.sunset.lavafishing.util.Utils.identifier
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.constants.RecipeTypes
import mezz.jei.api.registration.IRecipeRegistration


@JeiPlugin
class LavaFishingJEIPlugin : IModPlugin {
    override fun getPluginUid() = BuiltConstants.MOD_ID.identifier("jei_support")

    override fun registerRecipes(registration: IRecipeRegistration) {
        registration.addRecipes(RecipeTypes.CRAFTING, RecipeDivisionTimes.createJeiRecipe())
    }
}