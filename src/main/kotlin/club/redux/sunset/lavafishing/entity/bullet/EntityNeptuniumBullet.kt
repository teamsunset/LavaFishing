package club.redux.sunset.lavafishing.entity.bullet

import com.teammetallurgy.aquaculture.api.AquacultureAPI
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.MobType
import net.minecraft.world.entity.animal.WaterAnimal
import net.minecraft.world.entity.monster.Drowned
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import kotlin.math.max


open class EntityNeptuniumBullet(
    entityType: EntityType<out EntityNeptuniumBullet>,
    world: Level,
) : EntityBullet(entityType, world, AquacultureAPI.MATS.NEPTUNIUM) {
    val range = 10.0
    val baseTraceRate = 0.3
    private var currentTarget: LivingEntity? = null
    val predicate = { entity: Entity ->
        entity is Mob && (entity.mobType == MobType.WATER || entity is Drowned || entity is WaterAnimal)
    }

    init {
        this.waterInertia = 1.0F
    }


    override fun tick() {
        super.tick()

        if (this.isInWater) {
            val targets = this.getTargets(this.range + this.deltaMovement.length())

            if (this.inGround) {
                targets.minByOrNull(this::distanceTo)?.let { entity ->
                    this.inGround = false
                    this.deltaMovement = this.getDirection(entity).scale(1.5)
                }
                return
            }

            if (this.deltaMovement.length() < 0.8) {
                return
            }

            if (!targets.contains(this.currentTarget)) {
                this.currentTarget = targets.let { filteredTargets ->
                    filteredTargets.filter { this.distanceTo(it) < (this.range + this.deltaMovement.length()) / 3 }
                        .minByOrNull(this::distanceTo)
                        ?: filteredTargets.maxByOrNull { this.getDirection(it).dot(this.deltaMovement.normalize()) }
                }
            }

            this.currentTarget?.let {
                this.deltaMovement = this.trace(it)
            }
        }
    }

    private fun getTargets(radius: Double): List<LivingEntity> {
        return this.level().getEntities(this, this.boundingBox.inflate(radius)) { entity ->
            this.predicate(entity) && entity.isAlive &&
                    this.level().clip(
                        ClipContext(
                            this.position(),
                            entity.position(),
                            ClipContext.Block.COLLIDER,
                            ClipContext.Fluid.NONE,
                            this
                        )
                    ).type == HitResult.Type.MISS
        }
            .filter { this.distanceTo(it) < radius }
            .filter { !this.piercedEntityIds.contains(it.id) }
            .filter {
                this.inGround || this.deltaMovement == Vec3.ZERO || this.getDirection(it).dot(this.deltaMovement) > 0
            }
            .map { it as LivingEntity }
    }

    private fun getDirection(entity: Entity): Vec3 {
        return this.position().vectorTo(entity.position()).normalize()
    }

    private fun trace(entity: Entity): Vec3 {
        val directionVec = this.getDirection(entity)
        val movement = this.deltaMovement
        val velocity = movement.length()
        val p = 0.1
        val r = (-1 * this.distanceTo(entity) + this.range + velocity) * p
        return movement.normalize().add(directionVec.scale(max(r, this.baseTraceRate))).normalize().scale(velocity)
    }
}
