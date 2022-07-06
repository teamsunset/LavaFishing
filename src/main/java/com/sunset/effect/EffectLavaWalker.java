package com.sunset.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class EffectLavaWalker extends MobEffect
{
    public EffectLavaWalker() {
        super(MobEffectCategory.BENEFICIAL, 0xCC3300);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        BlockPos onPos = pLivingEntity.getOnPos();
        if (pLivingEntity.level.getFluidState(onPos).is(FluidTags.LAVA)) {
            Vec3 movement = pLivingEntity.getDeltaMovement();
            Vec3 pos = pLivingEntity.position();
            if (pLivingEntity.isInLava()) {
                pLivingEntity.setDeltaMovement(movement.add(0, 0.1, 0));
            }
            else {
                pLivingEntity.setDeltaMovement(movement.x(), Math.max(movement.y(), 0.0D), movement.z());
            }
            pLivingEntity.setOnGround(true);
            if (pLivingEntity.level instanceof ServerLevel level) {
                level.sendParticles(ParticleTypes.WHITE_ASH, pos.x(), pos.y(), pos.z(), 10, 0.2, 0, 0.2, 1.5);
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
