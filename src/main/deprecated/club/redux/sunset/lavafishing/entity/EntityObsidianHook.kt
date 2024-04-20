package club.redux.sunset.lavafishing.entity

import club.asynclab.web.BuildConstants
import club.redux.sunset.lavafishing.loot.LootTableHandler
import club.redux.sunset.lavafishing.registry.RegistryEntityType
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.stats.Stats
import net.minecraft.tags.FluidTags
import net.minecraft.tags.ItemTags
import net.minecraft.util.Mth
import net.minecraft.world.entity.*
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.FishingHook
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.level.storage.loot.LootParams
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.minecraft.world.level.storage.loot.parameters.LootContextParams
import net.minecraft.world.phys.Vec3
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.ItemFishedEvent
import java.util.*
import kotlin.math.*

open class EntityObsidianHook : FishingHook {
    private val lavaTickRand = Random()

    constructor(entityType: EntityType<out FishingHook>, pLevel: Level) : super(entityType, pLevel)

    constructor(pPlayer: Player, pLevel: Level, pLuck: Int, pLureSpeed: Int) : super(
        pPlayer,
        pLevel,
        pLuck,
        pLureSpeed
    )

    open val isInLavaFishing: Boolean
        get() = level().getFluidState(blockPosition().offset(0, -1, 0)).`is`(FluidTags.LAVA) ||
                level().getFluidState(this.blockPosition()).`is`(FluidTags.LAVA)

    override fun tick() {
        if (isInLavaFishing) {
            lavaFishingTick()
        } else {
            super.tick()
        }
    }

    /**
     * # [Projectile]的tick处理函数
     *
     * 原样照抄，因为不能直接访问祖宗类的tick()
     */
    open fun projectileTick() {
        if (!this.hasBeenShot) { // 如果弹道还未被射击，则触发射击事件并标记为已射击
            this.gameEvent(GameEvent.PROJECTILE_SHOOT, this.owner)
            this.hasBeenShot = true
        }
        if (!this.leftOwner) { // 如果还未检查过所有者，则进行检查并记录结果
            this.leftOwner = this.checkLeftOwner()
        }
        super.baseTick() // 执行基类的tick处理逻辑
    }

    /**
     * # 熔岩钓鱼的tick处理函数
     *
     * 抄原版的
     */
    open fun lavaFishingTick() {
        lavaTickRand.setSeed(getUUID().leastSignificantBits xor level().gameTime) // Modified
        projectileTick() // Modified

        val player = this.playerOwner
        if (player == null) {
            this.discard()
        } else if (level().isClientSide || !this.shouldStopFishing(player)) {
            if (this.onGround()) {
                ++this.life
                if (this.life >= 1200) {
                    this.discard()
                    return
                }
            } else {
                this.life = 0
            }

            var f = 0.0f
            val blockpos = this.blockPosition()
            val fluidstate = level().getFluidState(blockpos)
            if (fluidstate.`is`(FluidTags.WATER)) {
                f = fluidstate.getHeight(this.level(), blockpos)
            }

            val flag = f > 0.0f
            if (this.currentState == FishHookState.FLYING) {
                if (this.hookedIn != null) {
                    this.deltaMovement = Vec3.ZERO
                    this.currentState = FishHookState.HOOKED_IN_ENTITY
                    return
                }

                if (flag) {
                    this.deltaMovement = deltaMovement.multiply(0.3, 0.2, 0.3)
                    this.currentState = FishHookState.BOBBING
                    return
                }

                this.checkCollision()
            } else {
                if (this.currentState == FishHookState.HOOKED_IN_ENTITY) {
                    if (this.hookedIn != null) {
                        if (!hookedIn!!.isRemoved && hookedIn!!.level().dimension() === level().dimension()) {
                            this.setPos(
                                hookedIn!!.x,
                                hookedIn!!.getY(0.8), hookedIn!!.z
                            )
                        } else {
                            this.setHookedEntity(null as Entity?)
                            this.currentState = FishHookState.FLYING
                        }
                    }

                    return
                }

                if (this.currentState == FishHookState.BOBBING) {
                    val vec3 = this.deltaMovement
                    var d0 = this.y + vec3.y - (blockpos.y.toDouble()) - (f.toDouble())
                    if (abs(d0) < 0.01) {
                        d0 += sign(d0) * 0.1
                    }

                    this.setDeltaMovement(vec3.x * 0.9, vec3.y - d0 * random.nextFloat().toDouble() * 0.2, vec3.z * 0.9)
                    if (this.nibble <= 0 && this.timeUntilHooked <= 0) {
                        this.openWater = true
                    } else {
                        this.openWater =
                            this.openWater && (this.outOfWaterTime < 10) && this.calculateOpenWater(blockpos)
                    }

                    if (flag) {
                        this.outOfWaterTime =
                            max(0.0, (this.outOfWaterTime - 1).toDouble()).toInt()
                        if (this.biting) {
                            this.deltaMovement =
                                deltaMovement.add(
                                    0.0,
                                    -0.1 * this.lavaTickRand.nextFloat().toDouble() * this.lavaTickRand.nextFloat()
                                        .toDouble(),
                                    0.0
                                ) // Modified
                        }

                        if (!level().isClientSide) {
                            this.lavaCatchingFish(blockpos) // Modified
                        }
                    } else {
                        this.outOfWaterTime =
                            min(10.0, (this.outOfWaterTime + 1).toDouble()).toInt()
                    }
                }
            }

            if (!fluidstate.`is`(FluidTags.LAVA)) { // Modified
                this.deltaMovement = deltaMovement.add(0.0, -0.03, 0.0)
            }

            this.move(MoverType.SELF, this.deltaMovement)
            this.updateRotation()
            if (this.currentState == FishHookState.FLYING && (this.onGround() || this.horizontalCollision)) {
                this.deltaMovement = Vec3.ZERO
            }

            val d1 = 0.92
            this.deltaMovement = deltaMovement.scale(0.92)
            this.reapplyPosition()
        }
    }

