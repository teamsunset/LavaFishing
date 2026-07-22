package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.item.recipe.RecipeDivisionTimes
import club.redux.sunset.lavafishing.tool.registry.Registrar
import com.mojang.serialization.MapCodec
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.crafting.RecipeSerializer

object ModRecipeSerializers : Registrar<RecipeSerializer<*>>(
    BuiltInRegistries.RECIPE_SERIALIZER,
    BuiltConstants.MOD_ID
) {
    val DIVISION_TIMES by this.register {
        RecipeSerializer(
            MapCodec.unit(::RecipeDivisionTimes),
            StreamCodec.of<RegistryFriendlyByteBuf, RecipeDivisionTimes>(
                { _, _ -> },
                { RecipeDivisionTimes() },
            ),
        )
    }
}
