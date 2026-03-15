package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.item.recipe.RecipeDivisionTimes
import club.redux.sunset.lavafishing.misc.ModResourceLocation
import club.redux.sunset.lavafishing.misc.ModTags
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModItemsAqua
import com.teammetallurgy.aquaculture.api.fishing.Hooks
import com.teammetallurgy.aquaculture.init.AquaItems
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraftforge.common.Tags
import java.util.function.Consumer

class ModDataProviderRecipe(pOutput: PackOutput) : RecipeProvider(pOutput) {
    override fun buildRecipes(writer: Consumer<FinishedRecipe>) {
        this.buildTools(writer)
        this.buildArmors(writer)
        this.buildMisc(writer)
        this.buildFood(writer)
    }

    private val smeltingPattern = { category: RecipeCategory, result: ItemLike, ingredients: List<ItemLike> ->
        SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(*ingredients.toTypedArray()),
            category,
            result,
            0.7f,
            200,
        ).unlockedBy(
            "has_item",
            inventoryTrigger(*ingredients.map { ItemPredicate.Builder.item().of(it).build() }.toTypedArray()),
        )
    }

    private fun buildTools(writer: Consumer<FinishedRecipe>) {
        val category = RecipeCategory.TOOLS

        ShapedRecipeBuilder.shaped(category, ModItems.OBSIDIAN_FISHING_ROD.get())
            .define('s', Tags.Items.STRING)
            .define('i', Tags.Items.OBSIDIAN)
            .define('t', Tags.Items.RODS_WOODEN)
            .pattern("  i")
            .pattern(" is")
            .pattern("t s")
            .unlockedBy("has_item", has(Tags.Items.OBSIDIAN))
            .save(writer)

        SmithingTransformRecipeBuilder.smithing(
            Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
            Ingredient.of(AquaItems.DIAMOND_FISHING_ROD.get()),
            Ingredient.of(Tags.Items.INGOTS_NETHERITE),
            category,
            ModItems.NETHERITE_FISHING_ROD.get()
        )
            .unlocks("has_item", has(Tags.Items.INGOTS_NETHERITE))
            .save(writer, ModResourceLocation(ModItems.NETHERITE_FISHING_ROD.key!!.location().path + "_smithing"))

        ShapedRecipeBuilder.shaped(category, ModItems.IRON_SLINGSHOT.get())
            .define('s', Tags.Items.STRING)
            .define('l', Tags.Items.LEATHER)
            .define('i', Tags.Items.INGOTS_IRON)
            .pattern("isi")
            .pattern(" l ")
            .pattern(" i ")
            .unlockedBy("has_item", has(Tags.Items.INGOTS_IRON))
            .save(writer)

        ShapedRecipeBuilder.shaped(category, ModItems.NEPTUNIUM_SLINGSHOT.get())
            .define('s', Tags.Items.STRING)
            .define('l', Tags.Items.LEATHER)
            .define('i', AquaItems.NEPTUNIUM_INGOT.get())
            .pattern("isi")
            .pattern(" l ")
            .pattern(" i ")
            .unlockedBy("has_item", has(AquaItems.NEPTUNIUM_INGOT.get()))
            .save(writer)

        ShapedRecipeBuilder.shaped(category, ModItems.PROMETHIUM_SLINGSHOT.get())
            .define('s', Tags.Items.STRING)
            .define('l', Tags.Items.LEATHER)
            .define('i', ModTags.OreDirectory.PROMETHIUM_INGOT)
            .pattern("isi")
            .pattern(" l ")
            .pattern(" i ")
            .unlockedBy("has_item", has(ModTags.OreDirectory.PROMETHIUM_INGOT))
            .save(writer)
    }

    private fun buildArmors(writer: Consumer<FinishedRecipe>) {
        val category = RecipeCategory.COMBAT
        val promethiumArmorPattern = { itemLike: ItemLike ->
            ShapedRecipeBuilder.shaped(category, itemLike)
                .define('#', ModTags.OreDirectory.PROMETHIUM_INGOT)
                .unlockedBy("has_item", has(ModTags.OreDirectory.PROMETHIUM_INGOT))
        }

        promethiumArmorPattern(ModItems.PROMETHIUM_HELMET.get())
            .pattern("###")
            .pattern("# #")
            .save(writer)
        promethiumArmorPattern(ModItems.PROMETHIUM_CHESTPLATE.get())
            .pattern("# #")
            .pattern("###")
            .pattern("###")
            .save(writer)
        promethiumArmorPattern(ModItems.PROMETHIUM_LEGGINGS.get())
            .pattern("###")
            .pattern("# #")
            .pattern("# #")
            .save(writer)
        promethiumArmorPattern(ModItems.PROMETHIUM_BOOTS.get())
            .pattern("# #")
            .pattern("# #")
            .save(writer)

        smeltingPattern(
            category,
            ModItems.PROMETHIUM_NUGGET.get(),
            listOf(
                ModItems.PROMETHIUM_HELMET.get(),
                ModItems.PROMETHIUM_CHESTPLATE.get(),
                ModItems.PROMETHIUM_LEGGINGS.get(),
                ModItems.PROMETHIUM_BOOTS.get(),
            ),
        ).save(writer, ModResourceLocation(ModItems.PROMETHIUM_NUGGET.get().descriptionId + "_smelting"))
    }

    private fun buildMisc(writer: Consumer<FinishedRecipe>) {
        val category = RecipeCategory.MISC
        ShapedRecipeBuilder.shaped(category, ModItems.PROMETHIUM_INGOT.get())
            .define('#', ModTags.OreDirectory.PROMETHIUM_NUGGET)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ModTags.OreDirectory.PROMETHIUM_NUGGET))
            .save(writer)
        ShapelessRecipeBuilder.shapeless(category, ModItems.PROMETHIUM_NUGGET.get(), 9)
            .requires(ModTags.OreDirectory.PROMETHIUM_INGOT)
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(writer)
        ShapedRecipeBuilder.shaped(category, ModItems.PROMETHIUM_BLOCK.get())
            .define('#', ModTags.OreDirectory.PROMETHIUM_INGOT)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ModTags.OreDirectory.PROMETHIUM_INGOT))
            .save(writer)
        ShapelessRecipeBuilder.shapeless(category, ModItems.PROMETHIUM_INGOT.get(), 9)
            .requires(ModTags.OreDirectory.PROMETHIUM_BLOCK)
            .unlockedBy("has_item", has(ModTags.OreDirectory.PROMETHIUM_BLOCK))
            .save(writer, ModResourceLocation(ModItems.PROMETHIUM_INGOT.get().descriptionId + "_from_block"))

        val bulletPattern = { itemLike: ItemLike, ingredient: ItemLike ->
            ShapelessRecipeBuilder.shapeless(category, itemLike)
                .requires(ingredient)
                .requires(Items.CLAY_BALL)
                .unlockedBy("has_item", has(ingredient))
        }
        bulletPattern(ModItems.STONE_BULLET.get(), Items.STONE_BUTTON).save(writer)
        ShapelessRecipeBuilder.shapeless(category, ModItems.IRON_BULLET.get())
            .requires(Tags.Items.NUGGETS_IRON)
            .requires(Items.CLAY_BALL)
            .unlockedBy("has_item", has(Tags.Items.NUGGETS_IRON))
            .save(writer)
        bulletPattern(ModItems.NEPTUNIUM_BULLET.get(), AquaItems.NEPTUNIUM_NUGGET.get())
            .requires(Items.PRISMARINE_CRYSTALS)
            .save(writer)
        ShapelessRecipeBuilder.shapeless(category, ModItems.PROMETHIUM_BULLET.get())
            .requires(ModTags.OreDirectory.PROMETHIUM_NUGGET)
            .requires(Tags.Items.GUNPOWDER)
            .requires(Items.CLAY_BALL)
            .unlockedBy("has_item", has(ModTags.OreDirectory.PROMETHIUM_NUGGET))
            .save(writer)

        RecipeDivisionTimes.save(writer, ModResourceLocation("division_times"))

        ShapelessRecipeBuilder.shapeless(category, ModItemsAqua.DOUBLE_OBSIDIAN_HOOK.get())
            .requires(ModItemsAqua.OBSIDIAN_HOOK.get(), 2)
            .unlockedBy("has_item", has(ModItemsAqua.OBSIDIAN_HOOK.get()))
            .save(writer)

        ShapedRecipeBuilder.shaped(category, ModItemsAqua.GLOWSTONE_HOOK.get())
            .define('G', Tags.Items.DUSTS_GLOWSTONE)
            .define('H', Hooks.IRON.item)
            .pattern(" G ")
            .pattern("GHG")
            .pattern(" G ")
            .unlockedBy("has_item", has(Hooks.IRON.item))
            .save(writer)

        ShapelessRecipeBuilder.shapeless(category, ModItemsAqua.OBSIDIAN_HOOK.get())
            .requires(Hooks.IRON.item)
            .requires(Tags.Items.OBSIDIAN)
            .unlockedBy("has_item", has(Hooks.IRON.item))
            .save(writer)

        ShapelessRecipeBuilder.shapeless(category, ModItemsAqua.OBSIDIAN_NOTE_HOOK.get())
            .requires(Hooks.IRON.item)
            .requires(Items.NOTE_BLOCK)
            .unlockedBy("has_item", has(Hooks.IRON.item))
            .save(writer)

        ShapedRecipeBuilder.shaped(category, ModItemsAqua.QUARTZ_HOOK.get())
            .define('Q', Tags.Items.GEMS_QUARTZ)
            .define('O', ModItemsAqua.OBSIDIAN_HOOK.get())
            .pattern(" Q ")
            .pattern("QOQ")
            .pattern(" Q ")
            .unlockedBy("has_item", has(ModItemsAqua.OBSIDIAN_HOOK.get()))
            .save(writer)

        ShapedRecipeBuilder.shaped(category, ModItemsAqua.SOUL_SAND_HOOK.get())
            .define('S', Items.SOUL_SAND)
            .define('O', ModItemsAqua.OBSIDIAN_HOOK.get())
            .pattern(" S ")
            .pattern("SOS")
            .pattern(" S ")
            .unlockedBy("has_item", has(ModItemsAqua.OBSIDIAN_HOOK.get()))
            .save(writer)
    }

    private fun buildFood(writer: Consumer<FinishedRecipe>) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SPICY_FISH_FILLET.get())
            .requires(Items.BLAZE_POWDER)
            .requires(Items.WEEPING_VINES)
            .requires(AquaItems.COOKED_FILLET.get())
            .requires(Items.BOWL)
            .unlockedBy("has_item", has(AquaItems.COOKED_FILLET.get()))
            .save(writer)

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.FISH_PASTE.get())
            .requires(AquaItems.FISH_FILLET.get(), 9)
            .unlockedBy("has_item", has(AquaItems.FISH_FILLET.get()))
            .save(writer)
    }
}
