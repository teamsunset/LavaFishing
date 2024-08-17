package club.redux.sunset.lavafishing.entity

import club.redux.sunset.lavafishing.ai.path.PathNavigationLavaBound
import club.redux.sunset.lavafishing.api.mixin.IMixinProxyAbstractFish
import club.redux.sunset.lavafishing.client.renderer.EntityRendererLavaFish
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.util.castToProxy
import club.redux.sunset.lavafishing.util.isInFluid
import com.teammetallurgy.aquaculture.entity.AquaFishEntity
import com.teammetallurgy.aquaculture.entity.FishType
import com.teammetallurgy.aquaculture.entity.ai.goal.FollowTypeSchoolLeaderGoal
import com.teammetallurgy.aquaculture.init.AquaItems
import com.teammetallurgy.aquaculture.init.AquaSounds
import com.teammetallurgy.aquaculture.misc.StackHelper
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.tags.FluidTags
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.control.MoveControl
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal
import net.minecraft.world.entity.ai.goal.WrappedGoal
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.entity.ai.util.DefaultRandomPos
import net.minecraft.world.entity.animal.AbstractFish
import net.minecraft.world.entity.animal.AbstractSchoolingFish
import net.minecraft.world.entity.animal.Cat
import net.minecraft.world.entity.animal.Ocelot
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
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.pathfinder.BlockPathTypes
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.registries.ForgeRegistries
import kotlin.math.sqrt

