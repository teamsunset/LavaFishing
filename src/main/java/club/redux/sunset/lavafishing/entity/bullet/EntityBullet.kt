package club.redux.sunset.lavafishing.entity.bullet

import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import club.redux.sunset.lavafishing.misc.ModResourceLocation
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.util.UtilEnchantment
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult

open class EntityBullet : AbstractArrow {
    private val inaccuracyMultiplier = 3.0F
    private var waterInertia = 0.6F

    init {
        this.setSoundEvent(SoundEvents.MUD_HIT)
    }

    constructor(
        entityType: EntityType<out EntityBullet>,
        level: Level,
    ) : super(entityType, level)

    constructor(
        entityType: EntityType<out EntityBullet>,
        owner: LivingEntity,
        level: Level,
    ) : super(entityType, owner, level)

    constructor(
        entityType: EntityType<out EntityBullet>,
        x: Double,
        y: Double,
        z: Double,
        level: Level,
    ) : super(entityType, x, y, z, level)

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

    override fun setEnchantmentEffectsFromEntity(pShooter: LivingEntity, pVelocity: Float) {
        pShooter.handSlots.firstOrNull { it.item is ItemSlingshot }?.let {
            this.attachEnchantment(it)
        }
    }

    open fun attachEnchantment(stack: ItemStack) {
        UtilEnchantment.hasThen(Enchantments.POWER_ARROWS, stack) { this.baseDamage += it * 0.5 + 0.5 }
        UtilEnchantment.hasThen(Enchantments.PUNCH_ARROWS, stack) { this.knockback = it }
        UtilEnchantment.hasThen(Enchantments.FLAMING_ARROWS, stack) { this.setSecondsOnFire(100) }
    }

    override fun shoot(pX: Double, pY: Double, pZ: Double, pVelocity: Float, pInaccuracy: Float) {
        super.shoot(pX, pY, pZ, pVelocity, pInaccuracy * inaccuracyMultiplier)
    }

    override fun getPickupItem(): ItemStack {
        return ModItems.REGISTER.entries
            .map { it.get() }.filterIsInstance<ItemBullet>().first { it.entityTypeProvider() == this.type }
            .let { ItemStack(it) }
    }

    public override fun getWaterInertia(): Float = this.waterInertia

    open fun setWaterInertia(value: Float) {
        this.waterInertia = value
    }

    override fun getDefaultHitGroundSoundEvent(): SoundEvent = this.soundEvent ?: SoundEvents.EMPTY
    open fun getTextureLocation(): ResourceLocation = DEFAULT_TEXTURE

    //-----------------network----------------//

//    override fun writeSpawnData(buffer: FriendlyByteBuf) {
//        buffer.writeResourceLocation(this.soundEvent.location)
//        buffer.writeFloat(this.waterInertia)
//    }

//    override fun readSpawnData(additionalData: FriendlyByteBuf) {
//        this.soundEvent =
//            ForgeRegistries.SOUND_EVENTS.getValue(additionalData.readResourceLocation()) ?: SoundEvents.EMPTY
//        this.waterInertia = additionalData.readFloat()
//    }

//    override fun getAddEntityPacket(): Packet<ClientGamePacketListener> {
//        return NetworkHooks.getEntitySpawningPacket(this)
//    }

    companion object {
        @JvmField val DEFAULT_TEXTURE = ModResourceLocation("textures/entity/bullet/default_bullet.png")
    }
}