    /**
     * # 收竿
     *
     * 抄原版的
     */
    override fun retrieve(pStack: ItemStack): Int {
        if (isInLavaFishing) { // Modified
            val player = this.playerOwner
            if (!level().isClientSide && player != null && !this.shouldStopFishing(player)) {
                var i = 0
                var event: ItemFishedEvent? = null
                if (this.hookedIn != null) {
                    this.pullEntity(hookedIn!!)
                    CriteriaTriggers.FISHING_ROD_HOOKED.trigger(player as ServerPlayer, pStack, this, emptyList())
                    level().broadcastEntityEvent(this, 31.toByte())
                    i = if (hookedIn is ItemEntity) 3 else 5
                } else if (this.nibble > 0) {
                    val lootparams = LootParams.Builder(level() as ServerLevel)
                        .withParameter(LootContextParams.ORIGIN, this.position())
                        .withParameter(LootContextParams.TOOL, pStack).withParameter(
                            LootContextParams.THIS_ENTITY,
                            this
                        ).withParameter(LootContextParams.KILLER_ENTITY, player).withParameter(
                            LootContextParams.THIS_ENTITY,
                            this
                        ).withLuck(
                            luck.toFloat() + player.luck
                        ).create(LootContextParamSets.FISHING)
                    val loottable =
                        level().server!!.lootData.getLootTable(LootTableHandler.FISH) // Modified, use lava fishing loot
                    val list: List<ItemStack> = loottable.getRandomItems(lootparams)
                    event = ItemFishedEvent(list, if (this.onGround()) 2 else 1, this)
                    MinecraftForge.EVENT_BUS.post(event)
                    if (event.isCanceled) {
                        this.discard()
                        return event.rodDamage
                    }
                    CriteriaTriggers.FISHING_ROD_HOOKED.trigger(player as ServerPlayer, pStack, this, list)
                    //vanilla code---
                    for (itemstack in list) {
                        val itementity = ItemEntity(this.level(), this.x, this.y, this.z, itemstack)
                        val d0 = player.getX() - this.x
                        val d1 = player.getY() - this.y
                        val d2 = player.getZ() - this.z
                        itementity.setDeltaMovement(
                            d0 * 0.1,
                            d1 * 0.1 + sqrt(sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08 + 0.2,
                            d2 * 0.1
                        ) // Modified, 这是因为太低会被岩浆黏住
                        level().addFreshEntity(itementity)
                        player.level().addFreshEntity(
                            ExperienceOrb(
                                player.level(), player.getX(), player.getY() + 0.5, player.getZ() + 0.5,
                                random.nextInt(6) + 1
                            )
                        )
                        if (itemstack.`is`(ItemTags.FISHES)) {
                            player.awardStat(Stats.FISH_CAUGHT, 1)
                        }
                    }
                    i = 1
                }

                if (this.onGround()) {
                    i = 2
                }

                this.discard()
                return event?.rodDamage ?: i
            } else {
                return 0
            }
        } else {
            return super.retrieve(pStack)
        }
    }

    open fun lavaCatchingFish(pPos: BlockPos) {
        val blockStateInRandomGenerateParticle = Blocks.LAVA
        val bubbleParticle = ParticleTypes.SMOKE
        val fishingParticle = ParticleTypes.ASH
        val splashParticle = ParticleTypes.LAVA

        //---vanilla code
        val serverlevel = level() as ServerLevel
        var i = 1
        val blockpos = pPos.above()
        if (random.nextFloat() < 0.25f && level().isRainingAt(blockpos)) {
            ++i
        }

        if (random.nextFloat() < 0.5f && !level().canSeeSky(blockpos)) {
            --i
        }

        if (this.nibble > 0) {
            --this.nibble
            if (this.nibble <= 0) {
                this.timeUntilLured = 0
                this.timeUntilHooked = 0
                getEntityData().set(DATA_BITING, false)
            }
        } else if (this.timeUntilHooked > 0) {
            this.timeUntilHooked -= i
            if (this.timeUntilHooked > 0) {
                this.fishAngle += (random.nextGaussian() * 4.0).toFloat()
                val f = this.fishAngle * (Math.PI.toFloat() / 180f)
                val f1 = Mth.sin(f)
                val f2 = Mth.cos(f)
                val d0 = this.x + (f1 * timeUntilHooked.toFloat() * 0.1f).toDouble()
                val d1 = (Mth.floor(this.y).toFloat() + 1.0f).toDouble()
                val d2 = this.z + (f2 * timeUntilHooked.toFloat() * 0.1f).toDouble()
                val blockstate1 = serverlevel.getBlockState(BlockPos(d0.toInt(), (d1 - 1.0).toInt(), d2.toInt()))
                if (blockstate1.`is`(blockStateInRandomGenerateParticle)) {
                    if (random.nextFloat() < 0.15f) {
                        serverlevel.sendParticles(
                            bubbleParticle,
                            d0,
                            d1 - 0.1,
                            d2,
                            1,
                            f1.toDouble(),
                            0.1,
                            f2.toDouble(),
                            0.0
                        )
                    }

                    val f3 = f1 * 0.04f
                    val f4 = f2 * 0.04f
                    serverlevel.sendParticles(
                        fishingParticle, d0, d1, d2, 5, f4.toDouble(), 0.01,
                        (-f3).toDouble(), 1.0
                    )
                    serverlevel.sendParticles(
                        fishingParticle, d0, d1, d2, 5,
                        (-f4).toDouble(), 0.01, f3.toDouble(), 1.0
                    )
                }
            } else {
                this.playSound(
                    SoundEvents.FISHING_BOBBER_SPLASH,
                    0.25f,
                    1.0f + (random.nextFloat() - random.nextFloat()) * 0.4f
                )
                val d3 = this.y + 0.5
                serverlevel.sendParticles(
                    bubbleParticle,
                    this.x, d3,
                    this.z,
                    (1.0f + this.bbWidth * 20.0f).toInt(),
                    this.bbWidth.toDouble(), 0.0,
                    this.bbWidth.toDouble(), 0.2
                )
                serverlevel.sendParticles(
                    splashParticle,
                    this.x, d3,
                    this.z,
                    (1.0f + this.bbWidth * 50.0f).toInt(),
                    this.bbWidth.toDouble(), 0.0,
                    this.bbWidth.toDouble(), 0.2
                )
                this.nibble = Mth.nextInt(this.random, 20, 40)
                getEntityData().set(DATA_BITING, true)
            }
        } else if (this.timeUntilLured > 0) {
            this.timeUntilLured -= i
            var f5 = 0.15f
            if (this.timeUntilLured < 20) {
                f5 += (20 - this.timeUntilLured).toFloat() * 0.05f
            } else if (this.timeUntilLured < 40) {
                f5 += (40 - this.timeUntilLured).toFloat() * 0.02f
            } else if (this.timeUntilLured < 60) {
                f5 += (60 - this.timeUntilLured).toFloat() * 0.01f
            }

            if (random.nextFloat() < f5) {
                val f6 = Mth.nextFloat(this.random, 0.0f, 360.0f) * (Math.PI.toFloat() / 180f)
                val f7 = Mth.nextFloat(this.random, 25.0f, 60.0f)
                val d4 = this.x + (Mth.sin(f6) * f7).toDouble() * 0.1
                val d5 = (Mth.floor(this.y).toFloat() + 1.0f).toDouble()
                val d6 = this.z + (Mth.cos(f6) * f7).toDouble() * 0.1
                val blockstate1 = serverlevel.getBlockState(BlockPos.containing(d4, d5 - 1.0, d6))
                if (blockstate1.`is`(Blocks.LAVA)) { // Modified
                    serverlevel.sendParticles(splashParticle, d4, d5, d6, 2 + random.nextInt(2), 0.1, 0.0, 0.1, 0.0)
                }
            }

            if (this.timeUntilLured <= 0) {
                this.fishAngle = Mth.nextFloat(this.random, 0.0f, 360.0f)
                this.timeUntilHooked = Mth.nextInt(this.random, 20, 80)
            }
        } else {
            this.timeUntilLured = Mth.nextInt(this.random, 100, 600)
            this.timeUntilLured -= this.lureSpeed * 20 * 5
        }
    }

    override fun getType(): EntityType<*> {
        return RegistryEntityType.OBSIDIAN_HOOK.get()
    }

    companion object {
        @JvmStatic
        fun buildEntityType(): EntityType<EntityObsidianHook> {
            return EntityType.Builder
                .of(
                    { entityType, pLevel -> EntityObsidianHook(entityType, pLevel) },
                    MobCategory.MISC
                )
                .noSave()
                .noSummon()
                .fireImmune()
                .sized(0.25f, 0.25f)
                .setTrackingRange(4)
                .setUpdateInterval(5)
                .build(ResourceLocation(BuildConstants.MOD_ID, "obsidian_hook").toString())
        }
    }
}