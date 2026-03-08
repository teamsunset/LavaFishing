package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.item.recipe.RecipeDivisionTimes
import club.redux.sunset.lavafishing.misc.ModTags
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModItemsAqua
import com.teammetallurgy.aquaculture.init.AquaItems
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.InventoryChangeTrigger
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.*
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.Tags
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
        val fishingRodPattern = { result: ItemLike, ingredient: Ingredient ->
            ShapedRecipeBuilder.shaped(category, result)
                .define('s', Tags.Items.STRINGS)
                .define('i', ingredient)
                .define('t', Items.STICK)
                .pattern("  i")
                .pattern(" is")
                .pattern("t s")
                .also { recipe ->
                    ingredient.items.map(ItemStack::getItem).forEach { recipe.unlockedBy("has_item", has(it)) }
                }
        }
        fishingRodPattern(ModItems.OBSIDIAN_FISHING_ROD.get(), Ingredient.of(Tags.Items.OBSIDIANS))
            .save(recipeOutput)
//        fishingRodPattern(ModItems.NETHERITE_FISHING_ROD.get(), Ingredient.of(Tags.Items.INGOTS_NETHERITE))
//            .save(recipeOutput)

        SmithingTransformRecipeBuilder.smithing(
            Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
            Ingredient.of(AquaItems.DIAMOND_FISHING_ROD),
            Ingredient.of(Items.NETHERITE_INGOT),
            category,
            ModItems.NETHERITE_FISHING_ROD.get()
        ).unlocks("has_item", has(Items.NETHERITE_INGOT)).save(
            recipeOutput,
            LavaFishing.resourceLocation(ModItems.NETHERITE_FISHING_ROD.key!!.location().path + "_smithing")
        )

        val slingshotPattern = { result: ItemLike, ingredient: Ingredient ->
            ShapedRecipeBuilder.shaped(category, result)
                .define('s', Tags.Items.STRINGS)
                .define('l', Tags.Items.LEATHERS)
                .define('i', ingredient)
                .pattern("isi")
                .pattern(" l ")
                .pattern(" i ")
                .also { recipe ->
                    ingredient.items.map(ItemStack::getItem).forEach { recipe.unlockedBy("has_item", has(it)) }
                }
        }
        slingshotPattern(ModItems.IRON_SLINGSHOT.get(), Ingredient.of(Tags.Items.INGOTS_IRON))
            .save(recipeOutput)
        slingshotPattern(ModItems.NEPTUNIUM_SLINGSHOT.get(), Ingredient.of(AquaItems.NEPTUNIUM_INGOT.get()))
            .save(recipeOutput)
        slingshotPattern(ModItems.PROMETHIUM_SLINGSHOT.get(), Ingredient.of(ModTags.OreDirectory.PROMETHIUM_INGOT))
            .save(recipeOutput)
    }

    private fun buildArmors(recipeOutput: RecipeOutput) {
        val category = RecipeCategory.COMBAT
        val promethiumArmorPattern = { itemLike: ItemLike ->
            ShapedRecipeBuilder.shaped(category, itemLike)
                .define('#', ModTags.OreDirectory.PROMETHIUM_INGOT)
                .unlockedBy("has_item", has(ModTags.OreDirectory.PROMETHIUM_INGOT))
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

        val blastingPattern = { result: ItemLike, ingredients: List<ItemLike> ->
            SimpleCookingRecipeBuilder.blasting(
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

        blastingPattern(
            ModItems.PROMETHIUM_INGOT.get(), listOf(
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
            .define('#', ModTags.OreDirectory.PROMETHIUM_NUGGET)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(recipeOutput)
        ShapelessRecipeBuilder.shapeless(category, ModItems.PROMETHIUM_NUGGET.get(), 9)
            .requires(ModTags.OreDirectory.PROMETHIUM_INGOT)
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(recipeOutput)
        ShapedRecipeBuilder.shaped(category, ModItems.PROMETHIUM_BLOCK.get())
            .define('#', ModTags.OreDirectory.PROMETHIUM_INGOT)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ModTags.OreDirectory.PROMETHIUM_INGOT))
            .save(recipeOutput)
        ShapelessRecipeBuilder.shapeless(category, ModItems.PROMETHIUM_INGOT.get(), 9)
            .requires(ModTags.OreDirectory.PROMETHIUM_BLOCK)
            .unlockedBy("has_item", has(ModItems.PROMETHIUM_INGOT.get()))
            .save(
                recipeOutput,
                LavaFishing.resourceLocation(ModItems.PROMETHIUM_INGOT.get().descriptionId + "_from_block")
            )

        val bulletPattern = { itemLike: ItemLike, ingredient: Ingredient ->
            ShapelessRecipeBuilder.shapeless(category, itemLike)
                .requires(ingredient)
                .requires(Items.CLAY_BALL)
                .also { recipe ->
                    ingredient.items.map(ItemStack::getItem).forEach { recipe.unlockedBy("has_item", has(it)) }
                }
        }
        bulletPattern(ModItems.STONE_BULLET.get(), Ingredient.of(Items.STONE_BUTTON))
            .save(recipeOutput)
        bulletPattern(ModItems.IRON_BULLET.get(), Ingredient.of(Tags.Items.NUGGETS_IRON))
            .save(recipeOutput)
        bulletPattern(ModItems.NEPTUNIUM_BULLET.get(), Ingredient.of(AquaItems.NEPTUNIUM_NUGGET.get()))
            .requires(Items.PRISMARINE_CRYSTALS)
            .save(recipeOutput)
        bulletPattern(ModItems.PROMETHIUM_BULLET.get(), Ingredient.of(ModTags.OreDirectory.PROMETHIUM_NUGGET))
            .requires(Tags.Items.GUNPOWDERS)
            .save(recipeOutput)

        RecipeDivisionTimes.dataSave(recipeOutput, LavaFishing.resourceLocation("division_times"))

        ShapelessRecipeBuilder.shapeless(category, ModItemsAqua.DOUBLE_OBSIDIAN_HOOK.get())
            .requires(ModItemsAqua.OBSIDIAN_HOOK.get(), 2)
            .unlockedBy("has_item", has(ModItemsAqua.OBSIDIAN_HOOK.get()))
            .save(recipeOutput)

        ShapedRecipeBuilder.shaped(category, ModItemsAqua.GLOWSTONE_HOOK.get())
            .define('G', Tags.Items.DUSTS_GLOWSTONE)
            .define('H', AquaItems.IRON_HOOK)
            .pattern(" G ")
            .pattern("GHG")
            .pattern(" G ")
            .unlockedBy("has_item", has(AquaItems.IRON_HOOK))
            .save(recipeOutput)

        ShapelessRecipeBuilder.shapeless(category, ModItemsAqua.OBSIDIAN_HOOK.get())
            .requires(AquaItems.IRON_HOOK)
            .requires(Tags.Items.OBSIDIANS)
            .unlockedBy("has_item", has(AquaItems.IRON_HOOK))
            .save(recipeOutput)

        ShapelessRecipeBuilder.shapeless(category, ModItemsAqua.OBSIDIAN_NOTE_HOOK.get())
            .requires(AquaItems.IRON_HOOK)
            .requires(Items.NOTE_BLOCK)
            .unlockedBy("has_item", has(AquaItems.IRON_HOOK))
            .save(recipeOutput)

        ShapedRecipeBuilder.shaped(category, ModItemsAqua.QUARTZ_HOOK.get())
            .define('Q', Tags.Items.GEMS_QUARTZ)
            .define('O', ModItemsAqua.OBSIDIAN_HOOK.get())
            .pattern(" Q ")
            .pattern("QOQ")
            .pattern(" Q ")
            .unlockedBy("has_item", has(ModItemsAqua.OBSIDIAN_HOOK.get()))
            .save(recipeOutput)

        ShapedRecipeBuilder.shaped(category, ModItemsAqua.SOUL_SAND_HOOK.get())
            .define('S', Items.SOUL_SAND)
            .define('O', ModItemsAqua.OBSIDIAN_HOOK.get())
            .pattern(" S ")
            .pattern("SOS")
            .pattern(" S ")
            .unlockedBy("has_item", has(ModItemsAqua.OBSIDIAN_HOOK.get()))
            .save(recipeOutput)

//        ShapelessRecipeBuilder.shapeless(category, ModItems.HYDROTHERMAL_HOOK.get())
//            .requires(ModItemsAqua.OBSIDIAN_HOOK.get())
//            .requires(Items.PRISMARINE_CRYSTALS)
//            .unlockedBy("has_obsidian_hook", has(ModItemsAqua.OBSIDIAN_HOOK.get()))
//            .save(recipeOutput)
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

        ShapelessRecipeBuilder.shapeless(category, ModItems.FISH_PASTE.get())
            .requires(AquaItems.FISH_FILLET, 9)
            .unlockedBy("has_item", has(AquaItems.FISH_FILLET))
            .save(recipeOutput)
    }
}
