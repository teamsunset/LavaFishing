package club.redux.sunset.lavafishing.effect

import club.redux.sunset.lavafishing.util.RegistryCollection.MobEffectCollection
import club.redux.sunset.lavafishing.util.RegistryCollection.ParticleTypeCollection
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.damagesource.DamageTypes
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Items
import net.minecraft.world.phys.Vec3
import net.minecraftforge.event.entity.living.LivingDamageEvent

class EffectBlessed : MobEffect(MobEffectCategory.NEUTRAL, 0xCC3300) {
    override fun applyEffectTick(pLivingEntity: LivingEntity, pAmplifier: Int) {
        pLivingEntity.remainingFireTicks = 20
        pLivingEntity.heal(0.4f)
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

    companion object {
        @JvmStatic
        fun onEntityDamaged(event: LivingDamageEvent) {
            val source = event.source
            val target = event.entity
            val sourceEntity = (source.entity as? LivingEntity) ?: return

            if (
                !source.`is`(DamageTypes.MOB_PROJECTILE) &&
                !source.`is`(DamageTypes.MAGIC) &&
                sourceEntity.getEffect(MobEffectCollection.EFFECT_BLESSED.get()) != null &&
                sourceEntity.mainHandItem.`is`(Items.AIR)
            ) {
                target.addEffect(
                    MobEffectInstance(MobEffectCollection.EFFECT_ENDLESS_FLAME.get(), 1200),
                    sourceEntity
                )
                spawnHitParticle(target.level() as ServerLevel, target.position())
            }
        }

        private fun spawnHitParticle(level: ServerLevel, pos: Vec3) {
            level.sendParticles(
                ParticleTypeCollection.PARTICLE_FIRE_PUNCH.get(), pos.x(), pos.y() + 1.3f, pos.z(),
                1, 0.3, 0.3, 0.3, 1.0
            )
        }
    }
}