package club.redux.sunset.lavafishing.entity.bullet

import club.redux.sunset.lavafishing.misc.ModTiers
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.util.UtilItemStack.hasEnchantmentThen
import club.redux.sunset.lavafishing.util.Utils
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.Vec3
import thedarkcolour.kotlinforforge.neoforge.forge.vectorutil.v3d.toVec3

class EntityPromethiumBullet(
    entityType: EntityType<EntityPromethiumBullet>,
    level: Level,
) : EntityBullet(entityType, level, ModTiers.PROMETHIUM) {

    var divisionTimes = 1
    var divisionNum = 2

    private fun explode(radius: Float) {
        this.level().explode(this, this.x, this.y, this.z, radius, this.isOnFire, Level.ExplosionInteraction.NONE)
    }

    private fun dividedBullet(divisionNum: Int, divisionTimes: Int): EntityPromethiumBullet {
        return ModEntityTypes.PROMETHIUM_BULLET.get().create(this.level())!!.also {
            it.setPos(this.x, this.y, this.z)
            it.divisionNum = divisionNum
            it.divisionTimes = divisionTimes
            it.owner = this.owner
            it.baseDamage = this.baseDamage
            it.remainingFireTicks = this.remainingFireTicks
        }
    }

    private fun divide(num: Int, velocity: Double, b: Double = 1.0) {
        Utils.generateArchimedianScrew(num, b).forEach { point ->
            this.level().addFreshEntity(dividedBullet(0, 0).also {
                it.deltaMovement = Vec3(point.first, -3.0 * velocity, point.second)
                it.waterInertia = this.waterInertia
            })
        }
    }

    override fun onHitEntity(pResult: EntityHitResult) {
        super.onHitEntity(pResult)
        if (this.level().isClientSide) return
        if (this.pierceLevel > 0) {
            this.explode(0.5f)
            return
        }

        if (this.divisionTimes == 0) {
            this.explode(1.5f)
            this.remove(RemovalReason.DISCARDED)
        } else if (this.divisionTimes > 0) this.hitDivide()
    }

    override fun onHitBlock(pResult: BlockHitResult) {
//        val originV = this.deltaMovement
//        super.onHitBlock(pResult)
//        this.deltaMovement = originV

        if (this.level().isClientSide) return

        if (this.divisionTimes == 0) {
            this.explode(1.5f)
            this.remove(RemovalReason.DISCARDED)
            return
        }

        when (pResult.direction) {
            Direction.UP -> this.hitDivide()
            else -> this.bounce(pResult.direction.normal.toVec3())
        }
    }

    override fun tick() {
        super.tick()
        if (this.level().isClientSide) return

        if (this.divisionTimes < 0) {
            this.remove(RemovalReason.DISCARDED)
            return
        }

        if (this.divisionTimes > 0 && ((!this.inGround && this.deltaMovement.length() < 2 && this.deltaMovement.y in (-1.0..-0.5)))) {
            this.explode(1f)
            this.divide(this.divisionNum, this.deltaMovement.length())
            this.deltaMovement = Vec3(this.deltaMovement.x, 0.5, this.deltaMovement.z)
            this.divisionTimes--
        }
    }

    private fun bounce(normal: Vec3) {
        this.explode(2f)
        val v = this.deltaMovement
        val n = normal.normalize()
        val newV = if (n.length() > 0) v.subtract(n.scale(2 * v.dot(n))) else v
        this.deltaMovement = newV.scale(1.0)
        this.divisionTimes--
    }

    private fun hitDivide() {
        if (this.divisionTimes == 1) {
            this.explode(1f)
            this.divide(this.divisionNum, -0.3)
        } else if (this.divisionTimes > 1) {
            this.explode(2f)
            this.level().addFreshEntity(dividedBullet(this.divisionNum, this.divisionTimes - 1).also {
                it.deltaMovement = Vec3(0.0, 1.0, 0.0)
                it.waterInertia = this.waterInertia
            })
        }
        this.remove(RemovalReason.DISCARDED)
    }

    override fun attachEnchantmentEffects(stack: ItemStack) {
        super.attachEnchantmentEffects(stack)
        stack.hasEnchantmentThen(Enchantments.POWER) { this.divisionNum += it }
//        stack.hasEnchantmentThen(Enchantments.MULTISHOT) { this.divisionTimes = 3 }
    }

    //-----------------network----------------//

    override fun addAdditionalSaveData(pCompound: CompoundTag) {
        super.addAdditionalSaveData(pCompound)
        pCompound.putInt("divisionNum", this.divisionNum)
        pCompound.putInt("divisionTimes", this.divisionTimes)
    }

    override fun readAdditionalSaveData(pCompound: CompoundTag) {
        super.readAdditionalSaveData(pCompound)
        this.divisionNum = pCompound.getInt("divisionNum")
        this.divisionTimes = pCompound.getInt("divisionTimes")
    }
}
