package club.redux.sunset.lavafishing.item.fishes

import club.redux.sunset.lavafishing.misc.ModTiers
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.SwordItem
import net.minecraft.world.level.Level

class ItemObsidianSwordFish : SwordItem(
    ModTiers.OBSIDIAN,
    2,
    2f,
    object : Properties() {
        override fun durability(pMaxDamage: Int): Properties {
            super.durability(0)
            stacksTo(64)
            return this
        }
    }.food(
        FoodProperties.Builder()
            .nutrition(3)
            .saturationMod(0.6f)
            .build()
    )
        .fireResistant()
        .setNoRepair()
) {
    override fun finishUsingItem(pStack: ItemStack, pLevel: Level, pLivingEntity: LivingEntity): ItemStack {
        pLivingEntity.addEffect(MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300))
        return super.finishUsingItem(pStack, pLevel, pLivingEntity)
    }
}
