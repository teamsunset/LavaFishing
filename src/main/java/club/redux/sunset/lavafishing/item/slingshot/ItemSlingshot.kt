package club.redux.sunset.lavafishing.item.slingshot

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModSoundEvents
import club.redux.sunset.lavafishing.util.UtilEnchantment
import club.redux.sunset.lavafishing.util.isServerSide
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.ArrowItem
import net.minecraft.world.item.BowItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraftforge.event.ForgeEventFactory
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import java.util.function.Predicate

open class ItemSlingshot(val tier: Tier, properties: Properties) : BowItem(properties) {

    /**
     * # 释放
     *
     * 抄原版的
     */
    override fun releaseUsing(pStack: ItemStack, pLevel: Level, pEntityLiving: LivingEntity, pTimeLeft: Int) {
        // 如果实体不是玩家则直接返回
        if (pEntityLiving !is Player) return

        // 检查实体是否有无限建造或无限箭矢的能力
        val instabuild = pEntityLiving.abilities.instabuild
        val infinity = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, pStack) > 0
        val hasFreeAmmo = instabuild || infinity

        // 获取物品的使用持续时间
        val duration = this.getUseDuration(pStack)

        // 尝试获取可用的发射物，如果不支持则使用空物品堆栈
        var itemStack =
            pEntityLiving.getProjectile(pStack).takeIf { allSupportedProjectiles.test(it) } ?: ItemStack.EMPTY

        // 触发箭矢松开事件，计算额外的使用时间
        val overtime = ForgeEventFactory.onArrowLoose(
            pStack, pLevel, pEntityLiving, duration - pTimeLeft, !itemStack.isEmpty || hasFreeAmmo
        )
        if (overtime < 0) return

        // 如果有免费弹药且当前发射物为空，则替换为默认子弹
        if (hasFreeAmmo && itemStack.isEmpty) {
            itemStack = ItemStack(ModItems.PROMETHIUM_BULLET.get())
        }

        // 如果经过筛选后发射物仍为空，则直接返回
        if (itemStack.isEmpty) return

        // 根据额外使用时间计算力量值，如果力量值过低，则不进行后续处理
        val timePower = getPowerForTime(overtime)
        if (timePower.toDouble() < 0.1) return

        // 仅在服务器端执行发射逻辑
        if (pLevel.isServerSide()) {
            // 创建箭矢实体并赋予初始属性
            val arrowItem = itemStack.item as ArrowItem
            val arrow = customArrow(arrowItem.createArrow(pLevel, itemStack, pEntityLiving)).apply {
                shootFromRotation(
                    pEntityLiving,
                    pEntityLiving.getXRot(),
                    pEntityLiving.getYRot(),
                    0.0f,
                    timePower * 3.0f,
                    1.0f
                )
            }

            // 如果力量值为最大，则设置有暴击尾迹
            if (timePower == 1.0f) {
                arrow.isCritArrow = true
            }

            // 绑定附魔效果到箭矢上
            this.attachEnchantmentToBullet(arrow as EntityPromethiumBullet, pStack)

            // 广播消耗耐久事件
            pStack.hurtAndBreak(1, pEntityLiving) { player ->
                player.broadcastBreakEvent(pEntityLiving.getUsedItemHand())
            }

            // 如果具有无限箭矢附魔，则设置箭矢为仅限创造模式拾取
            if (infinity) {
                arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY
            }

            // 将箭矢实体添加到游戏世界中
            pLevel.addFreshEntity(arrow)

            // 播放发射声音，声音强度随力量值变化
            pLevel.playSound(
                null,
                pEntityLiving.getX(),
                pEntityLiving.getY(),
                pEntityLiving.getZ(),
                ModSoundEvents.SLINGSHOT.get(),
                SoundSource.PLAYERS,
                1.0f,
                1.0f / (pLevel.getRandom().nextFloat() * 0.4f + 1.2f) + timePower * 0.5f
            )

            // 如果没有免费弹药，则消耗发射物
            if (!hasFreeAmmo) {
                itemStack.shrink(1)
                // 如果发射物耗尽，则从玩家库存中移除该物品
                if (itemStack.isEmpty) {
                    pEntityLiving.inventory.removeItem(itemStack)
                }
            }

            // 统计物品使用次数
            pEntityLiving.awardStat(Stats.ITEM_USED[this])
        }
    }

    override fun getAllSupportedProjectiles(): Predicate<ItemStack> =
        Predicate { pStack -> pStack.item is ItemBullet }

    override fun isValidRepairItem(pStack: ItemStack, pRepairCandidate: ItemStack): Boolean =
        this.tier.repairIngredient.test(pRepairCandidate)

    override fun getEnchantmentValue(stack: ItemStack): Int = this.tier.enchantmentValue

    override fun canApplyAtEnchantingTable(stack: ItemStack?, enchantment: Enchantment?): Boolean {
        return super.canApplyAtEnchantingTable(
            stack,
            enchantment
        ) || enchantment == Enchantments.MULTISHOT || enchantment == Enchantments.QUICK_CHARGE
    }

    override fun getUseDuration(pStack: ItemStack): Int {
        var duration = super.getUseDuration(pStack) * 0.5
        UtilEnchantment.hasThen(Enchantments.QUICK_CHARGE, pStack) { duration *= 1.0 / (1 + it) }
        return duration.toInt()
    }

    
    open fun attachEnchantmentToBullet(bullet: EntityBullet, stack: ItemStack) {
        UtilEnchantment.hasThen(Enchantments.POWER_ARROWS, stack) { bullet.baseDamage += it.toDouble() * 0.5 + 0.5 }
        UtilEnchantment.hasThen(Enchantments.PUNCH_ARROWS, stack) { bullet.knockback = it }
        UtilEnchantment.hasThen(Enchantments.FLAMING_ARROWS, stack) { bullet.setSecondsOnFire(100) }
    }

    companion object {
        @JvmStatic
        fun onClientSetup(event: FMLClientSetupEvent) {
            event.enqueueWork {
                for (item in ModItems.REGISTER.entries) {
                    ItemProperties.register(item.get(), ResourceLocation("pull")) { pStack, _, pEntity, _ ->
                        if (pEntity == null || pEntity.useItem != pStack) {
                            0f
                        } else {
                            (pStack.useDuration - pEntity.useItemRemainingTicks) / 20f
                        }
                    }
                    ItemProperties.register(item.get(), ResourceLocation("pulling")) { pStack, _, pEntity, _ ->
                        if (pEntity != null && pEntity.isUsingItem && pEntity.useItem === pStack) {
                            1f
                        } else {
                            0f
                        }
                    }
                }
            }
        }
    }
}