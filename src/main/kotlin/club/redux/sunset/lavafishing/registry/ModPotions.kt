package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.potion.PotionLavaWalker
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionUtils
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.common.brewing.BrewingRecipe
import net.minecraftforge.common.brewing.BrewingRecipeRegistry
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.registries.ForgeRegistries


object ModPotions {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.POTIONS, BuildConstants.MOD_ID)

    @JvmField val LAVA_WALKER = REGISTER.registerKt("lava_walker") { PotionLavaWalker() }

    @JvmStatic
    fun onCommonSetupEvent(event: FMLCommonSetupEvent) {
        val createBrewingRecipe = { input: Potion, ingredient: Item, output: Potion ->
            BrewingRecipe(
                Ingredient.of(PotionUtils.setPotion(ItemStack(Items.POTION), input)),
                Ingredient.of(ingredient),
                PotionUtils.setPotion(ItemStack(Items.POTION), output)
            )
        }

        val register = { recipe: BrewingRecipe -> event.enqueueWork { BrewingRecipeRegistry.addRecipe(recipe) } }

        register(createBrewingRecipe(Potions.AWKWARD, ModItems.AROWANA_FISH.get(), Potions.LUCK))
        register(createBrewingRecipe(Potions.AWKWARD, ModItems.FLAME_SQUAT_LOBSTER.get(), Potions.FIRE_RESISTANCE))
        register(createBrewingRecipe(Potions.AWKWARD, ModItems.STEAM_FLYING_FISH.get(), LAVA_WALKER.get()))
    }
}