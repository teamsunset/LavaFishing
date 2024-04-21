package club.redux.sunset.lavafishing.item.fishes

import club.redux.sunset.lavafishing.registry.RegistryMobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemAgniFish : Item(
    Properties()
        .food(
            FoodProperties.Builder()
                .nutrition(1)
                .saturationMod(0.5f)
                .build()
        )
        .fireResistant()
) {
    override fun finishUsingItem(pStack: ItemStack, pLevel: Level, pLivingEntity: LivingEntity): ItemStack {
        pLivingEntity.addEffect(MobEffectInstance(RegistryMobEffect.BLESSED.get(), 2400))
        return super.finishUsingItem(pStack, pLevel, pLivingEntity)
    }
}