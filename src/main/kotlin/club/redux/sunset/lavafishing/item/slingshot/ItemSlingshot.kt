package club.redux.sunset.lavafishing.item.slingshot

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModSoundEvents
import club.redux.sunset.lavafishing.util.UtilItemStack.getEnchantmentLevel
import club.redux.sunset.lavafishing.util.UtilProjectile.setShooter
import net.minecraft.core.Holder
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.entity.projectile.arrow.AbstractArrow
import net.minecraft.world.entity.projectile.arrow.ThrownTrident
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.WindCharge
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEgg
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownExperienceBottle
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownLingeringPotion
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownSplashPotion
import net.minecraft.world.item.*
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.neoforged.neoforge.event.EventHooks
import net.minecraft.world.phys.Vec3
import java.util.function.Predicate

open class ItemSlingshot(
    open val tier: ToolMaterial,
    properties: Properties,
) : BowItem(
    properties
        .durability((BASE_DURABILITY_MUTIPLIER * tier.durability).toInt())
        .repairable(tier.repairItems)
        .enchantable(tier.enchantmentValue),
) {

    /**
     * # 释放
     *
     * 抄原版的
     */
    override fun releaseUsing(
        pStack: ItemStack,
        pLevel: Level,
        pEntityLiving: LivingEntity,
        pTimeLeft: Int,
    ): Boolean {
        val player = pEntityLiving as? Player ?: return false
        val projectileStack = player.getProjectile(pStack)
        if (projectileStack.isEmpty) return false

        var chargeTime = (getUseDuration(pStack, player) - pTimeLeft) * getChargeMultiplier(pStack)
        chargeTime = EventHooks.onArrowLoose(pStack, pLevel, player, chargeTime, true)
        if (chargeTime < 0) return false

        val power = getPowerForTime(chargeTime)
        if (power < 0.1f) return false

        val projectiles = draw(pStack, projectileStack, player)
        if (pLevel is ServerLevel && projectiles.isNotEmpty()) {
            shoot(
                pLevel,
                player,
                player.usedItemHand,
                pStack,
                projectiles,
                power * 3.0f,
                1.0f,
                power == 1.0f,
                null,
            )
        }

        pLevel.playSound(
            null,
            player.x,
            player.y,
            player.z,
            ModSoundEvents.SLINGSHOT,
            SoundSource.PLAYERS,
            1.0f,
            1.0f / (pLevel.random.nextFloat() * 0.4f + 1.2f) + power * 0.5f,
        )
        player.awardStat(Stats.ITEM_USED[this])
        return true
    }

    override fun getAllSupportedProjectiles(): Predicate<ItemStack> = Predicate { stack ->
        stack.item is ItemBullet || SUPPORTED_PROJECTILES.keys.any { stack.item == it }
    }

//    override fun isPrimaryItemFor(stack: ItemStack, enchantment: Holder<Enchantment>): Boolean {
//        return super.isPrimaryItemFor(stack, enchantment) || this.supportsEnchantment(stack, enchantment)
//    }

    override fun supportsEnchantment(stack: ItemStack, enchantment: Holder<Enchantment>): Boolean {
        return super.supportsEnchantment(stack, enchantment) &&
                listOf(
                    Enchantments.MULTISHOT,
                    Enchantments.INFINITY
                ).all { it != enchantment.key }
    }

    public override fun createProjectile(
        pLevel: Level,
        pShooter: LivingEntity,
        pWeapon: ItemStack,
        pAmmo: ItemStack,
        pIsCrit: Boolean,
    ): Projectile = when (val ammoItem = pAmmo.item) {
        in SUPPORTED_PROJECTILES.keys -> SUPPORTED_PROJECTILES[pAmmo.item]!!(pLevel, pShooter, pAmmo)
        is ItemBullet -> ammoItem.createBullet(pLevel, pAmmo, pShooter, pWeapon).apply {
            if (pIsCrit) setCritArrow(true)
        }
        else -> super.createProjectile(pLevel, pShooter, pWeapon, pAmmo, pIsCrit)
    }

    override fun customArrow(pArrow: AbstractArrow, pAmmo: ItemStack, pWeapon: ItemStack) = this.customBullet(
        pArrow as? EntityBullet ?: ModItems.STONE_BULLET.get()
            .createBullet(pArrow.level(), pAmmo, pArrow.owner, pWeapon)
    )

    open fun customBullet(bullet: EntityBullet) = bullet
    open fun getChargeMultiplier(stack: ItemStack): Int = 1 + stack.getEnchantmentLevel(Enchantments.QUICK_CHARGE)

    companion object {
        val SUPPORTED_PROJECTILES: Map<Item, (Level, LivingEntity, ItemStack) -> Projectile> = mapOf(
            Items.SNOWBALL to { level, entity, stack -> Snowball(level, entity, stack) },
            Items.EGG to { level, entity, stack -> ThrownEgg(level, entity, stack) },
            Items.FIRE_CHARGE to { level, entity, _ ->
                SmallFireball(level, entity, Vec3.ZERO)
            },
            Items.WITHER_SKELETON_SKULL to { level, entity, _ ->
                WitherSkull(level, entity, Vec3.ZERO)
            },
            Items.EXPERIENCE_BOTTLE to { level, entity, stack -> ThrownExperienceBottle(level, entity, stack) },
            Items.TRIDENT to { level, entity, stack ->
                ThrownTrident(level, entity, stack.also {
                    (level as? ServerLevel)?.let { level -> it.hurtAndBreak(1, level, entity) {} }
                })
            },
            Items.ENDER_PEARL to { level, entity, stack -> ThrownEnderpearl(level, entity, stack) },
            Items.WIND_CHARGE to { level, entity, _ ->
                WindCharge(level, entity.x, entity.eyeY, entity.z, Vec3.ZERO).setShooter(entity)
            },
            Items.POTION to { level, entity, stack -> ThrownSplashPotion(level, entity, stack) },
            Items.SPLASH_POTION to { level, entity, stack -> ThrownSplashPotion(level, entity, stack) },
            Items.LINGERING_POTION to { level, entity, stack -> ThrownLingeringPotion(level, entity, stack) },
        )

        const val BASE_DURABILITY_MUTIPLIER = 0.5
    }
}
