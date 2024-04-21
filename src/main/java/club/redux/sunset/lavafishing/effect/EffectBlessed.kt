package club.redux.sunset.lavafishing.effect

import club.redux.sunset.lavafishing.registry.RegistryMobEffect
import club.redux.sunset.lavafishing.registry.RegistryParticleType
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
        pLivingEntity.apply {
            remainingFireTicks = 20
            heal(0.4f)
            setSharedFlagOnFire(true)
            if (isInWaterOrRain) {
                hurt(damageSources().onFire(), 0.2f)
            }
            hurt(damageSources().onFire(), 0.1f)
        }

        super.applyEffectTick(pLivingEntity, pAmplifier)
    }

    override fun isDurationEffectTick(pDuration: Int, pAmplifier: Int): Boolean {
        return true
    }

    companion object {
        @JvmStatic
        fun onEntityDamage(event: LivingDamageEvent) {
            val source = event.source
            val target = event.entity
            val sourceEntity = source.entity

            if (sourceEntity !is LivingEntity) {
                return
            }

            if (
                !source.`is`(DamageTypes.MOB_PROJECTILE) &&
                !source.`is`(DamageTypes.MAGIC) &&
                sourceEntity.getEffect(RegistryMobEffect.BLESSED.get()) != null &&
                sourceEntity.mainHandItem.`is`(Items.AIR)
            ) {
                target.addEffect(
                    MobEffectInstance(RegistryMobEffect.ENDLESS_FLAME.get(), 1200),
                    sourceEntity
                )
                spawnHitParticle(target.level() as ServerLevel, target.position())
            }
        }

        private fun spawnHitParticle(level: ServerLevel, pos: Vec3) {
            level.sendParticles(
                RegistryParticleType.FIRE_PUNCH.get(), pos.x(), pos.y() + 1.3f, pos.z(),
                1, 0.3, 0.3, 0.3, 1.0
            )
        }
    }
}