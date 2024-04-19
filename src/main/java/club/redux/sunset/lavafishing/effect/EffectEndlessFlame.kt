package club.redux.sunset.lavafishing.effect

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity

class EffectEndlessFlame : MobEffect(MobEffectCategory.HARMFUL, 0xCC3300) {

    override fun applyEffectTick(pLivingEntity: LivingEntity, pAmplifier: Int) {
        pLivingEntity.apply {
            remainingFireTicks = 20
            setSharedFlagOnFire(true)
            if (isInWaterOrRain) {
                hurt(damageSources().onFire(), 0.2f)
            }
            hurt(pLivingEntity.damageSources().onFire(), 0.1f)
        }
        
        super.applyEffectTick(pLivingEntity, pAmplifier)
    }

    override fun isDurationEffectTick(pDuration: Int, pAmplifier: Int): Boolean {
        return true
    }
}