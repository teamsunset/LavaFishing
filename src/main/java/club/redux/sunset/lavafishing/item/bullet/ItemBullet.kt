package club.redux.sunset.lavafishing.item.bullet


import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.ArrowItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level

open class ItemBullet(
    open val tier: Tier,
    properties: Properties,
    val entityTypeProvider: () -> EntityType<out EntityBullet>,
    val entityProvider: (EntityType<out EntityBullet>, LivingEntity, Level) -> EntityBullet,
) : ArrowItem(properties) {
    open val baseDamage = 0.5

    final override fun isInfinite(stack: ItemStack, bow: ItemStack, player: Player): Boolean {
        return EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow) > 0
    }


    /**
     * # 第一步
     *
     * 从Item中获取createArrow
     */
    @Deprecated("不建议用", ReplaceWith("this.createBullet(pLevel, pStack, pShooter)"))
    override fun createArrow(pLevel: Level, pStack: ItemStack, pShooter: LivingEntity): AbstractArrow {
        val bullet = this.createBullet(pLevel, pStack, pShooter)
        bullet.baseDamage = this.baseDamage * tier.attackDamageBonus
        return bullet
    }

    open fun createBullet(pLevel: Level, pStack: ItemStack, pShooter: LivingEntity): EntityBullet {
        return entityProvider(entityTypeProvider(), pShooter, pLevel)
    }
}