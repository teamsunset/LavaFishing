package club.redux.sunset.lavafishing.tool.datagenerator

import net.minecraft.advancements.Criterion
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack


// This class is abstract because there is a lot of per-recipe-serializer logic.
// It serves the purpose of showing the common part of all (vanilla) recipe builders.
abstract class RecipeBuilderSimple // It is common for constructors to accept the result item stack.
// Alternatively, static builder methods are also possible.
    (
// Make the fields protected so our subclasses can use them.
    protected val result: ItemStack,
) : RecipeBuilder {
    protected val criteria = LinkedHashMap<String, Criterion<*>>()

    protected var group: String? = null

    // This method adds a criterion for the recipe advancement.
    override fun unlockedBy(name: String, criterion: Criterion<*>): RecipeBuilder {
        this.criteria[name] = criterion
        return this
    }

    // This method adds a recipe book group. If you do not want to use recipe book groups,
    // remove the this.group field and make this method no-op (i.e. return this).
    override fun group(group: String?): RecipeBuilderSimple {
        this.group = group
        return this
    }

    // Vanilla wants an Item here, not an ItemStack. You still can and should use the ItemStack
    // for serializing the recipes.
    override fun getResult(): Item = result.item
}