package club.redux.sunset.lavafishing.misc

import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.tags.BlockTags
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
    private val tag: TagKey<Block>,
) : Tier {

    OBSIDIAN(
        2,
        1500,
        7.0f,
        2.0f,
        9,
        { Ingredient.of(Items.OBSIDIAN) },
        BlockTags.INCORRECT_FOR_IRON_TOOL
    ),
    PROMETHIUM(
        4,
        2000,
        10.0f,
        4.0f,
        15,
        { Ingredient.of(ModItems.PROMETHIUM_INGOT.get()) },
        BlockTags.INCORRECT_FOR_NETHERITE_TOOL
    );

    override fun getUses() = this.maxUses
    override fun getSpeed() = this.speed
    override fun getAttackDamageBonus() = this.attackDamage
    override fun getEnchantmentValue() = this.enchantmentValue
    override fun getRepairIngredient() = this.repairIngredientSupplier()

    // 定义了哪些方块在使用该等级的工具时不会从中掉落物品
    override fun getIncorrectBlocksForDrops(): TagKey<Block> = this.tag
}
