package com.sunset.effect;

import com.sunset.util.RegistryCollections.EffectCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EffectLavaWalker extends MobEffect
{
    public EffectLavaWalker() {
        super(MobEffectCategory.BENEFICIAL, 0xCC3300);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!(pLivingEntity instanceof Player player && player.isSpectator())) {
            Vec3 pos = pLivingEntity.position();
            Vec3 movement = pLivingEntity.getDeltaMovement();
            BlockPos onPos = pLivingEntity.getOnPos();
            BlockPos futurePos = new BlockPos(pos.add(movement));
            if (pLivingEntity.isInLava()) {
                if (pLivingEntity.level.isClientSide()) {
                    pLivingEntity.setDeltaMovement(movement.add(0, 0.1, 0));
                }
            }
            else if (pLivingEntity.level.getFluidState(onPos).is(FluidTags.LAVA)) {
                if (pLivingEntity.level instanceof ServerLevel level) {
                    level.sendParticles(ParticleTypes.WHITE_ASH, pos.x(), pos.y() + 0.1D, pos.z(), 10, 0.2, 0.1, 0.2, 1.5);
                }
                else {
                    pLivingEntity.setDeltaMovement(movement.x(), Math.max(movement.y(), 0D), movement.z());
                    pLivingEntity.setOnGround(true);
                }
            }
            else if (pLivingEntity.level.getFluidState(futurePos).is(FluidTags.LAVA) && movement.y() > -0.8) {
                if (pLivingEntity.level instanceof ServerLevel level) {
                    level.sendParticles(ParticleTypes.WHITE_ASH, pos.x(), pos.y() + 0.1D, pos.z(), 10, 0.2, 0.1, 0.2, 1.5);
                }
                else {
                    pLivingEntity.setDeltaMovement(movement.x(), Math.max(movement.y(), movement.y() * 0.5), movement.z());
                }
            }
            super.applyEffectTick(pLivingEntity, pAmplifier);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (event.getPlayer().hasEffect(EffectCollection.EFFECT_LAVA_WALKER) && event.getPlayer().level.getFluidState(event.getPlayer().getOnPos()).is(FluidTags.LAVA)) {
            event.setNewSpeed(event.getNewSpeed() * 5);
        }
    }
}
