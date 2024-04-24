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

abstract class ItemBullet(
    val tier: Tier,
    properties: Properties,
) :
    ArrowItem(properties) {
    final override fun isInfinite(stack: ItemStack, bow: ItemStack, player: Player): Boolean {
        return EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow) > 0
    }

    abstract override fun createArrow(pLevel: Level, pStack: ItemStack, pShooter: LivingEntity): AbstractArrow
}