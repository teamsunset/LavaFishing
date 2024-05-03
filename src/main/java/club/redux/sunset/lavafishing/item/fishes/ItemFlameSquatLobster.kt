package club.redux.sunset.lavafishing.item.fishes

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemFlameSquatLobster : ItemLavaFish(SMALL_FISH_RAW) {
    override fun finishUsingItem(pStack: ItemStack, pLevel: Level, pLivingEntity: LivingEntity): ItemStack {
        pLivingEntity.addEffect(MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400))
        pLivingEntity.remainingFireTicks = 100
        return super.finishUsingItem(pStack, pLevel, pLivingEntity)
    }
}