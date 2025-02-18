package club.redux.sunset.lavafishing.entity.bullet

import com.teammetallurgy.aquaculture.api.AquacultureAPI
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.monster.Drowned
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.common.NeoForgeMod
import kotlin.math.log
import kotlin.math.max


open class EntityNeptuniumBullet(
    entityType: EntityType<out EntityNeptuniumBullet>,
    world: Level,
) : EntityBullet(entityType, world, AquacultureAPI.MATS.NEPTUNIUM) {
    val range = 10.0
    val baseTraceRate = 0.3

    val predicate = { entity: Entity ->
        entity is Mob && (entity.canDrownInFluidType(NeoForgeMod.WATER_TYPE.value()) || entity is Drowned)
    }

    init {
        this.waterInertia = 1.0F
    }


    override fun tick() {
        super.tick()
        val fishes = { radius: Double ->
            this.level().getEntities(this, this.boundingBox.inflate(radius)) { entity ->
                predicate(entity) && entity.isAlive &&
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
        }

        if (this.isInWater) {
            if (this.inGround) {
                fishes(this.range).minByOrNull { this.distanceTo(it) }?.let { entity ->
                    this.inGround = false
                    this.deltaMovement = this.getDirection(entity).scale(1.0)
                }
            } else if (this.deltaMovement.length() > 0.8) {
                fishes(this.range / 2).minByOrNull { this.distanceTo(it) }?.let {
                    this.deltaMovement = this.trace(it)
                } ?: fishes(this.range).maxByOrNull { this.getDirection(it).dot(this.trace(it).normalize()) }?.let {
                    this.deltaMovement = this.trace(it)
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
        val logRes = log(entity.distanceTo(this).toDouble(), range)

        return if (logRes == Double.NEGATIVE_INFINITY) {
            directionVec.scale(velocity)
        } else {
            this.deltaMovement.add(
                directionVec.scale(
                    max((-1 * logRes + 1) * 5 + baseTraceRate, baseTraceRate)
                )
            ).normalize().scale(velocity)
        }
    }
}