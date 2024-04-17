package club.redux.sunset.lavafishing.effect

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity

class EffectEndlessFlame : MobEffect(MobEffectCategory.HARMFUL, 0xCC3300) {
    override fun applyEffectTick(pLivingEntity: LivingEntity, pAmplifier: Int) {
        pLivingEntity.remainingFireTicks = 20
        pLivingEntity.setSharedFlagOnFire(true)
        if (pLivingEntity.isInWaterOrRain) {
            pLivingEntity.hurt(pLivingEntity.damageSources().onFire(), 0.2f)
        }
        pLivingEntity.hurt(pLivingEntity.damageSources().onFire(), 0.1f)
        super.applyEffectTick(pLivingEntity, pAmplifier)
    }

    override fun isDurationEffectTick(pDuration: Int, pAmplifier: Int): Boolean {
        return true
    }
}