package club.redux.sunset.lavafishing.misc

import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Items
import net.minecraft.world.item.Tier
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Block

enum class ModTiers(
    private val level: Int,
    private val maxUses: Int,
    private val speed: Float,
    private val attackDamage: Float,
    private val enchantmentValue: Int,
    private val repairIngredientSupplier: () -> Ingredient,
    private val tag: TagKey<Block>?,
) : Tier {

    OBSIDIAN(2, 1500, 7.0f, 3.0f, 9, { Ingredient.of(Items.OBSIDIAN) }, null),
    PROMETHIUM(4, 2000, 10.0f, 5.0f, 15, { Ingredient.of(ModItems.PROMETHIUM_INGOT.get()) }, null);

    override fun getUses() = this.maxUses
    override fun getSpeed() = this.speed
    override fun getAttackDamageBonus() = this.attackDamage
    override fun getEnchantmentValue() = this.enchantmentValue
    override fun getRepairIngredient() = this.repairIngredientSupplier()
    override fun getTag() = this.tag

    @Deprecated("Use getLevel()", ReplaceWith("getLevel()"))
    override fun getLevel() = this.level
}
