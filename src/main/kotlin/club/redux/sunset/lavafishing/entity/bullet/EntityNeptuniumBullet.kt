package club.redux.sunset.lavafishing.entity.bullet

import club.redux.sunset.lavafishing.util.UtilEntity.getDirectionTo
import club.redux.sunset.lavafishing.util.UtilMath
import club.redux.sunset.lavafishing.util.UtilVec3.rotateToward
import com.teammetallurgy.aquaculture.api.AquacultureAPI
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.animal.fish.WaterAnimal
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.storage.ValueInput
import net.minecraft.world.level.storage.ValueOutput
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

    val homingDurationTicks = 20 * 3

    val maxTurnRadians = 20 * UtilMath.DEG

    private var waterPhaseTicks: Int
        get() = this.entityData.get(DATA_WATER_PHASE_TICKS)
        set(value) = this.entityData.set(DATA_WATER_PHASE_TICKS, value)

    private var prevOnGround = false
    private var prevInWater = false

    private var currentTarget: LivingEntity? = null

    val predicate = { entity: Entity ->
        entity is Mob && ((!entity.canDrownInFluidType(NeoForgeMod.WATER_TYPE.value())) || entity is WaterAnimal)
    }

    init {
        this.setWaterInertia(1.0F)
    }

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
        super.defineSynchedData(builder)
        builder.define(DATA_TARGET_ID, -1)
        builder.define(DATA_WATER_PHASE_TICKS, 0)
    }

    override fun tick() {
        if (!this.level().isClientSide) {
            this.serverHomingTick()
        } else {
            this.clientVisualHoming()
        }
        // Preserve the state observed by advancePhase, before vanilla updates fluids or collisions.
        this.prevOnGround = this.isInGround
        this.prevInWater = this.isInWater
        super.tick()
    }

    /** 客户端按同步来的目标 id 做本地视觉追踪，与服务端算法一致 */
    private fun clientVisualHoming() {
        if (this.isInGround || !this.isInWater) return
        if (this.phaseProgress() >= 1.0) return
        if (this.deltaMovement.length() < 0.8) return
        val targetId = this.entityData.get(DATA_TARGET_ID)
        if (targetId < 0) return
        val target = this.level().getEntity(targetId)
        if (target !is LivingEntity) return
        this.deltaMovement = this.trace(target)
    }

    private fun serverHomingTick() {
        this.advancePhase(this.prevOnGround)

        if (this.isInGround) {
            this.guardTick()
            return
        }

        if (!this.isInWater) return
        if (this.phaseProgress() >= 1.0) return // 追踪能力归零，纯弹道
        if (this.deltaMovement.length() < 0.8) return

        val targets = this.getTargets(this.range + this.deltaMovement.length())
        if (!targets.contains(this.currentTarget)) {
            val target = targets.let { filteredTargets ->
                filteredTargets.filter { this.distanceTo(it) < (this.range + this.deltaMovement.length()) / 3 }
                    .minByOrNull(this::distanceTo)
                    ?: filteredTargets.maxByOrNull { this.getDirectionTo(it).dot(this.deltaMovement.normalize()) }
                    ?: run {
                        this.syncTarget(null)
                        return
                    }
            }
            this.syncTarget(target)
        }

        this.deltaMovement = this.trace(this.currentTarget!!)
    }

    /** 守卫模式：落地等待，目标进入范围后弹射并重置阶段 */
    private fun guardTick() {
        if (!this.isInWater) return
        this.getTargets(this.range).minByOrNull(this::distanceTo)?.let { entity ->
            this.isInGround = false
            this.deltaMovement = this.getDirectionTo(entity).scale(1.5)
            this.waterPhaseTicks = 0
            this.syncTarget(entity)
        }
    }

    /** 目标变更时写入同步字段并主动触发一次位置+速度同步 */
    private fun syncTarget(target: LivingEntity?) {
        this.currentTarget = target
        val newId = target?.id ?: -1
        if (this.entityData.get(DATA_TARGET_ID) != newId) {
            this.entityData.set(DATA_TARGET_ID, newId)
            this.needsSync = true
        }
    }

    /**
     * 推进水生阶段：只在水中且离地时推进；
     * 落地转离地 (1->0) 或重新入水时重置
     */
    private fun advancePhase(wasOnGround: Boolean) {
        if (this.isInGround) return
        if (wasOnGround || (!this.prevInWater && this.isInWater)) {
            this.waterPhaseTicks = 0
            return
        }
        if (this.isInWater && this.waterPhaseTicks < this.homingDurationTicks) {
            this.waterPhaseTicks++
        }
    }

    private fun phaseProgress() = (this.waterPhaseTicks.toDouble() / this.homingDurationTicks).coerceIn(0.0, 1.0)

    /** 重力随阶段 0 -> 0.05 线性增强；阶段 0（刚入水）忽略重力、维持惯性 */
    override fun getDefaultGravity() =
        if (this.isInWater && !this.isInGround) super.getDefaultGravity() * this.phaseProgress()
        else super.getDefaultGravity()

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
                this.isInGround || this.deltaMovement == Vec3.ZERO || this.getDirectionTo(it)
                    .dot(this.deltaMovement) > 0
            }
            .map { it as LivingEntity }
    }

    /** 追踪强度随阶段衰减：gain 1 -> 0，转向受每 tick 最大转角限制 */
    private fun trace(entity: Entity): Vec3 {
        val movement = this.deltaMovement
        val velocity = movement.length()
        val directionVec = this.getDirectionTo(entity)
        val p = 0.1
        val r = (-1 * this.distanceTo(entity) + this.range + velocity) * p
        val gain = 1.0 - this.phaseProgress()
        val weight = max(r, this.baseTraceRate) * gain
        val desired = movement.normalize().add(directionVec.scale(weight)).normalize()
        return movement.normalize().rotateToward(desired, this.maxTurnRadians).scale(velocity)
    }

    //-----------------persistent----------------//

    override fun addAdditionalSaveData(output: ValueOutput) {
        super.addAdditionalSaveData(output)
        output.putInt("waterPhaseTicks", this.waterPhaseTicks)
    }

    override fun readAdditionalSaveData(input: ValueInput) {
        super.readAdditionalSaveData(input)
        this.waterPhaseTicks = input.getIntOr("waterPhaseTicks", 0)
    }

    companion object {
        /** 当前追踪目标实体 id，-1 表示无目标；客户端据此做视觉追踪 */
        private val DATA_TARGET_ID: EntityDataAccessor<Int> =
            SynchedEntityData.defineId(EntityNeptuniumBullet::class.java, EntityDataSerializers.INT)
        private val DATA_WATER_PHASE_TICKS: EntityDataAccessor<Int> =
            SynchedEntityData.defineId(EntityNeptuniumBullet::class.java, EntityDataSerializers.INT)
    }
}
