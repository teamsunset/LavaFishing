package com.sunset.lavafishing.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectEndlessFlame extends MobEffect
{
    public EffectEndlessFlame() {
        super(MobEffectCategory.HARMFUL, 0xCC3300);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.setRemainingFireTicks(20);
        pLivingEntity.setSharedFlagOnFire(true);
        if (pLivingEntity.isInWaterOrRain()) {
            pLivingEntity.hurt(pLivingEntity.damageSources().onFire(), 0.2f);
        }
        pLivingEntity.hurt(pLivingEntity.damageSources().onFire(), 5.0f);
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

}