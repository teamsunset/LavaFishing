package club.redux.sunset.lavafishing.entity.bullet

import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.network.protocol.game.ClientboundGameEventPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.Mth
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import kotlin.math.max
import kotlin.math.min

open class EntityBullet : AbstractArrow {

    private val inaccuracyMultiplier = 2.0F

    init {
        this.baseDamage = 1.0
        this.setSoundEvent(SoundEvents.EMPTY)
    }

    constructor(arrow: EntityType<EntityPromethiumBullet>, world: Level) : super(arrow, world)

    constructor(
        entityType: EntityType<out AbstractArrow>,
        owner: LivingEntity,
        level: Level,
    ) : super(entityType, owner, level)

    constructor(
        entityType: EntityType<out AbstractArrow>,
        x: Double,
        y: Double,
        z: Double,
        level: Level,
    ) : super(entityType, x, y, z, level)

    override fun shoot(pX: Double, pY: Double, pZ: Double, pVelocity: Float, pInaccuracy: Float) {
        super.shoot(pX, pY, pZ, pVelocity, pInaccuracy * inaccuracyMultiplier)
    }

    /**
     * # 击中实体
     *
     * 重写原版的方法，删掉了渲染箭的计数
     */
    override fun onHitEntity(pResult: EntityHitResult) {
        val entity = pResult.entity
        val velocity = deltaMovement.length().toFloat()
        var damage = Mth.ceil(
            Mth.clamp(
                velocity.toDouble() * this.baseDamage,
                0.0,
                Int.MAX_VALUE.toDouble()
            )
        )

        if (this.pierceLevel > 0) {
            if (this.piercingIgnoreEntityIds == null) {
                this.piercingIgnoreEntityIds = IntOpenHashSet(5)
            }

            if (this.piercedAndKilledEntities == null) {
                this.piercedAndKilledEntities = ArrayList(5)
            }

            if (piercingIgnoreEntityIds!!.size >= this.pierceLevel + 1) {
                this.discard()
                return
            }

            piercingIgnoreEntityIds!!.add(entity.id)
        }

        if (this.isCritArrow) {
            val critDamage = random.nextInt(damage / 2 + 2).toLong()
            damage = min((critDamage + damage.toLong()).toDouble(), 2147483647.0).toInt()
        }

        val owner = this.owner
        val damageSource: DamageSource
        if (owner == null) {
            damageSource = damageSources().arrow(this, this)
        } else {
            damageSource = damageSources().arrow(this, owner)
            if (owner is LivingEntity) {
                owner.setLastHurtMob(entity)
            }
        }

        val flag = entity.type === EntityType.ENDERMAN
        val k = entity.remainingFireTicks
        if (this.isOnFire && !flag) {
            entity.setSecondsOnFire(5)
        }

        if (entity.hurt(damageSource, damage.toFloat())) {
            if (flag) {
                return
            }

            if (entity is LivingEntity) {
//                if (!level().isClientSide && this.pierceLevel <= 0) {
//                    entity.arrowCount += 1
//                }

                if (this.knockback > 0) {
                    val d0 = max(0.0, 1.0 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE))
                    val vec3 = deltaMovement.multiply(1.0, 0.0, 1.0).normalize().scale(knockback.toDouble() * 0.6 * d0)
                    if (vec3.lengthSqr() > 0.0) {
                        entity.push(vec3.x, 0.1, vec3.z)
                    }
                }

                if (!level().isClientSide && owner is LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(entity, owner)
                    EnchantmentHelper.doPostDamageEffects(owner, entity)
                }

                this.doPostHurtEffects(entity)
                if (owner != null && entity !== owner && entity is Player && owner is ServerPlayer && !this.isSilent) {
                    owner.connection.send(
                        ClientboundGameEventPacket(
                            ClientboundGameEventPacket.ARROW_HIT_PLAYER,
                            0.0f
                        )
                    )
                }

                if (!entity.isAlive() && this.piercedAndKilledEntities != null) {
                    piercedAndKilledEntities!!.add(entity)
                }

                if (!level().isClientSide && owner is ServerPlayer) {
                    if (this.piercedAndKilledEntities != null && this.shotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(
                            owner,
                            this.piercedAndKilledEntities as Collection<Entity>
                        )
                    } else if (!entity.isAlive() && this.shotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(owner, listOf<Entity>(entity))
                    }
                }
            }

            this.playSound(this.soundEvent, 1.0f, 1.2f / (random.nextFloat() * 0.2f + 0.9f))
            if (this.pierceLevel <= 0) {
                this.discard()
            }
        } else {
            entity.remainingFireTicks = k
            this.deltaMovement = deltaMovement.scale(-0.1)
            this.yRot += 180.0f
            this.yRotO += 180.0f
            if (!level().isClientSide && deltaMovement.lengthSqr() < 1.0E-7) {
                if (this.pickup == Pickup.ALLOWED) {
                    this.spawnAtLocation(this.pickupItem, 0.1f)
                }

                this.discard()
            }
        }
    }

    override fun getPickupItem(): ItemStack {
        return ItemStack.EMPTY
    }
}