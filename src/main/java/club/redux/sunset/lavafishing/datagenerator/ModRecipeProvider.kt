package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.registry.ModItems
import com.teammetallurgy.aquaculture.init.AquaItems
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.*
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import java.util.function.Consumer

class ModRecipeProvider(pOutput: PackOutput) : RecipeProvider(pOutput) {
    override fun buildRecipes(pWriter: Consumer<FinishedRecipe>) {
        this.buildTools(pWriter)
        this.buildArmors(pWriter)
        this.buildMisc(pWriter)
    }

    val smeltingPattern = { category: RecipeCategory, result: ItemLike, ingredients: List<ItemLike> ->
        SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(*ingredients.toTypedArray()),
            category,
            result,
            0.7f,
            200
        ).unlockedBy(
            "has_item",
            inventoryTrigger(*ingredients.map { ItemPredicate.Builder.item().of(it).build() }.toTypedArray())
        )
    }

    private fun buildTools(pWriter: Consumer<FinishedRecipe>) {
        val category = RecipeCategory.TOOLS
        ShapedRecipeBuilder.shaped(category, ModItems.OBSIDIAN_FISHING_ROD.get())
            .define('s', Items.STRING)
            .define('o', Items.OBSIDIAN)
            .define('t', Items.STICK)
            .pattern("  o")
            .pattern(" os")
            .pattern("t s")
            .unlockedBy("has_item", has(Items.OBSIDIAN))
            .save(pWriter)
        ShapedRecipeBuilder.shaped(category, ModItems.PROMETHIUM_SLINGSHOT.get())
            .define('s', Items.STRING)
            .define('p', ModItems.PROMETHIUM_INGOT.get())
            .define('l', Items.LEATHER)
            .pattern("psp")
            .pattern("lpl")
            .pattern("lpl")
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(pWriter)
    }

    private fun buildArmors(pWriter: Consumer<FinishedRecipe>) {
        val category = RecipeCategory.COMBAT
        val promethiumArmorPattern = { itemLike: ItemLike ->
            ShapedRecipeBuilder.shaped(category, itemLike)
                .define('#', ModItems.PROMETHIUM_INGOT.get())
                .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
        }

        promethiumArmorPattern(ModItems.PROMETHIUM_HELMET.get())
            .pattern("###")
            .pattern("# #")
            .save(pWriter)
        promethiumArmorPattern(ModItems.PROMETHIUM_CHESTPLATE.get())
            .pattern("# #")
            .pattern("###")
            .pattern("###")
            .save(pWriter)
        promethiumArmorPattern(ModItems.PROMETHIUM_LEGGINGS.get())
            .pattern("###")
            .pattern("# #")
            .pattern("# #")
            .save(pWriter)
        promethiumArmorPattern(ModItems.PROMETHIUM_BOOTS.get())
            .pattern("# #")
            .pattern("# #")
            .save(pWriter)

        smeltingPattern(
            category, ModItems.PROMETHIUM_NUGGET.get(),
            listOf(
                ModItems.PROMETHIUM_HELMET.get(),
                ModItems.PROMETHIUM_CHESTPLATE.get(),
                ModItems.PROMETHIUM_LEGGINGS.get(),
                ModItems.PROMETHIUM_BOOTS.get()
            )
        ).save(pWriter, ModItems.PROMETHIUM_NUGGET.get().descriptionId + "_smelting")
    }

    private fun buildMisc(pWriter: Consumer<FinishedRecipe>) {
        val category = RecipeCategory.MISC
        ShapedRecipeBuilder.shaped(category, ModItems.PROMETHIUM_INGOT.get())
            .define('#', ModItems.PROMETHIUM_NUGGET.get())
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(pWriter)
        ShapelessRecipeBuilder.shapeless(category, ModItems.PROMETHIUM_NUGGET.get(), 9)
            .requires(ModItems.PROMETHIUM_INGOT.get())
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(pWriter)
        ShapedRecipeBuilder.shaped(category, ModItems.PROMETHIUM_BLOCK.get())
            .define('#', ModItems.PROMETHIUM_INGOT.get())
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(pWriter)
        ShapelessRecipeBuilder.shapeless(category, ModItems.PROMETHIUM_INGOT.get(), 9)
            .requires(ModItems.PROMETHIUM_BLOCK.get())
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(pWriter, ModItems.PROMETHIUM_INGOT.get().descriptionId + "_from_block")

        val bulletPattern = { itemLike: ItemLike, ingredient: ItemLike ->
            ShapelessRecipeBuilder.shapeless(category, itemLike)
                .requires(ingredient)
                .requires(Items.CLAY_BALL)
                .unlockedBy("has_item", has(ingredient))
        }
        bulletPattern(ModItems.STONE_BULLET.get(), Items.STONE_BUTTON)
            .save(pWriter)
        bulletPattern(ModItems.IRON_BULLET.get(), Items.IRON_NUGGET)
            .save(pWriter)
        bulletPattern(ModItems.NEPTUNIUM_BULLET.get(), AquaItems.NEPTUNIUM_NUGGET.get())
            .requires(Items.PRISMARINE_CRYSTALS)
            .save(pWriter)
        bulletPattern(ModItems.PROMETHIUM_BULLET.get(), ModItems.PROMETHIUM_NUGGET.get())
            .requires(Items.GUNPOWDER)
            .save(pWriter)
    }
}