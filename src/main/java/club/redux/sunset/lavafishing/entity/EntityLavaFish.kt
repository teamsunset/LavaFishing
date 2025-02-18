package club.redux.sunset.lavafishing.entity

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.ai.path.pathnavigation.PathNavigationLavaBound
import club.redux.sunset.lavafishing.api.mixin.IMixinProxyAbstractFish
import club.redux.sunset.lavafishing.client.renderer.entity.EntityRendererLavaFish
import club.redux.sunset.lavafishing.misc.LavaFishType
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.util.castToProxy
import com.teammetallurgy.aquaculture.init.AquaSounds
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal
import net.minecraft.world.entity.ai.goal.PanicGoal
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.entity.animal.AbstractFish
import net.minecraft.world.entity.animal.AbstractSchoolingFish
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ItemUtils
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.ServerLevelAccessor
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent

open class EntityLavaFish(
    entityType: EntityType<out AbstractSchoolingFish>,
    level: Level,
    val fishType: LavaFishType,
) : AbstractSchoolingFish(entityType, level) {
    private var freezeTick: Int = 0

    init {
        this.init()
    }

    protected open fun init(): Unit = Unit

    override fun registerGoals() {
        this.goalSelector.availableGoals.clear()

        this.goalSelector.addGoal(0, PanicGoal(this, 1.25))
        this.goalSelector.addGoal(2, AvoidEntityGoal(
            this, Player::class.java,
            8.0f, 1.6, 1.4
        ) { EntitySelector.NO_SPECTATORS.test(it) })
    }

    override fun getPickedResult(target: HitResult): ItemStack = this.bucketItemStack


    override fun getBucketItemStack(): ItemStack {
        return ItemStack(
            BuiltInRegistries.ITEM.get(
                LavaFishing.resourceLocation(
                    BuiltInRegistries.ENTITY_TYPE.getKey(this.type).path.toString() + "_bucket"
                )
            )
        )
    }

    override fun mobInteract(pPlayer: Player, pHand: InteractionHand): InteractionResult {
        val itemStack = pPlayer.getItemInHand(pHand)
        if (itemStack.item === Items.LAVA_BUCKET && this.isAlive) {
            this.playSound(this.pickupSound, 1.0f, 1.0f)
            val bucketItemStack = this.bucketItemStack
            this.saveToBucketTag(bucketItemStack)
            val itemStackInHandFuture = ItemUtils.createFilledResult(itemStack, pPlayer, bucketItemStack, false)
            pPlayer.setItemInHand(pHand, itemStackInHandFuture)
            val level: Level = this.level()
            if (!level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger(pPlayer as ServerPlayer, bucketItemStack)
            }

            this.discard()
            return InteractionResult.sidedSuccess(level.isClientSide)
        } else {
            return InteractionResult.PASS
        }
    }

    override fun handleAirSupply(pAirSupply: Int) {
        if (this.isAlive && this.isInWater) {
            this.freezeTick--
            if (this.freezeTick == -20) {
                this.freezeTick = 0
                this.hurt(this.damageSources().freeze(), 1.0f)
            }
        }
    }

    override fun travel(pTravelVector: Vec3) {
        if (this.isEffectiveAi && this.isInLava) {
            this.moveRelative(0.01f, pTravelVector)
            this.move(MoverType.SELF, this.deltaMovement)
            this.deltaMovement = deltaMovement.scale(0.9)
            if (this.target == null) {
                this.deltaMovement = deltaMovement.add(0.0, -0.005, 0.0)
            }
        } else {
            this.castToProxy(IMixinProxyAbstractFish::class.java).travelFromLivingEntity(pTravelVector)
        }
    }

    override fun stopFollowing() {
        if (this.leader != null) {
            super.stopFollowing()
        }
    }

    override fun aiStep() = this.castToProxy(IMixinProxyAbstractFish::class.java).aiStepFromMob()
    override fun getFlopSound(): SoundEvent = AquaSounds.FISH_FLOP.get()
    override fun getAmbientSound(): SoundEvent = AquaSounds.FISH_AMBIENT.get()
    override fun getDeathSound(): SoundEvent = AquaSounds.FISH_DEATH.get()
    override fun getHurtSound(damageSource: DamageSource): SoundEvent = AquaSounds.FISH_HURT.get()
    override fun playerTouch(player: Player) = super.playerTouch(player)
    override fun fireImmune(): Boolean = true

    override fun createNavigation(pLevel: Level): PathNavigation = PathNavigationLavaBound(this, pLevel)

    companion object {
        fun canSpawnHere(
            fish: EntityType<out AbstractFish>,
            serverLevelAccessor: ServerLevelAccessor,
            spawnReason: MobSpawnType,
            pos: BlockPos,
            random: RandomSource,
        ): Boolean {
            val seaLevel = serverLevelAccessor.level.seaLevel
//            val minY = seaLevel - AquaConfig.BASIC_OPTIONS.fishSpawnLevelModifier.get()
            val minY = 0
            val isAllNeighborsSource =
                isSourceBlock(serverLevelAccessor, pos.north()) &&
                        isSourceBlock(serverLevelAccessor, pos.south()) &&
                        isSourceBlock(serverLevelAccessor, pos.west()) &&
                        isSourceBlock(serverLevelAccessor, pos.east())
            return isSourceBlock(serverLevelAccessor, pos) &&
                    isAllNeighborsSource && pos.y >= minY &&
                    pos.y <= seaLevel
        }

        private fun isSourceBlock(world: LevelAccessor, pos: BlockPos): Boolean {
            val state = world.getBlockState(pos)
            return state.block is LiquidBlock &&
                    world.getBlockState(pos).`is`(Blocks.LAVA) &&
                    state.getValue(LiquidBlock.LEVEL) == 0
        }

        fun onRegisterSpawnPlacements(event: RegisterSpawnPlacementsEvent) {
            ModEntityTypes.getEntriesByEntityParentClass(EntityLavaFish::class.java).forEach {
                event.register(
                    it.get(),
                    SpawnPlacementTypes.IN_LAVA,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    EntityLavaFish.Companion::canSpawnHere,
                    RegisterSpawnPlacementsEvent.Operation.REPLACE
                )
            }
        }

        fun onEntityAttributeCreation(event: EntityAttributeCreationEvent) {
            ModEntityTypes.getEntriesByEntityParentClass(EntityLavaFish::class.java).forEach {
                event.put(it.get(), AbstractFish.createAttributes().build())
            }
        }

        fun onRegisterEntityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
            ModEntityTypes.getEntriesByEntityParentClass(EntityLavaFish::class.java).forEach {
                event.registerEntityRenderer(it.get(), ::EntityRendererLavaFish)
            }
        }

    }
}