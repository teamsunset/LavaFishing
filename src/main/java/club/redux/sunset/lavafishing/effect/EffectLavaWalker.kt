package club.redux.sunset.lavafishing.effect

import club.redux.sunset.lavafishing.registry.RegistryMobEffect
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.FluidTags
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed
import kotlin.math.max

class EffectLavaWalker : MobEffect(MobEffectCategory.BENEFICIAL, 0xCC3300) {

    override fun applyEffectTick(pLivingEntity: LivingEntity, pAmplifier: Int) {
        if (!pLivingEntity.isSpectator) {
            val pos = pLivingEntity.position()
            val movement = pLivingEntity.deltaMovement
            val futurePos = pos.add(movement)
            val onPos = pLivingEntity.onPos
            val futureBlockPos = BlockPos(futurePos.x.toInt(), futurePos.y.toInt(), futurePos.z.toInt())
            val level = pLivingEntity.level()

            if (pLivingEntity.isInLava) {
                pLivingEntity.deltaMovement = movement.add(0.0, 0.1, 0.0)
            } else if (level.getFluidState(onPos).`is`(FluidTags.LAVA)) {
                if (level is ServerLevel) {
                    level.sendParticles(
                        ParticleTypes.WHITE_ASH,
                        pos.x(),
                        pos.y() + 0.1,
                        pos.z(),
                        10,
                        0.2,
                        0.1,
                        0.2,
                        1.5
                    )
                }

                pLivingEntity.setDeltaMovement(movement.x(), max(movement.y(), 0.0), movement.z())
                pLivingEntity.setOnGround(true)
            } else if (pLivingEntity.level().getFluidState(futureBlockPos)
                    .`is`(FluidTags.LAVA) && movement.y() > -0.8
            ) {
                if (level is ServerLevel) {
                    level.sendParticles(
                        ParticleTypes.WHITE_ASH,
                        pos.x(),
                        pos.y() + 0.1,
                        pos.z(),
                        10,
                        0.2,
                        0.1,
                        0.2,
                        1.5
                    )
                }

                pLivingEntity.setDeltaMovement(movement.x(), max(movement.y(), movement.y() * 0.5), movement.z())
            }
            super.applyEffectTick(pLivingEntity, pAmplifier)
        }
    }

    override fun isDurationEffectTick(pDuration: Int, pAmplifier: Int): Boolean {
        return true
    }

    companion object {
        @JvmStatic
        fun onBreakSpeed(event: BreakSpeed) {
            if (event.entity.hasEffect(RegistryMobEffect.LAVA_WALKER.get()) && event.entity.level()
                    .getFluidState(event.entity.onPos).`is`(FluidTags.LAVA)
            ) {
                event.newSpeed *= 5
            }
        }
    }
}