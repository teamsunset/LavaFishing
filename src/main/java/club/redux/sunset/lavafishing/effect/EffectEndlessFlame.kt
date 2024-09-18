package club.redux.sunset.lavafishing.effect

import club.redux.sunset.lavafishing.registry.ModMobEffects
import club.redux.sunset.lavafishing.registry.ModParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.damagesource.DamageTypes
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Items
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent

class EffectEndlessFlame : MobEffect(MobEffectCategory.HARMFUL, 0xCC3300) {

    override fun applyEffectTick(pLivingEntity: LivingEntity, pAmplifier: Int): Boolean {
        pLivingEntity.apply {
            remainingFireTicks = 20
            setSharedFlagOnFire(true)
            if (isInWaterOrRain) {
                hurt(damageSources().onFire(), 0.2f)
            }
            hurt(pLivingEntity.damageSources().onFire(), 0.1f)
        }

        return true
    }

    companion object {
        @JvmStatic
        fun onLivingDamagePre(event: LivingDamageEvent.Pre) {
            val source = event.source
            val target = event.entity
            val sourceEntity = source.entity

            if (sourceEntity !is LivingEntity) {
                return
            }

            if (
                !source.`is`(DamageTypes.MOB_PROJECTILE) &&
                !source.`is`(DamageTypes.MAGIC) &&
                sourceEntity.getEffect(ModMobEffects.ENDLESS_FLAME) != null &&
                target.getEffect(ModMobEffects.ENDLESS_FLAME) == null &&
                sourceEntity.mainHandItem.`is`(Items.AIR)
            ) {
                target.addEffect(
                    MobEffectInstance(ModMobEffects.ENDLESS_FLAME, 1200),
                    sourceEntity
                )
                val level = target.level()
                val pos = target.position()
                if (level is ServerLevel) {
                    level.sendParticles(
                        ModParticleTypes.FIRE_PUNCH.get(), pos.x(), pos.y() + 1.3f, pos.z(),
                        1, 0.3, 0.3, 0.3, 1.0
                    )
                }
            }
        }
    }
}