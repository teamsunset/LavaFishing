package club.redux.sunset.lavafishing.entity.bullet

import club.redux.sunset.lavafishing.misc.ModTiers
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.util.Utils
import club.redux.sunset.lavafishing.util.hasEnchantmentThen
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
    var divisionNum = 3

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
        if (this.level().isClientSide) return

        if (this.dividable) {
            this.hitDivide()
        } else {
            this.explode(1.5f)
        }
        this.remove(RemovalReason.DISCARDED)
    }

    override fun onHitBlock(pResult: BlockHitResult) {
        super.onHitBlock(pResult)
        if (this.level().isClientSide) return

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
        if (this.level().isClientSide) return

        if (this.dividable && this.divisionTimes <= 0) {
            this.remove(RemovalReason.DISCARDED)
        }
        if (this.dividable && this.divisionTimes > 0 && ((!this.inGround && this.deltaMovement.length() < 2 && this.deltaMovement.y in (-1.0..-0.3)) || (this.inGround && this.xRot > 0))) {
            this.explode(1f)
            this.divide(this.divisionNum, this.deltaMovement.length())
            this.deltaMovement = Vec3(this.deltaMovement.x, 0.5, this.deltaMovement.z)
            this.divisionTimes--
        }
        if (!this.dividable && this.inGround) {
            this.explode(1.5f)
            this.remove(RemovalReason.DISCARDED)
        }
    }

    private fun hitDivide() {
        if (this.divisionTimes <= 1) {
            this.explode(1f)
            this.divide(this.divisionNum, -0.3, 0.5)
        } else {
            this.explode(2f)
            this.level().addFreshEntity(dividedBullet(true, this.divisionNum, this.divisionTimes - 1).also {
                it.deltaMovement = Vec3(0.0, 1.0, 0.0)
                it.waterInertia = this.waterInertia
            })
        }
    }

    override fun attachEnchantmentEffects(stack: ItemStack) {
        super.attachEnchantmentEffects(stack)
        stack.hasEnchantmentThen(Enchantments.POWER) { this.divisionNum += it }
        stack.hasEnchantmentThen(Enchantments.MULTISHOT) { this.divisionTimes = 3 }
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
