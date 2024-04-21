package club.redux.sunset.lavafishing.entity

import club.redux.sunset.lavafishing.util.Utils
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.Arrow
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.Vec3
import kotlin.math.abs
import kotlin.math.sqrt

class EntityPromethiumBullet : Arrow {
    private val divisionNum = 3
    private val explode = { radius: Float ->
        this.level().explode(this.owner, this.x, this.y, this.z, radius, false, Level.ExplosionInteraction.NONE)
    }
    private val newArrow = { division: Boolean, divisionCount: Int ->
        EntityPromethiumBullet(
            this.level(),
            this.x,
            this.y,
            this.z,
            division,
            divisionCount
        ).apply {
            owner = this.owner
        }
    }

    private var division: Boolean = false
    private var divisionCount = 3

    constructor(
        world: Level,
        x: Double,
        y: Double,
        z: Double,
        division: Boolean = false,
        divisionCount: Int = 3,
    ) : super(world, x, y, z) {
        this.division = division
        this.divisionCount = divisionCount
    }

    constructor(arrow: EntityType<out Arrow>, world: Level) : super(arrow, world)

    constructor(division: Boolean, world: Level, livingEntity: LivingEntity) : super(world, livingEntity) {
        this.division = division
    }

    override fun onHitEntity(pResult: EntityHitResult) {
        super.onHitEntity(pResult)
        this.explode(1.5f)
        this.level().addFreshEntity(newArrow(true, this.divisionCount - 1).apply {
            deltaMovement = Vec3(0.0, 1.0, 0.0)
        })
        pResult.entity.remainingFireTicks = 100
    }

    override fun onHitBlock(pResult: BlockHitResult) {
        super.onHitBlock(pResult)
        if (!this.division) {
            this.explode(1.5f)
            this.remove(RemovalReason.DISCARDED)
        } else if (this.xRot <= 0) {
            this.explode(1.5f)
            this.level().addFreshEntity(newArrow(true, this.divisionCount - 1).apply {
                deltaMovement = Vec3(0.0, 1.0, 0.0)
            })
        }
    }

    override fun tick() {
        super.tick()
        if (this.division && this.divisionCount <= 0) {
            this.remove(RemovalReason.DISCARDED)
        }

        if (this.division && this.divisionCount > 0 && (this.deltaMovement.y < -0.3 || (this.inGround && this.xRot > 0))) {
            this.explode(1f)
            val velocity = sqrt(abs(this.deltaMovement.x * this.deltaMovement.y + this.deltaMovement.z))

            Utils.generateArchimedianScrew(this.divisionNum).forEach { point ->
                this.level().addFreshEntity(newArrow(false, 0).apply {
                    deltaMovement = Vec3(point.first, -3.0 * velocity, point.second)
                })
            }
            this.deltaMovement = Vec3(this.deltaMovement.x, 1.0, this.deltaMovement.z)
            this.divisionCount--
        }
    }
}