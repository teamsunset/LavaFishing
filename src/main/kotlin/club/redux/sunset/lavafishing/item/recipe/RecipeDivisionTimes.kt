package club.redux.sunset.lavafishing.item.recipe

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.registry.ModDataComponentTypes
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModRecipeSerializers
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.InventoryChangeTrigger
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level


class RecipeDivisionTimes(pCategory: CraftingBookCategory) : CustomRecipe(pCategory) {

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

    override fun assemble(pInput: CraftingInput, pRegistries: HolderLookup.Provider): ItemStack {
        val noAirAndClay = pInput.items().filter { !it.isEmpty }.filter { !it.`is`(Items.CLAY_BALL) }
        return ItemStack(noAirAndClay[0].item).apply {
            set(
                ModDataComponentTypes.BULLET_DIVISION_TIMES,
                (noAirAndClay[0].get(ModDataComponentTypes.BULLET_DIVISION_TIMES) ?: 1) * noAirAndClay.size
            )
        }
    }

    override fun canCraftInDimensions(pWidth: Int, pHeight: Int) = pWidth * pHeight >= 2

    override fun getSerializer(): RecipeSerializer<*> = ModRecipeSerializers.DIVISION_TIMES.get()

    companion object {
        const val MAX_DIVISION_TIMES = 16

        fun createJeiRecipe(): List<RecipeHolder<CraftingRecipe>> {
            val recipes = mutableListOf<RecipeHolder<CraftingRecipe>>()

            (2..MAX_DIVISION_TIMES).forEach { resultDivisionTimes ->
                (1..MAX_DIVISION_TIMES / 2).forEach innerFor@{ ingredientDivisionTimes ->
                    if (resultDivisionTimes % ingredientDivisionTimes != 0 || resultDivisionTimes / ingredientDivisionTimes !in 2..8) return@innerFor
                    recipes.add(
                        RecipeHolder(
                            LavaFishing.resourceLocation("division_times.${resultDivisionTimes}_${ingredientDivisionTimes}"),
                            ShapelessRecipe(
                                "${BuiltConstants.MOD_ID}.division_times",
                                CraftingBookCategory.EQUIPMENT,
                                ItemStack(ModItems.PROMETHIUM_BULLET).apply {
                                    set(ModDataComponentTypes.BULLET_DIVISION_TIMES, resultDivisionTimes)
                                },
                                NonNullList.of(
                                    Ingredient.EMPTY,
                                    *arrayOf(
                                        Ingredient.of(Items.CLAY_BALL),
                                        *List(resultDivisionTimes / ingredientDivisionTimes) {
                                            Ingredient.of(
                                                ItemStack(ModItems.PROMETHIUM_BULLET).apply {
                                                    set(
                                                        ModDataComponentTypes.BULLET_DIVISION_TIMES,
                                                        ingredientDivisionTimes
                                                    )
                                                }
                                            )
                                        }.toTypedArray()
                                    )
                                )
                            )
                        )
                    )
                }
            }

            return recipes
        }

        fun dataSave(output: RecipeOutput, location: ResourceLocation) {
            val advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(location))
                .addCriterion(
                    "has_item",
                    Criterion(
                        CriteriaTriggers.INVENTORY_CHANGED,
                        InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PROMETHIUM_BULLET.get()).triggerInstance
                    )
                )
                .rewards(AdvancementRewards.Builder.recipe(location))
                .requirements(AdvancementRequirements.Strategy.OR)
            val recipe = RecipeDivisionTimes(CraftingBookCategory.EQUIPMENT)
            output.accept(location, recipe, advancement.build(location.withPrefix("recipes/")))
        }
    }
}