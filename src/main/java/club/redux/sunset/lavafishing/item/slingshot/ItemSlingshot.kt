package club.redux.sunset.lavafishing.item.slingshot

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModSoundEvents
import club.redux.sunset.lavafishing.util.isServerSide
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.AbstractArrow
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

open class ItemSlingshot(
    open val tier: Tier,
    properties: Properties,
) : BowItem(properties.defaultDurability((BASE_DURABILITY * tier.uses).toInt())) {

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
        var overtime = duration - pTimeLeft

        // 加速拉弓
        overtime *= this.getChargeMultiplier(pStack)

        overtime = ForgeEventFactory.onArrowLoose(
            pStack, pLevel, pEntityLiving, overtime, !itemStack.isEmpty || hasFreeAmmo
        )
        if (overtime < 0) return

        // 如果有免费弹药且当前发射物为空，则替换为默认子弹
        if (hasFreeAmmo && itemStack.isEmpty) {
            itemStack = ItemStack(ModItems.STONE_BULLET.get())
        }

        // 如果经过筛选后发射物仍为空，则直接返回
        if (itemStack.isEmpty) return

        // 根据额外使用时间计算力量值，如果力量值过低，则不进行后续处理
        val timePower = getPowerForTime(overtime)
        if (timePower.toDouble() < 0.1) return

        // 仅在服务器端执行发射逻辑
        if (pLevel.isServerSide()) {
            // 创建弹丸实体并赋予初始属性
            val itemBullet = itemStack.item as ItemBullet
            val bullet = customBullet(itemBullet.createBullet(pLevel, itemStack, pEntityLiving)).apply {
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
                bullet.isCritArrow = true
            }

            // 绑定附魔效果到箭矢上
            bullet.attachEnchantment(pStack)

            // 广播消耗耐久事件
            pStack.hurtAndBreak(1, pEntityLiving) { player ->
                player.broadcastBreakEvent(pEntityLiving.getUsedItemHand())
            }

            // 如果具有无限箭矢附魔，则设置箭矢为仅限创造模式拾取
            if (infinity) {
                bullet.pickup = AbstractArrow.Pickup.CREATIVE_ONLY
            }

            // 将箭矢实体添加到游戏世界中
            pLevel.addFreshEntity(bullet)

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

    override fun getAllSupportedProjectiles(): Predicate<ItemStack> = Predicate { pStack -> pStack.item is ItemBullet }

    override fun isValidRepairItem(pStack: ItemStack, pRepairCandidate: ItemStack): Boolean =
        this.tier.repairIngredient.test(pRepairCandidate)

    override fun getEnchantmentValue(stack: ItemStack): Int = this.tier.enchantmentValue

    override fun canApplyAtEnchantingTable(stack: ItemStack?, enchantment: Enchantment?): Boolean {
        return (super.canApplyAtEnchantingTable(stack, enchantment) ||
                enchantment == Enchantments.MULTISHOT ||
                enchantment == Enchantments.QUICK_CHARGE) &&
                enchantment != Enchantments.INFINITY_ARROWS
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
    override fun customArrow(arrow: AbstractArrow): AbstractArrow {
        return this.customBullet(
            if (arrow is EntityBullet) arrow
            else ModItems.STONE_BULLET.get().createBullet(arrow.level()).apply {
                this.setPos(arrow.x, arrow.y, arrow.z)
                this.owner = arrow.owner
            }
        )
    }

    open fun customBullet(bullet: EntityBullet): EntityBullet {
        return bullet
    }

    open fun getChargeMultiplier(stack: ItemStack): Int {
        return 1 + EnchantmentHelper.getTagEnchantmentLevel(Enchantments.QUICK_CHARGE, stack)
    }

    companion object {
        @JvmStatic
        fun onClientSetup(event: FMLClientSetupEvent) {
            event.enqueueWork {
                ModItems.REGISTER.entries.map { it.get() }.filterIsInstance<ItemSlingshot>().forEach { item ->
                    ItemProperties.register(item, ResourceLocation("pull")) { pStack, _, pEntity, _ ->
                        if (pEntity == null || pEntity.useItem != pStack) {
                            0f
                        } else {
                            (pStack.useDuration - pEntity.useItemRemainingTicks) / 20f * item.getChargeMultiplier(pStack)
                        }
                    }
                    ItemProperties.register(item, ResourceLocation("pulling")) { pStack, _, pEntity, _ ->
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