class EntityLavaFish(
    entityType: EntityType<out AbstractSchoolingFish>,
    level: Level,
    private val fishType: FishType,
) : AquaFishEntity(entityType, level, fishType) {
    private var freezeTick = 0

    init {
        this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0f)
        this.moveControl = MoveControlLavaFish(this)
    }

    override fun registerGoals() {
        super.registerGoals()

        goalSelector.availableGoals.forEach { wrappedGoal: WrappedGoal ->  //Removes vanilla schooling goal
            if (
                wrappedGoal.goal.javaClass == FollowFlockLeaderGoal::class.java ||
                wrappedGoal.goal.javaClass == RandomSwimmingGoal::class.java ||
                wrappedGoal.goal.javaClass == FollowTypeSchoolLeaderGoal::class.java
            ) {
                goalSelector.removeGoal(wrappedGoal.goal)
            }
        }
        goalSelector.addGoal(4, GoalLavaFishSwim(this))
        goalSelector.addGoal(5, FollowTypeSchoolLeaderGoal(this))
    }

    override fun getPickedResult(target: HitResult): ItemStack = this.bucketItemStack


    override fun getBucketItemStack(): ItemStack {
        return ItemStack(
            ForgeRegistries.ITEMS.getValue(
                ResourceLocation(
                    ForgeRegistries.ENTITY_TYPES.getKey(this.type).toString() + "_bucket"
                )
            )!!
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

    override fun getFlopSound(): SoundEvent {
        if (this.fishType == FishType.JELLYFISH) {
            return AquaSounds.JELLYFISH_FLOP.get()
        }

        return AquaSounds.FISH_FLOP.get()
    }

    override fun handleAirSupply(pAirSupply: Int) {
        if (this.isAlive && this.isInWater) {
            this.freezeTick--
            if (this.freezeTick == -10) {
                this.freezeTick = 0
                this.hurt(this.damageSources().freeze(), 1.0f)
            }
        }

        if (this.isAlive && !this.isInLava) {
            this.airSupply = pAirSupply - 1
            if (this.airSupply == -20) {
                this.airSupply = 0
                this.hurt(this.damageSources().drown(), 2.0f)
            }
        } else {
            this.airSupply = 300
        }
    }

    override fun aiStep() {
        if (!acceptedFluids.any { this.isInFluid(it) } && this.onGround() && this.verticalCollision) {
            this.deltaMovement = deltaMovement.add(
                ((random.nextFloat() * 2.0f - 1.0f) * 0.05f).toDouble(),
                0.4000000059604645,
                ((random.nextFloat() * 2.0f - 1.0f) * 0.05f).toDouble()
            )
            this.setOnGround(false)
            this.hasImpulse = true
            this.playSound(this.flopSound, this.soundVolume, this.voicePitch)
        }
        this.castToProxy(IMixinProxyAbstractFish::class.java).mobAiStep()
    }

    override fun travel(pTravelVector: Vec3) {
        if (this.isEffectiveAi && acceptedFluids.any { this.isInFluid(it) }) {
            this.moveRelative(0.01f, pTravelVector)
            this.move(MoverType.SELF, this.deltaMovement)
            this.deltaMovement = deltaMovement.scale(0.9)
            if (this.target == null) {
                this.deltaMovement = deltaMovement.add(0.0, -0.005, 0.0)
            }
        } else {
            this.castToProxy(IMixinProxyAbstractFish::class.java).livingEntityTravel(pTravelVector)
        }
    }

    override fun stopFollowing() {
        if (this.leader != null) {
            super.stopFollowing()
        }
    }

    override fun getAmbientSound(): SoundEvent = AquaSounds.FISH_AMBIENT.get()
    override fun getDeathSound(): SoundEvent = AquaSounds.FISH_DEATH.get()
    override fun getHurtSound(damageSource: DamageSource): SoundEvent = AquaSounds.FISH_HURT.get()
    override fun getDimensions(pose: Pose): EntityDimensions = super.getDimensions(pose)
    override fun playerTouch(player: Player) = super.playerTouch(player)
    override fun fireImmune(): Boolean = true

    override fun createNavigation(pLevel: Level): PathNavigation = PathNavigationLavaBound(this, pLevel)

    companion object {
        val acceptedFluids = arrayOf(FluidTags.LAVA, FluidTags.WATER)

        class GoalLavaFishSwim(private val fish: AbstractFish) : RandomSwimmingGoal(fish, 1.0, 40) {
            override fun getPosition(): Vec3? {
                val isAcceptedFluids = { p: BlockPos -> acceptedFluids.any { fish.level().getFluidState(p).`is`(it) } }
                val randomPos = { DefaultRandomPos.getPos(fish, 10, 7) }

                var vec3 = randomPos()
                var i = 0
                while (vec3 != null && !isAcceptedFluids(BlockPos.containing(vec3)) && i++ < 10) {
                    vec3 = randomPos()
                }

                return vec3
            }
        }

        class MoveControlLavaFish internal constructor(private val fish: AbstractFish) : MoveControl(fish) {
            override fun tick() {
                if (fish.isEyeInFluidType(Fluids.LAVA.fluidType)) {
                    fish.deltaMovement = fish.deltaMovement.add(0.0, 0.005, 0.0)
                }

                if (this.operation == Operation.MOVE_TO && !fish.navigation.isDone) {
                    val targetSpeed =
                        (this.speedModifier * fish.getAttributeValue(Attributes.MOVEMENT_SPEED)).toFloat()
                    fish.speed = Mth.lerp(0.125f, fish.speed, targetSpeed)
                    val deltaX = this.wantedX - fish.x
                    val deltaY = this.wantedY - fish.y
                    val deltaZ = this.wantedZ - fish.z

                    if (deltaY != 0.0) {
                        val distance = sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ)
                        fish.deltaMovement = fish.deltaMovement.add(
                            0.0,
                            fish.speed.toDouble() * (deltaY / distance) * 0.1,
                            0.0
                        )
                    }

                    if (deltaX != 0.0 || deltaZ != 0.0) {
                        val targetYaw = (Mth.atan2(deltaZ, deltaX) * 57.2957763671875).toFloat() - 90.0f
                        fish.yRot = this.rotlerp(fish.yRot, targetYaw, 90.0f)
                        fish.yBodyRot = fish.yRot
                    }
                } else {
                    fish.speed = 0.0f
                }
            }
        }

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

        fun onSpawnPlacementRegister(event: SpawnPlacementRegisterEvent) {
            ModEntityTypes.getEntriesByEntityParentClass(EntityLavaFish::class.java).forEach {
                event.register(
                    it.get(),
                    SpawnPlacements.Type.IN_LAVA,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    EntityLavaFish::canSpawnHere,
                    SpawnPlacementRegisterEvent.Operation.REPLACE
                )
            }
        }

        fun onEntityAttributeCreation(event: EntityAttributeCreationEvent) {
            ModEntityTypes.getEntriesByEntityParentClass(EntityLavaFish::class.java).forEach {
                event.put(it.get(), AbstractFish.createAttributes().build())
            }
        }

        fun onSetup(event: FMLCommonSetupEvent) {
            try {
                // CatBreeding
                val catBreedingItems = Cat.TEMPT_INGREDIENT
                val ocelotBreedingItems = Ocelot.TEMPT_INGREDIENT
                val lavaFish: MutableList<ItemStack> = ArrayList()
                ModEntityTypes.getEntriesByEntityParentClass(EntityLavaFish::class.java)
                    .forEach {
                        lavaFish.add(
                            ItemStack(ForgeRegistries.ITEMS.getValue(it.key!!.location())!!)
                        )
                    }
                lavaFish.removeIf { p: ItemStack -> p.item == AquaItems.JELLYFISH.get() }

                Cat.TEMPT_INGREDIENT =
                    StackHelper.mergeIngredient(catBreedingItems, StackHelper.ingredientFromStackList(lavaFish))
                Ocelot.TEMPT_INGREDIENT =
                    StackHelper.mergeIngredient(ocelotBreedingItems, StackHelper.ingredientFromStackList(lavaFish))
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }

        fun onRegisterEntityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
            ModEntityTypes.getEntriesByEntityParentClass(EntityLavaFish::class.java).forEach {
                event.registerEntityRenderer(it.get()) { context: EntityRendererProvider.Context ->
                    EntityRendererLavaFish(context, false)
                }
            }
        }
    }
}