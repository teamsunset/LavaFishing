package club.redux.sunset.lavafishing.item.fishes

import club.redux.sunset.lavafishing.registry.ModMobEffects
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemAgniFish : ItemLavaFish() {
    override fun finishUsingItem(pStack: ItemStack, pLevel: Level, pLivingEntity: LivingEntity): ItemStack {
        pLivingEntity.addEffect(MobEffectInstance(ModMobEffects.ENDLESS_FLAME.get(), 2400))
        return super.finishUsingItem(pStack, pLevel, pLivingEntity)
    }
}