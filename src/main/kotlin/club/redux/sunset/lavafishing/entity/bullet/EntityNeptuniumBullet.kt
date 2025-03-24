package club.redux.sunset.lavafishing.entity.bullet

import com.teammetallurgy.aquaculture.api.AquacultureAPI
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.animal.WaterAnimal
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.common.NeoForgeMod
import kotlin.math.log
import kotlin.math.max
import kotlin.math.pow


open class EntityNeptuniumBullet(
    entityType: EntityType<EntityNeptuniumBullet>,
    level: Level,
) : EntityBullet(entityType, level, AquacultureAPI.MATS.NEPTUNIUM) {
    val range = 10.0
    val baseTraceRate = 0.3

    val predicate = { entity: Entity ->
        entity is Mob && ((!entity.canDrownInFluidType(NeoForgeMod.WATER_TYPE.value())) || entity is WaterAnimal)
    }

    init {
        this.waterInertia = 1.0F
    }

    private fun getTarget(radius: Double): List<LivingEntity> {
        return this.level().getEntities(this, this.boundingBox.inflate(radius)) {
            this.predicate(it) && it.isAlive && this.level().clip(
                ClipContext(
                    this.position(),
                    it.position(),
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    this
                )
            ).type == HitResult.Type.MISS
        }.filter { this.distanceTo(it) < radius }.map { it as LivingEntity }
    }

    override fun tick() {
        super.tick()

        if (this.isInWater) {
            if (this.inGround) {
                this.getTarget(this.range).minByOrNull { this.distanceTo(it) }?.let { entity ->
                    this.inGround = false
                    this.deltaMovement = this.getDirection(entity).scale(2.0)
                }
            } else if (this.deltaMovement.length() > 0.8) {
                this.getTarget(this.range).filter { this.getDirection(it).dot(this.deltaMovement) > 0 }.let { targets ->
                    val target =
                        targets.filter { this.distanceTo(it) < this.range / 2 }.minByOrNull { this.distanceTo(it) }
                            ?: targets.maxByOrNull { this.getDirection(it).dot(this.deltaMovement.normalize()) }
                            ?: return
                    this.deltaMovement = this.trace(target)
                }
            }
        }
    }

    private fun getDirection(entity: Entity): Vec3 {
        return this.position().vectorTo(entity.position()).normalize()
    }

    private fun trace(entity: Entity): Vec3 {
        val velocity = this.deltaMovement.length()
        val directionVec = this.getDirection(entity)
        val p = 100
        val r = -1 * log(this.distanceTo(entity).toDouble() / 10, range.pow(2) / p + 1)

        return this.deltaMovement.add(directionVec.scale(max(r, baseTraceRate))).normalize().scale(velocity)
    }
}