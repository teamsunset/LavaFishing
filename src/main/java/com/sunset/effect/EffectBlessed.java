package com.sunset.effect;

import com.sunset.util.RegistryCollections.EffectCollection;
import com.sunset.util.RegistryCollections.ParticleTypeCollection;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class EffectBlessed extends MobEffect
{
    public EffectBlessed() {
        super(MobEffectCategory.NEUTRAL, 0xCC3300);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.setRemainingFireTicks(20);
        pLivingEntity.heal(1f);
        pLivingEntity.setSharedFlagOnFire(true);
        if (pLivingEntity.isInWaterOrRain()) {
            pLivingEntity.hurt(DamageSource.ON_FIRE, 1.0f);

        }
        pLivingEntity.hurt(DamageSource.ON_FIRE, 5.0f);
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    public static void onEntityDamaged(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        LivingEntity target = event.getEntityLiving();
        if (source.getEntity() instanceof LivingEntity sourceEntity) {
            if (!source.isProjectile() && !source.isMagic() && sourceEntity.getEffect(EffectCollection.EFFECT_BLESSED) != null && sourceEntity.getMainHandItem().is(Items.AIR)) {
                target.addEffect(new MobEffectInstance(EffectCollection.EFFECT_ENDLESS_FLAME, 4800), sourceEntity);
                spawnHitParticle((ServerLevel) target.getLevel(), target.position());
            }
        }
    }

    private static void spawnHitParticle(ServerLevel level, Vec3 pos) {
//        level.addParticle(ParticleTypeCollection.PARTICLE_FIRE_PUNCH,
//                pos.x(), pos.y(), pos.z(),
//                0, 0, 0);
        level.sendParticles(ParticleTypeCollection.PARTICLE_FIRE_PUNCH, pos.x(), pos.y() + 1.3f, pos.z(),
                1, 0.3, 0.3, 0.3, 1);
    }
}
