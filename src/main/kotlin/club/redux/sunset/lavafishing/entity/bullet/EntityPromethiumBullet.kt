package club.redux.sunset.lavafishing.entity.bullet

import club.redux.sunset.lavafishing.misc.ModToolMaterials
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.util.UtilItemStack.hasEnchantmentThen
import club.redux.sunset.lavafishing.util.UtilMath
import club.redux.sunset.lavafishing.util.UtilVec3.toVec3
import net.minecraft.core.Direction
import net.minecraft.world.entity.EntitySpawnReason
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraft.world.level.storage.ValueInput
import net.minecraft.world.level.storage.ValueOutput
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.Vec3

class EntityPromethiumBullet(
    entityType: EntityType<EntityPromethiumBullet>,
    level: Level,
) : EntityBullet(entityType, level, ModToolMaterials.PROMETHIUM) {

    var divisionTimes = 0
    var divisionNum = 2

    private fun destroy() = if (!this.level().isClientSide) this.remove(RemovalReason.DISCARDED) else Unit

    private fun explode(radius: Float) {
        this.level().explode(this, this.x, this.y, this.z, radius, this.isOnFire, Level.ExplosionInteraction.NONE)
    }

    private fun dividedBullet(divisionNum: Int, divisionTimes: Int): EntityPromethiumBullet {
        return ModEntityTypes.PROMETHIUM_BULLET.get().create(this.level(), EntitySpawnReason.TRIGGERED)!!.also {
            it.setPos(this.x, this.y, this.z)
            it.divisionNum = divisionNum
            it.divisionTimes = divisionTimes
            this.copyBulletPropertiesTo(it)
        }
    }

    private fun divide(num: Int, velocity: Double, b: Double = 1.0) {
        UtilMath.generateArchimedianScrew(num, b).forEach { point ->
            this.level().addFreshEntity(dividedBullet(0, 0).also {
                it.deltaMovement = Vec3(point.first, -3.0 * velocity, point.second)
            })
        }
    }

    override fun onHitEntity(pResult: EntityHitResult) {
        super.onHitEntity(pResult)

        if (this.piercingIgnoreEntityIds != null && this.piercingIgnoreEntityIds!!.size <= this.pierceLevel) {
            this.explode(0.5f)
            return
        }

        if (this.divisionTimes == 0) {
            this.explode(1.5f)
            this.destroy()
        } else if (this.divisionTimes > 0) this.hitDivide()
    }

    override fun onHitBlock(pResult: BlockHitResult) {
        val originV = this.deltaMovement
        super.onHitBlock(pResult)
        this.isInGround = false
        this.deltaMovement = originV

        if (this.divisionTimes == 0) {
            this.explode(1.5f)
            this.destroy()
            return
        }

        when (pResult.direction) {
            Direction.UP -> this.hitDivide()
            else -> this.bounce(pResult.direction.unitVec3i.toVec3())
        }
    }

    override fun tick() {
        super.tick()

        if (this.divisionTimes < 0) {
            this.destroy()
            return
        }

        if (this.divisionTimes > 0 && ((!this.isInGround && this.deltaMovement.length() < 2 && this.deltaMovement.y in (-1.0..-0.5)))) {
            this.explode(1f)
            this.divide(this.divisionNum, this.deltaMovement.length())
            this.deltaMovement = Vec3(this.deltaMovement.x, 0.5, this.deltaMovement.z)
            this.divisionTimes--
            if (this.divisionTimes == 0) this.destroy()
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
            })
        }
        this.destroy()
    }

    override fun attachEnchantmentEffects(stack: ItemStack) {
        super.attachEnchantmentEffects(stack)
        stack.hasEnchantmentThen(Enchantments.POWER) { this.divisionNum += it }
    }

    //-----------------network----------------//

    override fun addAdditionalSaveData(output: ValueOutput) {
        super.addAdditionalSaveData(output)
        output.putInt("divisionNum", this.divisionNum)
        output.putInt("divisionTimes", this.divisionTimes)
    }

    override fun readAdditionalSaveData(input: ValueInput) {
        super.readAdditionalSaveData(input)
        this.divisionNum = input.getIntOr("divisionNum", 2)
        this.divisionTimes = input.getIntOr("divisionTimes", 0)
    }
}
