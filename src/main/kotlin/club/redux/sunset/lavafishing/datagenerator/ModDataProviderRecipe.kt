package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.item.recipe.RecipeDivisionTimes
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModItemsAqua
import com.teammetallurgy.aquaculture.init.AquaItems
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.InventoryChangeTrigger
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.*
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import java.util.concurrent.CompletableFuture

class ModDataProviderRecipe(
    pOutput: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
) : RecipeProvider(pOutput, lookupProvider) {
    override fun buildRecipes(recipeOutput: RecipeOutput) {
        this.buildTools(recipeOutput)
        this.buildArmors(recipeOutput)
        this.buildMisc(recipeOutput)
        this.buildFood(recipeOutput)
    }

    private fun checkInventory(vararg ingredients: ItemLike): Criterion<InventoryChangeTrigger.TriggerInstance> {
        return inventoryTrigger(*ingredients.map { ItemPredicate.Builder.item().of(it).build() }.toTypedArray())
    }

    private fun buildTools(recipeOutput: RecipeOutput) {
        val category = RecipeCategory.TOOLS
        val fishingRodPattern = { result: ItemLike, ingredient: ItemLike ->
            ShapedRecipeBuilder.shaped(category, result)
                .define('s', Items.STRING)
                .define('i', ingredient)
                .define('t', Items.STICK)
                .pattern("  i")
                .pattern(" is")
                .pattern("t s")
                .unlockedBy("has_item", has(ingredient))
        }
        fishingRodPattern(ModItems.OBSIDIAN_FISHING_ROD.get(), Items.OBSIDIAN)
            .save(recipeOutput)
        fishingRodPattern(ModItems.NETHERITE_FISHING_ROD.get(), Items.NETHERITE_INGOT)
            .save(recipeOutput)

        val slingshotPattern = { result: ItemLike, ingredient: ItemLike ->
            ShapedRecipeBuilder.shaped(category, result)
                .define('s', Items.STRING)
                .define('l', Items.LEATHER)
                .define('i', ingredient)
                .pattern("isi")
                .pattern(" l ")
                .pattern(" i ")
                .unlockedBy("has_item", has(ingredient))
        }
        slingshotPattern(ModItems.IRON_SLINGSHOT.get(), Items.IRON_INGOT)
            .save(recipeOutput)
        slingshotPattern(ModItems.NEPTUNIUM_SLINGSHOT.get(), AquaItems.NEPTUNIUM_INGOT.get())
            .save(recipeOutput)
        slingshotPattern(ModItems.PROMETHIUM_SLINGSHOT.get(), ModItems.PROMETHIUM_INGOT.get())
            .save(recipeOutput)
    }

    private fun buildArmors(recipeOutput: RecipeOutput) {
        val category = RecipeCategory.COMBAT
        val promethiumArmorPattern = { itemLike: ItemLike ->
            ShapedRecipeBuilder.shaped(category, itemLike)
                .define('#', ModItems.PROMETHIUM_INGOT.get())
                .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
        }

        promethiumArmorPattern(ModItems.PROMETHIUM_HELMET.get())
            .pattern("###")
            .pattern("# #")
            .save(recipeOutput)
        promethiumArmorPattern(ModItems.PROMETHIUM_CHESTPLATE.get())
            .pattern("# #")
            .pattern("###")
            .pattern("###")
            .save(recipeOutput)
        promethiumArmorPattern(ModItems.PROMETHIUM_LEGGINGS.get())
            .pattern("###")
            .pattern("# #")
            .pattern("# #")
            .save(recipeOutput)
        promethiumArmorPattern(ModItems.PROMETHIUM_BOOTS.get())
            .pattern("# #")
            .pattern("# #")
            .save(recipeOutput)

        val smeltingPattern = { category: RecipeCategory, result: ItemLike, ingredients: List<ItemLike> ->
            SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(*ingredients.toTypedArray()),
                category,
                result,
                0.7f,
                200
            ).unlockedBy(
                "has_item",
                checkInventory(*ingredients.toTypedArray())
            )
        }

        smeltingPattern(
            category, ModItems.PROMETHIUM_NUGGET.get(),
            listOf(
                ModItems.PROMETHIUM_HELMET.get(),
                ModItems.PROMETHIUM_CHESTPLATE.get(),
                ModItems.PROMETHIUM_LEGGINGS.get(),
                ModItems.PROMETHIUM_BOOTS.get()
            )
        ).save(recipeOutput, LavaFishing.resourceLocation(ModItems.PROMETHIUM_NUGGET.get().descriptionId + "_smelting"))
    }

    private fun buildMisc(recipeOutput: RecipeOutput) {
        val category = RecipeCategory.MISC
        ShapedRecipeBuilder.shaped(category, ModItems.PROMETHIUM_INGOT.get())
            .define('#', ModItems.PROMETHIUM_NUGGET.get())
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(recipeOutput)
        ShapelessRecipeBuilder.shapeless(category, ModItems.PROMETHIUM_NUGGET.get(), 9)
            .requires(ModItems.PROMETHIUM_INGOT.get())
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(recipeOutput)
        ShapedRecipeBuilder.shaped(category, ModItems.PROMETHIUM_BLOCK.get())
            .define('#', ModItems.PROMETHIUM_INGOT.get())
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(recipeOutput)
        ShapelessRecipeBuilder.shapeless(category, ModItems.PROMETHIUM_INGOT.get(), 9)
            .requires(ModItems.PROMETHIUM_BLOCK.get())
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(
                recipeOutput,
                LavaFishing.resourceLocation(ModItems.PROMETHIUM_INGOT.get().descriptionId + "_from_block")
            )

        val bulletPattern = { itemLike: ItemLike, ingredient: ItemLike ->
            ShapelessRecipeBuilder.shapeless(category, itemLike)
                .requires(ingredient)
                .requires(Items.CLAY_BALL)
                .unlockedBy("has_item", has(ingredient))
        }
        bulletPattern(ModItems.STONE_BULLET.get(), Items.STONE_BUTTON)
            .save(recipeOutput)
        bulletPattern(ModItems.IRON_BULLET.get(), Items.IRON_NUGGET)
            .save(recipeOutput)
        bulletPattern(ModItems.NEPTUNIUM_BULLET.get(), AquaItems.NEPTUNIUM_NUGGET.get())
            .requires(Items.PRISMARINE_CRYSTALS)
            .save(recipeOutput)
        bulletPattern(ModItems.PROMETHIUM_BULLET.get(), ModItems.PROMETHIUM_NUGGET.get())
            .requires(Items.GUNPOWDER)
            .save(recipeOutput)

        RecipeDivisionTimes.dataSave(recipeOutput, LavaFishing.resourceLocation("division_times"))

        ShapelessRecipeBuilder.shapeless(category, ModItemsAqua.DOUBLE_OBSIDIAN_HOOK.get())
            .requires(ModItemsAqua.OBSIDIAN_HOOK.get())
            .requires(ModItemsAqua.OBSIDIAN_HOOK.get())
            .unlockedBy("has_item", has(ModItemsAqua.OBSIDIAN_HOOK.get()))
            .save(recipeOutput)

        ShapedRecipeBuilder.shaped(category, ModItemsAqua.GLOWSTONE_HOOK.get())
            .define('G', Items.GLOWSTONE_DUST)
            .define('H', AquaItems.IRON_HOOK)
            .pattern(" G ")
            .pattern("GHG")
            .pattern(" G ")
            .unlockedBy("has_item", checkInventory(Items.GLOWSTONE_DUST, AquaItems.IRON_HOOK))
            .save(recipeOutput)

        ShapelessRecipeBuilder.shapeless(category, ModItemsAqua.OBSIDIAN_HOOK.get())
            .requires(AquaItems.IRON_HOOK)
            .requires(Items.OBSIDIAN)
            .unlockedBy("has_item", checkInventory(AquaItems.IRON_HOOK, Items.OBSIDIAN))
            .save(recipeOutput)

        ShapelessRecipeBuilder.shapeless(category, ModItemsAqua.OBSIDIAN_NOTE_HOOK.get())
            .requires(AquaItems.IRON_HOOK)
            .requires(Items.NOTE_BLOCK)
            .unlockedBy("has_item", checkInventory(AquaItems.IRON_HOOK, Items.NOTE_BLOCK))
            .save(recipeOutput)

        ShapedRecipeBuilder.shaped(category, ModItemsAqua.QUARTZ_HOOK.get())
            .define('Q', Items.QUARTZ)
            .define('O', ModItemsAqua.OBSIDIAN_HOOK.get())
            .pattern(" Q ")
            .pattern("QOQ")
            .pattern(" Q ")
            .unlockedBy("has_item", checkInventory(Items.QUARTZ, ModItemsAqua.OBSIDIAN_HOOK.get()))
            .save(recipeOutput)

        ShapedRecipeBuilder.shaped(category, ModItemsAqua.SOUL_SAND_HOOK.get())
            .define('S', Items.SOUL_SAND)
            .define('O', ModItemsAqua.OBSIDIAN_HOOK.get())
            .pattern(" S ")
            .pattern("SOS")
            .pattern(" S ")
            .unlockedBy("has_item", checkInventory(Items.SOUL_SAND, ModItemsAqua.OBSIDIAN_HOOK.get()))
            .save(recipeOutput)
    }

    private fun buildFood(recipeOutput: RecipeOutput) {
        val category = RecipeCategory.FOOD
        ShapelessRecipeBuilder.shapeless(category, ModItems.SPICY_FISH_FILLET.get())
            .requires(Items.BLAZE_POWDER)
            .requires(Items.WEEPING_VINES)
            .requires(AquaItems.COOKED_FILLET)
            .requires(Items.BOWL)
            .unlockedBy("has_item", has(AquaItems.COOKED_FILLET))
            .save(recipeOutput)
    }
}