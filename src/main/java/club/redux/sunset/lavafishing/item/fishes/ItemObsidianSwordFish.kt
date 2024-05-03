package club.redux.sunset.lavafishing.item.fishes

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemObsidianSwordFish : ItemLavaFish() {
    override fun finishUsingItem(pStack: ItemStack, pLevel: Level, pLivingEntity: LivingEntity): ItemStack {
        pLivingEntity.addEffect(MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300))
        return super.finishUsingItem(pStack, pLevel, pLivingEntity)
    }
}
