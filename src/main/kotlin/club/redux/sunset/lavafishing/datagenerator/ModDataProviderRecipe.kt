package club.redux.sunset.lavafishing.datagenerator

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.item.recipe.RecipeDivisionTimes
import club.redux.sunset.lavafishing.misc.ModTags
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModItemsAqua
import com.teammetallurgy.aquaculture.init.AquaItems
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.*
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.CookingBookCategory
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.Tags
import java.util.concurrent.CompletableFuture

class ModDataProviderRecipe(
    registries: HolderLookup.Provider,
    output: RecipeOutput,
) : RecipeProvider(registries, output) {
    override fun buildRecipes() {
        buildTools()
        buildCombat()
        buildMisc()
        buildFood()
    }

    private fun buildTools() {
        shaped(RecipeCategory.TOOLS, ModItems.OBSIDIAN_FISHING_ROD.get())
            .define('s', Tags.Items.STRINGS)
            .define('i', Tags.Items.OBSIDIANS)
            .define('t', Items.STICK)
            .pattern("  i")
            .pattern(" is")
            .pattern("t s")
            .unlockedBy("has_obsidian", has(Tags.Items.OBSIDIANS))
            .save(this.output)

        SmithingTransformRecipeBuilder.smithing(
            Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
            Ingredient.of(AquaItems.DIAMOND_FISHING_ROD),
            Ingredient.of(Items.NETHERITE_INGOT),
            RecipeCategory.TOOLS,
            ModItems.NETHERITE_FISHING_ROD.get(),
        ).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
            .save(output, recipeKey("netherite_fishing_rod_smithing"))

        shaped(RecipeCategory.TOOLS, ModItems.IRON_SLINGSHOT.get())
            .define('s', Tags.Items.STRINGS)
            .define('l', Tags.Items.LEATHERS)
            .define('i', Tags.Items.INGOTS_IRON)
            .pattern("isi")
            .pattern(" l ")
            .pattern(" i ")
            .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
            .save(this.output)

        shaped(RecipeCategory.TOOLS, ModItems.NEPTUNIUM_SLINGSHOT.get())
            .define('s', Tags.Items.STRINGS)
            .define('l', Tags.Items.LEATHERS)
            .define('i', AquaItems.NEPTUNIUM_INGOT.get())
            .pattern("isi")
            .pattern(" l ")
            .pattern(" i ")
            .unlockedBy("has_neptunium_ingot", has(AquaItems.NEPTUNIUM_INGOT.get()))
            .save(this.output)

        shaped(RecipeCategory.TOOLS, ModItems.PROMETHIUM_SLINGSHOT.get())
            .define('s', Tags.Items.STRINGS)
            .define('l', Tags.Items.LEATHERS)
            .define('i', ModTags.OreDirectory.PROMETHIUM_INGOT)
            .pattern("isi")
            .pattern(" l ")
            .pattern(" i ")
            .unlockedBy("has_promethium_ingot", has(ModTags.OreDirectory.PROMETHIUM_INGOT))
            .save(this.output)
    }

    private fun buildCombat() {
        val promethiumArmorPattern = { itemLike: ItemLike ->
            shaped(RecipeCategory.COMBAT, itemLike)
                .define('#', ModTags.OreDirectory.PROMETHIUM_INGOT)
                .unlockedBy("has_promethium_ingot", has(ModTags.OreDirectory.PROMETHIUM_INGOT))
        }

        promethiumArmorPattern(ModItems.PROMETHIUM_HELMET.get())
            .pattern("###")
            .pattern("# #")
            .save(this.output)
        promethiumArmorPattern(ModItems.PROMETHIUM_CHESTPLATE.get())
            .pattern("# #")
            .pattern("###")
            .pattern("###")
            .save(this.output)
        promethiumArmorPattern(ModItems.PROMETHIUM_LEGGINGS.get())
            .pattern("###")
            .pattern("# #")
            .pattern("# #")
            .save(this.output)
        promethiumArmorPattern(ModItems.PROMETHIUM_BOOTS.get())
            .pattern("# #")
            .pattern("# #")
            .save(this.output)

        SimpleCookingRecipeBuilder.blasting(
            Ingredient.of(
                ModItems.PROMETHIUM_HELMET.get(),
                ModItems.PROMETHIUM_CHESTPLATE.get(),
                ModItems.PROMETHIUM_LEGGINGS.get(),
                ModItems.PROMETHIUM_BOOTS.get(),
            ),
            RecipeCategory.COMBAT,
            CookingBookCategory.MISC,
            ModItems.PROMETHIUM_INGOT.get(),
            0.7f,
            200,
        ).unlockedBy("has_promethium_armor", has(ModItems.PROMETHIUM_HELMET.get()))
            .save(this.output, recipeKey("promethium_ingot_from_armor_blasting"))

        CustomCraftingRecipeBuilder.customCrafting(RecipeCategory.COMBAT) { _, _ -> RecipeDivisionTimes() }
            .unlockedBy("has_promethium_bullet", has(ModItems.PROMETHIUM_BULLET.get()))
            .save(this.output, recipeKey("division_times"))
    }

    private fun buildMisc() {
        shaped(RecipeCategory.MISC, ModItems.PROMETHIUM_INGOT.get())
            .define('#', ModTags.OreDirectory.PROMETHIUM_NUGGET)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_promethium_nugget", has(ModTags.OreDirectory.PROMETHIUM_NUGGET))
            .save(this.output)
        shapeless(RecipeCategory.MISC, ModItems.PROMETHIUM_NUGGET.get(), 9)
            .requires(ModTags.OreDirectory.PROMETHIUM_INGOT)
            .unlockedBy("has_promethium_ingot", has(ModTags.OreDirectory.PROMETHIUM_INGOT))
            .save(this.output)
        shaped(RecipeCategory.MISC, ModItems.PROMETHIUM_BLOCK.get())
            .define('#', ModTags.OreDirectory.PROMETHIUM_INGOT)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_promethium_ingot", has(ModTags.OreDirectory.PROMETHIUM_INGOT))
            .save(this.output)
        shapeless(RecipeCategory.MISC, ModItems.PROMETHIUM_INGOT.get(), 9)
            .requires(ModTags.OreDirectory.PROMETHIUM_BLOCK)
            .unlockedBy("has_promethium_block", has(ModTags.OreDirectory.PROMETHIUM_BLOCK))
            .save(this.output, recipeKey("promethium_ingot_from_block"))

        shapeless(RecipeCategory.MISC, ModItems.STONE_BULLET.get())
            .requires(Items.STONE_BUTTON)
            .requires(Items.CLAY_BALL)
            .unlockedBy("has_stone_button", has(Items.STONE_BUTTON))
            .save(this.output)
        shapeless(RecipeCategory.MISC, ModItems.IRON_BULLET.get())
            .requires(Tags.Items.NUGGETS_IRON)
            .requires(Items.CLAY_BALL)
            .unlockedBy("has_iron_nugget", has(Tags.Items.NUGGETS_IRON))
            .save(this.output)
        shapeless(RecipeCategory.MISC, ModItems.NEPTUNIUM_BULLET.get())
            .requires(AquaItems.NEPTUNIUM_NUGGET.get())
            .requires(Items.CLAY_BALL)
            .requires(Items.PRISMARINE_CRYSTALS)
            .unlockedBy("has_neptunium_nugget", has(AquaItems.NEPTUNIUM_NUGGET.get()))
            .save(this.output)
        shapeless(RecipeCategory.MISC, ModItems.PROMETHIUM_BULLET.get())
            .requires(ModTags.OreDirectory.PROMETHIUM_NUGGET)
            .requires(Items.CLAY_BALL)
            .requires(Tags.Items.GUNPOWDERS)
            .unlockedBy("has_promethium_nugget", has(ModTags.OreDirectory.PROMETHIUM_NUGGET))
            .save(this.output)

        shapeless(RecipeCategory.MISC, ModItemsAqua.DOUBLE_OBSIDIAN_HOOK.get())
            .requires(ModItemsAqua.OBSIDIAN_HOOK.get(), 2)
            .unlockedBy("has_obsidian_hook", has(ModItemsAqua.OBSIDIAN_HOOK.get()))
            .save(this.output)
        shaped(RecipeCategory.MISC, ModItemsAqua.GLOWSTONE_HOOK.get())
            .define('G', Tags.Items.DUSTS_GLOWSTONE)
            .define('H', AquaItems.IRON_HOOK)
            .pattern(" G ")
            .pattern("GHG")
            .pattern(" G ")
            .unlockedBy("has_iron_hook", has(AquaItems.IRON_HOOK))
            .save(this.output)
        shapeless(RecipeCategory.MISC, ModItemsAqua.OBSIDIAN_HOOK.get())
            .requires(AquaItems.IRON_HOOK)
            .requires(Tags.Items.OBSIDIANS)
            .unlockedBy("has_iron_hook", has(AquaItems.IRON_HOOK))
            .save(this.output)
        shapeless(RecipeCategory.MISC, ModItemsAqua.OBSIDIAN_NOTE_HOOK.get())
            .requires(AquaItems.IRON_HOOK)
            .requires(Items.NOTE_BLOCK)
            .unlockedBy("has_iron_hook", has(AquaItems.IRON_HOOK))
            .save(this.output)
        shaped(RecipeCategory.MISC, ModItemsAqua.QUARTZ_HOOK.get())
            .define('Q', Tags.Items.GEMS_QUARTZ)
            .define('O', ModItemsAqua.OBSIDIAN_HOOK.get())
            .pattern(" Q ")
            .pattern("QOQ")
            .pattern(" Q ")
            .unlockedBy("has_obsidian_hook", has(ModItemsAqua.OBSIDIAN_HOOK.get()))
            .save(this.output)
        shaped(RecipeCategory.MISC, ModItemsAqua.SOUL_SAND_HOOK.get())
            .define('S', Items.SOUL_SAND)
            .define('O', ModItemsAqua.OBSIDIAN_HOOK.get())
            .pattern(" S ")
            .pattern("SOS")
            .pattern(" S ")
            .unlockedBy("has_obsidian_hook", has(ModItemsAqua.OBSIDIAN_HOOK.get()))
            .save(this.output)
    }

    private fun buildFood() {
        shapeless(RecipeCategory.FOOD, ModItems.SPICY_FISH_FILLET.get())
            .requires(Items.BLAZE_POWDER)
            .requires(Items.WEEPING_VINES)
            .requires(AquaItems.COOKED_FILLET)
            .requires(Items.BOWL)
            .unlockedBy("has_cooked_fillet", has(AquaItems.COOKED_FILLET))
            .save(this.output)
        shapeless(RecipeCategory.FOOD, ModItems.FISH_PASTE.get())
            .requires(AquaItems.FISH_FILLET, 9)
            .unlockedBy("has_fish_fillet", has(AquaItems.FISH_FILLET))
            .save(this.output)
    }

    private fun recipeKey(path: String) = ResourceKey.create(Registries.RECIPE, LavaFishing.identifier(path))

    class Runner(
        output: PackOutput,
        registries: CompletableFuture<HolderLookup.Provider>,
    ) : RecipeProvider.Runner(output, registries) {
        override fun createRecipeProvider(
            registries: HolderLookup.Provider,
            output: RecipeOutput,
        ): RecipeProvider = ModDataProviderRecipe(registries, output)

        override fun getName(): String = "Lava Fishing Recipes"
    }
}
