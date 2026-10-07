package club.redux.sunset.lavafishing.effect

import club.redux.sunset.lavafishing.misc.LavaSwim
import club.redux.sunset.lavafishing.registry.ModMobEffects
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.FluidTags
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import net.neoforged.neoforge.event.tick.EntityTickEvent
import kotlin.math.max
import kotlin.math.pow

class EffectLavaWalker : MobEffect(MobEffectCategory.BENEFICIAL, 0xCC3300) {

    private fun sendParticles(level: Level, pos: Vec3) {
        if (level is ServerLevel) {
            level.sendParticles(
                ParticleTypes.WHITE_ASH, pos.x, pos.y + 0.1, pos.z, 10, 0.2, 0.1, 0.2, 1.5
            )
        }
    }

    // applyEffectTick only in server, so...
    private fun applyMovement(level: Level, pLivingEntity: LivingEntity) {
        if (!pLivingEntity.isSpectator && !pLivingEntity.isShiftKeyDown) {
            val pos = pLivingEntity.position()
            val movement = pLivingEntity.deltaMovement
            val onPos = pLivingEntity.onPos
            val futureBlockPos = BlockPos.containing(pos.add(movement.scale(1.5)))
            if (pLivingEntity.isInLava) {
                if (LavaSwim.isDirectionalSwimming(pLivingEntity)) return
                pLivingEntity.deltaMovement = movement.add(0.0, 0.1, 0.0)
            } else if (level.getFluidState(onPos).`is`(FluidTags.LAVA)) {
                this.sendParticles(level, pos)
                pLivingEntity.setDeltaMovement(movement.x(), max(movement.y(), 0.0), movement.z())
                pLivingEntity.setOnGround(true)
            } else if (level.getFluidState(futureBlockPos).`is`(FluidTags.LAVA) && movement.y() < 0) {
                this.sendParticles(level, pos)
                val multiplier = max(0.5, 20.0.pow(movement.y()))
                pLivingEntity.setDeltaMovement(movement.x(), max(movement.y(), movement.y() * multiplier), movement.z())
            }
        }
    }

    companion object {
        @JvmStatic
        fun onEntityTickPre(event: EntityTickEvent.Pre) {
            val entity = event.entity
            if (
                entity is LivingEntity &&
                (!entity.level().isClientSide || entity.isLocalInstanceAuthoritative) &&
                entity.hasEffect(ModMobEffects.LAVA_WALKER)
            ) {
                ModMobEffects.LAVA_WALKER.value().applyMovement(entity.level(), entity)
            }
        }

        @JvmStatic
        fun onBreakSpeed(event: PlayerEvent.BreakSpeed) {
            if (event.entity.hasEffect(ModMobEffects.LAVA_WALKER) && event.entity.level()
                    .getFluidState(event.entity.onPos).`is`(FluidTags.LAVA)
            ) {
                event.newSpeed *= 5
            }
        }
    }
}
