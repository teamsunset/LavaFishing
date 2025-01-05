package club.redux.sunset.lavafishing.item.slingshot

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModSoundEvents
import club.redux.sunset.lavafishing.util.getEnchantmentLevel
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.BowItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
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

    override fun getAllSupportedProjectiles(stack: ItemStack): Predicate<ItemStack> =
        Predicate { pStack -> pStack.item is ItemBullet }

    override fun isValidRepairItem(pStack: ItemStack, pRepairCandidate: ItemStack): Boolean =
        this.tier.repairIngredient.test(pRepairCandidate)

    override fun getEnchantmentValue(stack: ItemStack): Int = this.tier.enchantmentValue

    override fun isPrimaryItemFor(stack: ItemStack, enchantment: Holder<Enchantment>): Boolean {
        return super.isPrimaryItemFor(stack, enchantment) || this.supportsEnchantment(stack, enchantment)
    }

    override fun supportsEnchantment(stack: ItemStack, enchantment: Holder<Enchantment>): Boolean {
        return (super.supportsEnchantment(stack, enchantment) ||
                listOf(
                    Enchantments.MULTISHOT,
                    Enchantments.QUICK_CHARGE
                ).any { it == enchantment.key }) &&
                listOf(
                    Enchantments.INFINITY
                ).any { it != enchantment.key }
    }

    /**
     * # 第二步
     *
     * 大概是forge的钩子，之前createArrow作为这里的参数传入
     * createArrow的结果可以在这里顶掉
     *
     * 仅用于兼容原版调用
     */
    @Deprecated("不建议用", ReplaceWith("this.customBullet(bullet)"))
    override fun customArrow(arrow: AbstractArrow, projectileStack: ItemStack, weaponStack: ItemStack): AbstractArrow {
        return this.customBullet(
            if (arrow is EntityBullet) arrow
            else ModItems.STONE_BULLET.get().createBullet(arrow.level()).apply {
                this.setPos(arrow.x, arrow.y, arrow.z)
                this.owner = arrow.owner
            }
        ).apply { attachEnchantmentEffects(weaponStack) }
    }

    open fun customBullet(bullet: EntityBullet): EntityBullet {
        return bullet
    }

    open fun getChargeMultiplier(stack: ItemStack): Int {
        return 1 + stack.getEnchantmentLevel(Enchantments.QUICK_CHARGE)
    }

    companion object {
        @JvmStatic
        fun onClientSetup(event: FMLClientSetupEvent) {
            event.enqueueWork {
                ModItems.REGISTER.entries.map { it.get() }.filterIsInstance<ItemSlingshot>().forEach { item ->
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

        const val BASE_DURABILITY = 1.5
    }
}