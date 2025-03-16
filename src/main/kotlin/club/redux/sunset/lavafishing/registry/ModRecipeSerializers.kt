package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.item.recipe.RecipeDivisionTimes
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer

object ModRecipeSerializers : Registrar<RecipeSerializer<*>>(
    BuiltInRegistries.RECIPE_SERIALIZER,
    BuiltConstants.MOD_ID
) {
    val DIVISION_TIMES by this.register { SimpleCraftingRecipeSerializer(::RecipeDivisionTimes) }
}