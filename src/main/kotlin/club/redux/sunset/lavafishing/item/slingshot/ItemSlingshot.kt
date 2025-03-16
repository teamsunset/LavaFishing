package club.redux.sunset.lavafishing.item.slingshot

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModSoundEvents
import club.redux.sunset.lavafishing.util.UtilItemStack.getEnchantmentLevel
import club.redux.sunset.lavafishing.util.UtilProjectile.setShooter
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.*
import net.minecraft.world.entity.projectile.windcharge.WindCharge
import net.minecraft.world.item.*
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.event.EventHooks
import java.util.function.Predicate

open class ItemSlingshot(
    open val tier: Tier,
    properties: Properties,
) : BowItem(properties.durability((BASE_DURABILITY * tier.uses).toInt())) {

    /**
     * # 释放
     *
     * 抄原版的
     */
    override fun releaseUsing(pStack: ItemStack, pLevel: Level, pEntityLiving: LivingEntity, pTimeLeft: Int) {
        if (pEntityLiving is Player) {
            //这里居然是用事件获取弹射物的
            val itemStack: ItemStack = pEntityLiving.getProjectile(pStack)
            if (!itemStack.isEmpty) {
                var overtime: Int = this.getUseDuration(pStack, pEntityLiving) - pTimeLeft
                overtime *= this.getChargeMultiplier(pStack)
                overtime = EventHooks.onArrowLoose(pStack, pLevel, pEntityLiving, overtime, !itemStack.isEmpty)

                if (overtime < 0) return

                val f = getPowerForTime(overtime)
                if (!(f.toDouble() < 0.1)) {
                    val list = draw(pStack, itemStack, pEntityLiving)
                    if (pLevel is ServerLevel) {
                        val serverLevel = pLevel as ServerLevel
                        if (list.isNotEmpty()) {
                            this.shoot(
                                serverLevel,
                                pEntityLiving,
                                pEntityLiving.getUsedItemHand(),
                                pStack,
                                list,
                                f * 3.0f,
                                1.0f,
                                f == 1.0f,
                                null
                            )
                        }
                    }

                    pLevel.playSound(
                        null,
                        pEntityLiving.getX(),
                        pEntityLiving.getY(),
                        pEntityLiving.getZ(),
                        ModSoundEvents.SLINGSHOT,
                        SoundSource.PLAYERS,
                        1.0f,
                        1.0f / (pLevel.getRandom().nextFloat() * 0.4f + 1.2f) + f * 0.5f
                    )
                    pEntityLiving.awardStat(Stats.ITEM_USED[this])
                }
            }
        }
    }

    override fun getAllSupportedProjectiles(pStack: ItemStack): Predicate<ItemStack> = Predicate { stack ->
        stack.item is ItemBullet || SUPPORTED_PROJECTILES.keys.any { stack.item == it }
    }

    override fun isValidRepairItem(pStack: ItemStack, pRepairCandidate: ItemStack) =
        this.tier.repairIngredient.test(pRepairCandidate)

    override fun getEnchantmentValue(stack: ItemStack) = this.tier.enchantmentValue

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
    ): Projectile = when (pAmmo.item) {
        in SUPPORTED_PROJECTILES.keys -> SUPPORTED_PROJECTILES[pAmmo.item]!!(pLevel, pShooter, pAmmo)
        else -> super.createProjectile(pLevel, pShooter, pWeapon, pAmmo, pIsCrit)
    }

    override fun customArrow(pArrow: AbstractArrow, pAmmo: ItemStack, pWeapon: ItemStack) = this.customBullet(
        if (pArrow is EntityBullet) pArrow
        else ModItems.STONE_BULLET.get().createBullet(pArrow.level(), pAmmo, pArrow.owner, pWeapon)
    ).apply { attachEnchantmentEffects(pWeapon) }

    open fun customBullet(bullet: EntityBullet) = bullet
    open fun getChargeMultiplier(stack: ItemStack): Int = 1 + stack.getEnchantmentLevel(Enchantments.QUICK_CHARGE)

    companion object {
        val SUPPORTED_PROJECTILES: Map<Item, (Level, LivingEntity, ItemStack) -> Projectile> = mapOf(
            Items.SNOWBALL to { level, entity, _ -> Snowball(level, entity) },
            Items.EGG to { level, entity, _ -> ThrownEgg(level, entity) },
            Items.FIRE_CHARGE to { level, entity, _ ->
                SmallFireball(EntityType.SMALL_FIREBALL, level).setShooter(entity)
            },
            Items.WITHER_SKELETON_SKULL to { level, entity, _ ->
                WitherSkull(EntityType.WITHER_SKULL, level).setShooter(entity)
            },
            Items.EXPERIENCE_BOTTLE to { level, entity, _ -> ThrownExperienceBottle(level, entity) },
            Items.TRIDENT to { level, entity, stack ->
                ThrownTrident(level, entity, stack.also {
                    (level as? ServerLevel)?.let { level -> it.hurtAndBreak(1, level, entity) {} }
                })
            },
            Items.ENDER_PEARL to { level, entity, _ -> ThrownEnderpearl(level, entity) },
            Items.WIND_CHARGE to { level, entity, _ ->
                WindCharge(EntityType.WIND_CHARGE, level).setShooter(entity)
            },
            Items.POTION to { level, entity, stack -> ThrownPotion(level, entity).apply { item = stack } },
            Items.SPLASH_POTION to { level, entity, stack -> ThrownPotion(level, entity).apply { item = stack } },
            Items.LINGERING_POTION to { level, entity, stack -> ThrownPotion(level, entity).apply { item = stack } },
        )

        @JvmStatic
        fun onClientSetup(event: FMLClientSetupEvent) {
            event.enqueueWork {
                ModItems.getEntriesIsInstance<ItemSlingshot>().forEach { item ->
                    ItemProperties.register(
                        item,
                        ResourceLocation.withDefaultNamespace("pull")
                    ) { pStack, _, pEntity, _ ->
                        if (pEntity == null || pEntity.useItem != pStack) {
                            0f
                        } else {
                            (pStack.getUseDuration(pEntity) - pEntity.useItemRemainingTicks) / 20f * item.getChargeMultiplier(
                                pStack
                            )
                        }
                    }
                    ItemProperties.register(
                        item,
                        ResourceLocation.withDefaultNamespace("pulling")
                    ) { pStack, _, pEntity, _ ->
                        if (pEntity != null && pEntity.isUsingItem && pEntity.useItem === pStack) {
                            1f
                        } else {
                            0f
                        }
                    }
                }
            }
        }

        const val BASE_DURABILITY = 0.5
    }
}