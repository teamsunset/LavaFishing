package club.redux.sunset.lavafishing.entity.bullet

import com.teammetallurgy.aquaculture.api.AquacultureAPI
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.animal.fish.WaterAnimal
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.common.NeoForgeMod
import kotlin.math.max


open class EntityNeptuniumBullet(
    entityType: EntityType<EntityNeptuniumBullet>,
    level: Level,
) : EntityBullet(entityType, level, AquacultureAPI.MATS.NEPTUNIUM) {
    val range = 10.0
    val baseTraceRate = 0.3

    private var currentTarget: LivingEntity? = null

    val predicate = { entity: Entity ->
        entity is Mob && ((!entity.canDrownInFluidType(NeoForgeMod.WATER_TYPE.value())) || entity is WaterAnimal)
    }

    init {
        this.setWaterInertia(1.0F)
    }

    private fun getTargets(radius: Double): List<LivingEntity> {
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
        }
            .filter { this.distanceTo(it) < radius }
            .filter { !(this.piercingIgnoreEntityIds?.contains(it.id) ?: false) }
            .filter {
                this.isInGround || this.deltaMovement == Vec3.ZERO || this.getDirection(it).dot(this.deltaMovement) > 0
            }
            .map { it as LivingEntity }
    }

    override fun tick() {
        super.tick()

        if (this.isInWater) {
            val targets = this.getTargets(this.range + this.deltaMovement.length())
            if (this.isInGround) {
                targets.minByOrNull(this::distanceTo)?.let { entity ->
                    this.isInGround = false
                    this.deltaMovement = this.getDirection(entity).scale(1.5)
                }
                return
            }

            if (this.deltaMovement.length() < 0.8) return

            if (!targets.contains(this.currentTarget)) {
                this.currentTarget = targets.let { filteredTargets ->
                    filteredTargets.filter { this.distanceTo(it) < (this.range + this.deltaMovement.length()) / 3 }
                        .minByOrNull(this::distanceTo)
                        ?: filteredTargets.maxByOrNull { this.getDirection(it).dot(this.deltaMovement.normalize()) }
                        ?: return
                }
            }

            this.deltaMovement = this.trace(this.currentTarget!!)
        }
    }

    private fun getDirection(entity: Entity): Vec3 {
        return this.position().vectorTo(entity.position()).normalize()
    }

    private fun trace(entity: Entity): Vec3 {
        val movement = this.deltaMovement
        val velocity = movement.length()
        val directionVec = this.getDirection(entity)
        val p = 0.1
        val r = (-1 * this.distanceTo(entity) + this.range + velocity) * p
        return movement.normalize().add(directionVec.scale(max(r, this.baseTraceRate))).normalize().scale(velocity)
    }
}
