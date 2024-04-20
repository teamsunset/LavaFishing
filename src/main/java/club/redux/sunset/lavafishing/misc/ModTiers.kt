package club.redux.sunset.lavafishing.misc

import club.redux.sunset.lavafishing.registry.RegistryItem
import net.minecraft.tags.TagKey
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

    OBSIDIAN(0, 0, 2.0f, 0.0f, 15, { Ingredient.of(RegistryItem.OBSIDIAN_SWORD_FISH.get()) }, null),
    ;

    override fun getUses() = this.maxUses
    override fun getSpeed() = this.speed
    override fun getAttackDamageBonus() = this.attackDamage
    override fun getEnchantmentValue() = this.enchantmentValue
    override fun getRepairIngredient() = this.repairIngredientSupplier()
    override fun getTag() = this.tag

    @Deprecated("Use getLevel()", ReplaceWith("getLevel()"))
    override fun getLevel() = this.level
}
