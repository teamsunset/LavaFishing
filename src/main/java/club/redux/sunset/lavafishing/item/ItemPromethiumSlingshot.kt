package club.redux.sunset.lavafishing.item

import club.redux.sunset.lavafishing.entity.EntityPromethiumBullet
import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.*
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraftforge.event.ForgeEventFactory
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import java.util.function.Predicate

class ItemPromethiumSlingshot(private val tier: Tier) : BowItem(Properties().durability(3000)) {
    override fun customArrow(arrowEntity: AbstractArrow): AbstractArrow {
        if (arrowEntity.owner is LivingEntity) {
            return EntityPromethiumBullet(arrowEntity.level(), arrowEntity.owner as LivingEntity, true, 1)
        }

        return super.customArrow(arrowEntity)
    }

    /**
     * # 释放
     *
     * 抄原版的
     */
    override fun releaseUsing(pStack: ItemStack, pLevel: Level, pEntityLiving: LivingEntity, pTimeLeft: Int) {
        if (pEntityLiving is Player) {
            val flag = pEntityLiving.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(
                Enchantments.INFINITY_ARROWS,
                pStack
            ) > 0
            var itemstack = pEntityLiving.getProjectile(pStack)

            var i = this.getUseDuration(pStack) - pTimeLeft
            i = ForgeEventFactory.onArrowLoose(pStack, pLevel, pEntityLiving, i, !itemstack.isEmpty || flag)
            if (i < 0) return

            if (!itemstack.isEmpty || flag) {
                if (itemstack.isEmpty) {
                    itemstack = ItemStack(Items.ARROW)
                }

                val f = getPowerForTime(i)
                if (!(f.toDouble() < 0.1)) {
                    val flag1 =
                        pEntityLiving.abilities.instabuild || (itemstack.item is ArrowItem && (itemstack.item as ArrowItem).isInfinite(
                            itemstack,
                            pStack,
                            pEntityLiving
                        ))
                    if (!pLevel.isClientSide) {
                        val arrowitem = (if (itemstack.item is ArrowItem) itemstack.item else Items.ARROW) as ArrowItem
                        val abstractarrow = arrowitem.createArrow(pLevel, itemstack, pEntityLiving)
                        val arrow = customArrow(abstractarrow) as EntityPromethiumBullet // Modified
                        arrow.shootFromRotation(
                            pEntityLiving,
                            pEntityLiving.getXRot(),
                            pEntityLiving.getYRot(),
                            0.0f,
                            f * 3.0f,
                            1.0f
                        )
                        if (f == 1.0f) {
                            arrow.isCritArrow = true
                        }

                        val j = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, pStack)
                        if (j > 0) {
                            arrow.baseDamage = arrow.baseDamage + j.toDouble() * 0.5 + 0.5
                            arrow.divisionNum += j
                        }

                        val k = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PUNCH_ARROWS, pStack)
                        if (k > 0) {
                            arrow.knockback = k
                        }

                        if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FLAMING_ARROWS, pStack) > 0) {
                            arrow.setSecondsOnFire(100)
                            arrow.isCarriedFire = true
                        }

                        if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.MULTISHOT, pStack) > 0) {
                            arrow.divisionTimes = 3
                        }

                        pStack.hurtAndBreak(
                            1,
                            pEntityLiving
                        ) { player -> player.broadcastBreakEvent(pEntityLiving.getUsedItemHand()) }
                        if (flag1 || pEntityLiving.abilities.instabuild && (itemstack.`is`(Items.SPECTRAL_ARROW) || itemstack.`is`(
                                Items.TIPPED_ARROW
                            ))
                        ) {
                            arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY
                        }

                        pLevel.addFreshEntity(arrow)
                    }

                    pLevel.playSound(
                        null,
                        pEntityLiving.getX(),
                        pEntityLiving.getY(),
                        pEntityLiving.getZ(),
                        SoundEvents.ARROW_SHOOT,
                        SoundSource.PLAYERS,
                        1.0f,
                        1.0f / (pLevel.getRandom().nextFloat() * 0.4f + 1.2f) + f * 0.5f
                    )
                    if (!flag1 && !pEntityLiving.abilities.instabuild) {
                        itemstack.shrink(1)
                        if (itemstack.isEmpty) {
                            pEntityLiving.inventory.removeItem(itemstack)
                        }
                    }

                    pEntityLiving.awardStat(Stats.ITEM_USED[this])
                }
            }
        }
    }

    override fun canApplyAtEnchantingTable(stack: ItemStack?, enchantment: Enchantment?): Boolean {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.MULTISHOT
    }

    override fun getAllSupportedProjectiles(): Predicate<ItemStack> {
        return Predicate { pStack -> pStack.`is`(ModItems.PROMETHIUM_BULLET.get()) }
    }

    override fun isValidRepairItem(pStack: ItemStack, pRepairCandidate: ItemStack): Boolean =
        this.tier.repairIngredient.test(pRepairCandidate)

    override fun getEnchantmentValue(stack: ItemStack): Int = this.tier.enchantmentValue


    companion object {
        @JvmStatic
        fun setupClient(event: FMLClientSetupEvent) {
            event.enqueueWork {
                ItemProperties.register(
                    ModItems.PROMETHIUM_SLINGSHOT.get(),
                    ResourceLocation("pull")
                ) { pStack, pLevel, pEntity, pSeed ->
                    if (pEntity == null || pEntity.useItem != pStack) {
                        0f
                    } else {
                        (pStack.useDuration - pEntity.useItemRemainingTicks) / 20f
                    }
                }
                ItemProperties.register(
                    ModItems.PROMETHIUM_SLINGSHOT.get(),
                    ResourceLocation("pulling")
                ) { pStack, pLevel, pEntity, pSeed ->
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