package club.redux.sunset.lavafishing.entity.bullet

import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.util.hasEnchantmentThen
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
import net.minecraft.world.item.Tiers
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn

open class EntityBullet(
    entityType: EntityType<out EntityBullet>,
    level: Level,
    private val tier: Tier = Tiers.STONE,
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
        stack.hasEnchantmentThen(Enchantments.MULTISHOT) { this.pickup = Pickup.DISALLOWED }
        stack.hasEnchantmentThen(Enchantments.FLAME) { this.remainingFireTicks = 100 }
    }

    override fun shoot(pX: Double, pY: Double, pZ: Double, pVelocity: Float, pInaccuracy: Float) {
        super.shoot(pX, pY, pZ, pVelocity, pInaccuracy * inaccuracyMultiplier)
    }

    override fun getDefaultPickupItem(): ItemStack = ModItems.REGISTER.entries
        .map { it.get() }.filterIsInstance<ItemBullet>().first { it.entityTypeProvider() == this.type }
        .let { ItemStack(it) }


    public override fun getWaterInertia(): Float = this.waterInertia

    open fun setWaterInertia(value: Float) {
        this.waterInertia = value
    }

    override fun getDefaultHitGroundSoundEvent(): SoundEvent = this.soundEvent ?: SoundEvents.EMPTY
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