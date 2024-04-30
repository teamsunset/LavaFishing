package club.redux.sunset.lavafishing.entity.bullet

import club.redux.sunset.lavafishing.misc.ModResourceLocation
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.util.UtilEnchantment
import club.redux.sunset.lavafishing.util.Utils
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.Vec3

class EntityPromethiumBullet : EntityBullet {
    override fun getTextureLocation() = ModResourceLocation("textures/entity/bullet/promethium_bullet.png")

    var isCarriedFire = false
    var dividable = true
    var divisionTimes = 1
    var divisionNum = 3

    constructor(
        entityType: EntityType<out EntityBullet>,
        owner: LivingEntity,
        level: Level,
    ) : super(entityType, owner, level)

    constructor(
        entityType: EntityType<out EntityBullet>,
        level: Level,
    ) : super(entityType, level)

    constructor(
        entityType: EntityType<out EntityBullet>,
        x: Double,
        y: Double,
        z: Double,
        level: Level,
        dividable: Boolean = false,
        divisionNum: Int = 3,
        divisionTimes: Int = 3,
        isCarriedFire: Boolean = false,
    ) : super(entityType, x, y, z, level) {
        this.dividable = dividable
        this.divisionNum = divisionNum
        this.divisionTimes = divisionTimes
        this.isCarriedFire = isCarriedFire
    }

    private val explode = { radius: Float ->
        this.level().explode(
            this.owner,
            this.x,
            this.y,
            this.z,
            radius,
            this.isCarriedFire,
            Level.ExplosionInteraction.NONE
        )
    }
    private val newBullet = { division: Boolean, divisionNum: Int, divisionCount: Int ->
        EntityPromethiumBullet(
            ModEntityTypes.PROMETHIUM_BULLET.get(),
            this.x,
            this.y,
            this.z,
            this.level(),
            division,
            divisionNum,
            divisionCount,
            this.isCarriedFire
        ).also {
            it.owner = this.owner
            it.baseDamage = this.baseDamage
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

    private fun divide(num: Int, velocity: Double, b: Double = 1.0) {
        Utils.generateArchimedianScrew(num, b).forEach { point ->
            this.level().addFreshEntity(newBullet(false, 0, 0).apply {
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
            this.level().addFreshEntity(newBullet(true, this.divisionNum, this.divisionTimes - 1).apply {
                deltaMovement = Vec3(0.0, 1.0, 0.0)
            })
        }
    }

    override fun attachEnchantment(stack: ItemStack) {
        super.attachEnchantment(stack)
        UtilEnchantment.hasThen(Enchantments.POWER_ARROWS, stack) { this.divisionNum += it }
        UtilEnchantment.hasThen(Enchantments.FLAMING_ARROWS, stack) { this.isCarriedFire = true }
        UtilEnchantment.hasThen(Enchantments.MULTISHOT, stack) { this.divisionTimes = 3 }
    }

    //-----------------network----------------//

    override fun addAdditionalSaveData(pCompound: CompoundTag) {
        super.addAdditionalSaveData(pCompound)
        pCompound.putBoolean("dividable", this.dividable)
        pCompound.putInt("divisionNum", this.divisionNum)
        pCompound.putInt("divisionTimes", this.divisionTimes)
        pCompound.putBoolean("isCarriedFire", this.isCarriedFire)
    }

    override fun readAdditionalSaveData(pCompound: CompoundTag) {
        super.readAdditionalSaveData(pCompound)
        this.dividable = pCompound.getBoolean("dividable")
        this.divisionNum = pCompound.getInt("divisionNum")
        this.divisionTimes = pCompound.getInt("divisionTimes")
        this.isCarriedFire = pCompound.getBoolean("isCarriedFire")
    }
}
