package club.redux.sunset.lavafishing.entity

import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.util.Utils
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.Vec3
import kotlin.math.pow
import kotlin.math.sqrt

class EntityPromethiumBullet : AbstractArrow {
    var isCarriedFire = false
    var dividable: Boolean = false
    var divisionNum = 3
    var divisionTimes = 3
    val velocity: Double
        get() = sqrt(
            this.deltaMovement.x.pow(2.0) +
                    this.deltaMovement.y.pow(2.0) +
                    this.deltaMovement.z.pow(2.0)
        )

    init {
        this.setSoundEvent(SoundEvents.ALLAY_HURT)
    }


    constructor(
        world: Level,
        x: Double,
        y: Double,
        z: Double,
        dividable: Boolean = false,
        divisionNum: Int = 3,
        divisionTimes: Int = 3,
        isCarriedFire: Boolean = false,
    ) : super(ModEntityTypes.PROMETHIUM_BULLET.get(), x, y, z, world) {
        this.dividable = dividable
        this.divisionNum = divisionNum
        this.divisionTimes = divisionTimes
        this.isCarriedFire = isCarriedFire
    }

    constructor(arrow: EntityType<EntityPromethiumBullet>, world: Level) : super(arrow, world)

    constructor(
        world: Level,
        livingEntity: LivingEntity,
        division: Boolean = false,
        divisionTimes: Int = 3,
    ) : super(ModEntityTypes.PROMETHIUM_BULLET.get(), livingEntity, world) {
        this.dividable = division
        this.divisionTimes = divisionTimes
    }

    private val explode = { radius: Float ->
        this.level()
            .explode(
                this.owner,
                this.x,
                this.y,
                this.z,
                radius,
                this.isCarriedFire,
                Level.ExplosionInteraction.NONE
            )
    }
    private val newArrow = { division: Boolean, divisionNum: Int, divisionCount: Int ->
        EntityPromethiumBullet(
            this.level(),
            this.x,
            this.y,
            this.z,
            division,
            divisionNum,
            divisionCount,
            this.isCarriedFire
        ).apply {
            owner = this.owner
        }
    }

    override fun onHitEntity(pResult: EntityHitResult) {
        super.onHitEntity(pResult)
        if (this.dividable) {
            this.hitDivide()
        } else {
            this.explode(1.5f)
        }
        this.remove(RemovalReason.DISCARDED)
    }

    override fun onHitBlock(pResult: BlockHitResult) {
        super.onHitBlock(pResult)
        if (!this.dividable) {
            this.explode(1.5f)
            this.remove(RemovalReason.DISCARDED)
        } else if (this.xRot <= 0) {
            this.hitDivide()
            this.remove(RemovalReason.DISCARDED)
        }
    }

    override fun tick() {
        super.tick()
        if (this.dividable && this.divisionTimes <= 0) {
            this.remove(RemovalReason.DISCARDED)
        }
        if (this.dividable && this.divisionTimes > 0 && ((!this.inGround && velocity < 2 && this.deltaMovement.y in (-1.0..-0.3)) || (this.inGround && this.xRot > 0))) {
            this.explode(1f)
            this.divide(this.divisionNum, this.velocity)
            this.deltaMovement = Vec3(this.deltaMovement.x, 0.5, this.deltaMovement.z)
            this.divisionTimes--
        }
        if (!this.dividable && this.inGround) {
            this.explode(1.5f)
            this.remove(RemovalReason.DISCARDED)
        }
    }

    private fun divide(num: Int, velocity: Double, b: Double = 1.0) {
        Utils.generateArchimedianScrew(num, b).forEach { point ->
            this.level().addFreshEntity(newArrow(false, 0, 0).apply {
                deltaMovement = Vec3(point.first, -3.0 * velocity, point.second)
            })
        }
    }

    private fun hitDivide() {
        if (this.divisionTimes <= 1) {
            this.explode(1f)
            this.divide(this.divisionNum, -0.3, 0.5)
        } else {
            this.explode(2f)
            this.level().addFreshEntity(newArrow(true, this.divisionNum, this.divisionTimes - 1).apply {
                deltaMovement = Vec3(0.0, 1.0, 0.0)
            })
        }
    }


    override fun getDefaultHitGroundSoundEvent(): SoundEvent {
        return super.getDefaultHitGroundSoundEvent()
    }

    override fun getPickupItem(): ItemStack = ItemStack.EMPTY
}
