package club.redux.sunset.lavafishing.item.recipe

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.registry.ModDataComponentTypes
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModRecipeSerializers
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level
import net.neoforged.neoforge.common.crafting.DataComponentIngredient


class RecipeDivisionTimes : CustomRecipe() {

    override fun matches(pInput: CraftingInput, pLevel: Level): Boolean {
        val noAir = pInput.items().filter { !it.isEmpty }
        val noAirAndClay = noAir.filter { !it.`is`(Items.CLAY_BALL) }
        return noAirAndClay.size > 1 &&
                noAir.count { it.`is`(Items.CLAY_BALL) } == 1 &&
                noAirAndClay.map { it.item }.distinct().size == 1 &&
                noAirAndClay
                    .map { it.get(ModDataComponentTypes.BULLET_DIVISION_TIMES) }
                    .distinct()
                    .let { !it.contains(null) && it.size == 1 && it[0]!! * noAirAndClay.size <= MAX_DIVISION_TIMES }
    }

    override fun assemble(pInput: CraftingInput): ItemStack {
        val noAirAndClay = pInput.items().filter { !it.isEmpty }.filter { !it.`is`(Items.CLAY_BALL) }
        return ItemStack(noAirAndClay[0].item).apply {
            set(
                ModDataComponentTypes.BULLET_DIVISION_TIMES,
                (noAirAndClay[0].get(ModDataComponentTypes.BULLET_DIVISION_TIMES) ?: 1) * noAirAndClay.size
            )
        }
    }

    override fun category() = CraftingBookCategory.EQUIPMENT

    override fun getSerializer(): RecipeSerializer<RecipeDivisionTimes> = ModRecipeSerializers.DIVISION_TIMES.get()

    companion object {
        const val MAX_DIVISION_TIMES = 16

        fun createJeiRecipe(): List<RecipeHolder<CraftingRecipe>> {
            val recipes = mutableListOf<RecipeHolder<CraftingRecipe>>()

            (2..MAX_DIVISION_TIMES).forEach { resultDivisionTimes ->
                (1..MAX_DIVISION_TIMES / 2).forEach innerFor@{ ingredientDivisionTimes ->
                    if (resultDivisionTimes % ingredientDivisionTimes != 0 || resultDivisionTimes / ingredientDivisionTimes !in 2..8) return@innerFor
                    recipes.add(
                        RecipeHolder(
                            ResourceKey.create(
                                Registries.RECIPE,
                                LavaFishing.identifier("division_times.${resultDivisionTimes}_${ingredientDivisionTimes}"),
                            ),
                            ShapelessRecipe(
                                Recipe.CommonInfo(false),
                                CraftingRecipe.CraftingBookInfo(
                                    CraftingBookCategory.EQUIPMENT,
                                    "lavafishing.division_times",
                                ),
                                ItemStackTemplate.fromNonEmptyStack(
                                    ItemStack(ModItems.PROMETHIUM_BULLET.get()).apply {
                                        set(ModDataComponentTypes.BULLET_DIVISION_TIMES, resultDivisionTimes)
                                    },
                                ),
                                buildList {
                                    add(Ingredient.of(Items.CLAY_BALL))
                                    repeat(resultDivisionTimes / ingredientDivisionTimes) {
                                        add(
                                            DataComponentIngredient.of(
                                                false,
                                                ItemStack(ModItems.PROMETHIUM_BULLET.get()).apply {
                                                    set(
                                                        ModDataComponentTypes.BULLET_DIVISION_TIMES,
                                                        ingredientDivisionTimes,
                                                    )
                                                },
                                            ),
                                        )
                                    }
                                },
                            )
                        )
                    )
                }
            }

            return recipes
        }
    }
}
