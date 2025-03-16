package club.redux.sunset.lavafishing.entity.bullet

import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.util.UtilItemStack.getEnchantmentLevel
import club.redux.sunset.lavafishing.util.UtilItemStack.hasEnchantmentThen
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn
import kotlin.math.max

open class EntityBullet(
    entityType: EntityType<out EntityBullet>,
    level: Level,
    private val tier: Tier,
) : AbstractArrow(entityType, level), IEntityWithComplexSpawn {
    private val inaccuracyMultiplier: Float = 3.0F
    private var waterInertia: Float = 0.6F

    init {
        this.setSoundEvent(SoundEvents.MUD_HIT)
        this.baseDamage = this.calculateBaseDamage()
    }

    open fun calculateBaseDamage(): Double = 0.5 * this.tier.attackDamageBonus

    /**
     * # 击中实体
     *
     * 重写原版的方法，删掉了渲染箭的计数
     */
    override fun onHitEntity(pResult: EntityHitResult) {
        val entity = pResult.entity

        if (entity is LivingEntity) {
            val oldArrowCount = entity.arrowCount
            super.onHitEntity(pResult)
            entity.arrowCount = oldArrowCount
        } else {
            super.onHitEntity(pResult)
        }
    }


    /**
     * # 去掉强制设置声音
     */
    override fun onHitBlock(pResult: BlockHitResult) {
        val oldSoundEvent = this.soundEvent
        super.onHitBlock(pResult)
        this.setSoundEvent(oldSoundEvent)
    }

    open fun attachEnchantmentEffects(stack: ItemStack) {
        stack.hasEnchantmentThen(Enchantments.POWER) { this.baseDamage += it * 0.5 + 0.5 }
        stack.hasEnchantmentThen(Enchantments.FLAME) { this.remainingFireTicks = 100 }
        stack.hasEnchantmentThen(Enchantments.MULTISHOT) { this.pickup = Pickup.DISALLOWED }
    }

    override fun shoot(pX: Double, pY: Double, pZ: Double, pVelocity: Float, pInaccuracy: Float) {
        super.shoot(pX, pY, pZ, pVelocity, pInaccuracy * inaccuracyMultiplier)
    }

    override fun getDefaultPickupItem(): ItemStack =
        ModItems.getEntriesIsInstance<ItemBullet>()
            .first { it.entityTypeProvider() == this.type }
            .let { ItemStack(it) }

    override fun getDefaultHitGroundSoundEvent(): SoundEvent = this.soundEvent ?: SoundEvents.EMPTY
    public override fun getWaterInertia() = this.waterInertia
    fun setWaterInertia(value: Float) = this.run { this.waterInertia = value }
    override fun doKnockback(pEntity: LivingEntity, pDamageSource: DamageSource) {
        if (this.firedFromWeapon == null) return
        if (this.level() !is ServerLevel) return

        val d0 = this.firedFromWeapon!!.getEnchantmentLevel(Enchantments.PUNCH) + 1
        val d1 = max(0.0, 1.0 - pEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE))
        val vec3 = deltaMovement.multiply(1.0, 0.0, 1.0).normalize().scale(d0 * 0.6 * d1)
        if (vec3.lengthSqr() > 0.0) {
            pEntity.push(vec3.x, 0.1, vec3.z)
        }
    }

    //-----------------network----------------//

    override fun writeSpawnData(buffer: RegistryFriendlyByteBuf) {
//        buffer.writeResourceLocation(this.soundEvent.location)
        buffer.writeFloat(this.waterInertia)
    }

    override fun readSpawnData(additionalData: RegistryFriendlyByteBuf) {
//        this.soundEvent =
//            ForgeRegistries.SOUND_EVENTS.getValue(additionalData.readResourceLocation()) ?: SoundEvents.EMPTY
        this.waterInertia = additionalData.readFloat()
    }
}