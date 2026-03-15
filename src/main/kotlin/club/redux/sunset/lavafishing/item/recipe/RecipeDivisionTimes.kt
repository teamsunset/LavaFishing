package club.redux.sunset.lavafishing.item.recipe

import club.redux.sunset.lavafishing.item.bullet.ItemPromethiumBullet
import club.redux.sunset.lavafishing.misc.ModResourceLocation
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModRecipeSerializers
import net.minecraft.core.NonNullList
import net.minecraft.core.RegistryAccess
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.SpecialRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingRecipe
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.ShapelessRecipe
import net.minecraft.world.level.Level
import java.util.function.Consumer

class RecipeDivisionTimes(id: ResourceLocation, category: CraftingBookCategory) : CustomRecipe(id, category) {
    override fun matches(container: CraftingContainer, level: Level): Boolean {
        val stacks = (0 until container.containerSize).map(container::getItem).filterNot(ItemStack::isEmpty)
        val bullets = stacks.filterNot { it.`is`(Items.CLAY_BALL) }

        if (bullets.size <= 1 || stacks.count { it.`is`(Items.CLAY_BALL) } != 1) {
            return false
        }
        if (!bullets.all { it.`is`(ModItems.PROMETHIUM_BULLET.get()) }) {
            return false
        }

        val divisionTimes = bullets.map(ItemPromethiumBullet::getDivisionTimes).distinct()
        return divisionTimes.size == 1 && divisionTimes.first() * bullets.size <= MAX_DIVISION_TIMES
    }

    override fun assemble(container: CraftingContainer, registryAccess: RegistryAccess): ItemStack {
        val bullets = (0 until container.containerSize)
            .map(container::getItem)
            .filterNot(ItemStack::isEmpty)
            .filterNot { it.`is`(Items.CLAY_BALL) }
        val divisionTimes = ItemPromethiumBullet.getDivisionTimes(bullets.first()) * bullets.size
        return ItemPromethiumBullet.setDivisionTimes(ItemStack(ModItems.PROMETHIUM_BULLET.get()), divisionTimes)
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean = width * height >= 2

    override fun getSerializer(): RecipeSerializer<*> = ModRecipeSerializers.DIVISION_TIMES.get()

    companion object {
        const val MAX_DIVISION_TIMES = 16

        fun createJeiRecipe(): List<CraftingRecipe> {
            val recipes = mutableListOf<CraftingRecipe>()

            for (resultDivisionTimes in 2..MAX_DIVISION_TIMES) {
                for (ingredientDivisionTimes in 1..(MAX_DIVISION_TIMES / 2)) {
                    if (resultDivisionTimes % ingredientDivisionTimes != 0) {
                        continue
                    }

                    val ingredientCount = resultDivisionTimes / ingredientDivisionTimes
                    if (ingredientCount !in 2..8) {
                        continue
                    }

                    recipes += ShapelessRecipe(
                        ModResourceLocation("division_times.${resultDivisionTimes}_${ingredientDivisionTimes}"),
                        "",
                        CraftingBookCategory.MISC,
                        ItemPromethiumBullet.setDivisionTimes(
                            ItemStack(ModItems.PROMETHIUM_BULLET.get()),
                            resultDivisionTimes,
                        ),
                        NonNullList.of(
                            Ingredient.EMPTY,
                            Ingredient.of(Items.CLAY_BALL),
                            *List(ingredientCount) {
                                Ingredient.of(
                                    ItemPromethiumBullet.setDivisionTimes(
                                        ItemStack(ModItems.PROMETHIUM_BULLET.get()),
                                        ingredientDivisionTimes,
                                    )
                                )
                            }.toTypedArray(),
                        ),
                    )
                }
            }

            return recipes
        }

        fun save(writer: Consumer<FinishedRecipe>, id: ResourceLocation) {
            SpecialRecipeBuilder.special(ModRecipeSerializers.DIVISION_TIMES.get()).save(writer, id.toString())
        }
    }
}
