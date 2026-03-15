package club.redux.sunset.lavafishing.entity.bullet

import club.redux.sunset.lavafishing.misc.ModTiers
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.util.Utils
import club.redux.sunset.lavafishing.util.UtilVec3.toVec3
import club.redux.sunset.lavafishing.util.hasEnchantmentThen
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.Vec3

class EntityPromethiumBullet(
    entityType: EntityType<out EntityBullet>,
    level: Level,
) : EntityBullet(entityType, level, ModTiers.PROMETHIUM) {
    var dividable = true
    var divisionTimes = 1
    var divisionNum = 2

    private fun destroy() = this.remove(RemovalReason.DISCARDED)

    private fun explode(radius: Float) {
        this.level().explode(
            this.owner,
            this.x,
            this.y,
            this.z,
            radius,
            this.isOnFire,
            Level.ExplosionInteraction.NONE
        )
    }

    private fun dividedBullet(dividable: Boolean, divisionNum: Int, divisionTimes: Int): EntityPromethiumBullet {
        return EntityPromethiumBullet(ModEntityTypes.PROMETHIUM_BULLET.get(), this.level()).also {
            it.setPos(this.x, this.y, this.z)
            it.dividable = dividable
            it.divisionNum = divisionNum
            it.divisionTimes = divisionTimes
            it.owner = this.owner
            it.baseDamage = this.baseDamage
            it.remainingFireTicks = this.remainingFireTicks
        }
    }

    private fun divide(num: Int, velocity: Double, b: Double = 1.0) {
        Utils.generateArchimedianScrew(num, b).forEach { point ->
            this.level().addFreshEntity(dividedBullet(false, 0, 0).also {
                it.deltaMovement = Vec3(point.first, -3.0 * velocity, point.second)
                it.waterInertia = this.waterInertia
            })
        }
    }

    override fun onHitEntity(pResult: EntityHitResult) {
        super.onHitEntity(pResult)

        if (this.piercedEntityIds.size <= this.pierceLevel) {
            this.explode(0.5f)
            return
        }

        if (this.dividable) {
            this.hitDivide()
        } else {
            this.explode(1.5f)
        }
        this.destroy()
    }

    override fun onHitBlock(pResult: BlockHitResult) {
        val originVelocity = this.deltaMovement
        super.onHitBlock(pResult)
        this.inGround = false
        this.deltaMovement = originVelocity

        if (!this.dividable) {
            this.explode(1.5f)
            this.destroy()
            return
        }

        when (pResult.direction) {
            Direction.UP -> {
                this.hitDivide()
                this.destroy()
            }

            else -> this.bounce(pResult.direction.normal.toVec3())
        }
    }

    private fun bounce(normal: Vec3) {
        this.explode(2f)
        val velocity = this.deltaMovement
        val normalized = normal.normalize()
        val reflected = if (normalized.length() > 0) {
            velocity.subtract(normalized.scale(2 * Utils.dot(velocity, normalized)))
        } else {
            velocity
        }
        this.deltaMovement = reflected
        this.divisionTimes--
    }

    override fun tick() {
        super.tick()
        if (this.level().isClientSide) return

        if (this.divisionTimes < 0) {
            this.destroy()
            return
        }

        if (this.dividable && this.divisionTimes > 0 && (!this.inGround && this.deltaMovement.length() < 2 && this.deltaMovement.y in (-1.0..-0.5))) {
            this.explode(1f)
            this.divide(this.divisionNum, this.deltaMovement.length())
            this.deltaMovement = Vec3(this.deltaMovement.x, 0.5, this.deltaMovement.z)
            this.divisionTimes--
            if (this.divisionTimes == 0) {
                this.destroy()
            }
            return
        }

        if (!this.dividable && this.inGround) {
            this.explode(1.5f)
            this.destroy()
        }
    }

    private fun hitDivideOnce() {
        this.explode(1f)
        this.divide(this.divisionNum, -0.3)
    }

    private fun hitDivide() {
        if (this.divisionTimes <= 1) {
            this.hitDivideOnce()
        } else {
            this.explode(2f)
            this.level().addFreshEntity(dividedBullet(true, this.divisionNum, this.divisionTimes - 1).also {
                it.deltaMovement = Vec3(0.0, 1.0, 0.0)
                it.waterInertia = this.waterInertia
            })
        }
    }

    override fun attachEnchantment(stack: ItemStack) {
        super.attachEnchantment(stack)
        stack.hasEnchantmentThen(Enchantments.POWER_ARROWS) { this.divisionNum += it }
    }

    //-----------------network----------------//

    override fun addAdditionalSaveData(pCompound: CompoundTag) {
        super.addAdditionalSaveData(pCompound)
        pCompound.putBoolean("dividable", this.dividable)
        pCompound.putInt("divisionNum", this.divisionNum)
        pCompound.putInt("divisionTimes", this.divisionTimes)
    }

    override fun readAdditionalSaveData(pCompound: CompoundTag) {
        super.readAdditionalSaveData(pCompound)
        this.dividable = pCompound.getBoolean("dividable")
        this.divisionNum = pCompound.getInt("divisionNum")
        this.divisionTimes = pCompound.getInt("divisionTimes")
    }
}
