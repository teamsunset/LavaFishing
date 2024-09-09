package club.redux.sunset.lavafishing.entity;

import club.redux.sunset.lavafishing.ai.goal.GoalLavaFishSwim
import club.redux.sunset.lavafishing.api.mixin.IMixinProxyAbstractFish
import club.redux.sunset.lavafishing.misc.LavaFishType
import club.redux.sunset.lavafishing.util.castToProxy
import club.redux.sunset.lavafishing.util.isInFluid
import com.teammetallurgy.aquaculture.entity.ai.goal.FollowTypeSchoolLeaderGoal
import net.minecraft.util.Mth
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.control.MoveControl
import net.minecraft.world.entity.animal.AbstractFish
import net.minecraft.world.entity.animal.AbstractSchoolingFish
import net.minecraft.world.level.Level
import net.minecraft.world.level.material.Fluids
import kotlin.math.sqrt

class EntityCommonFish(
    entityType: EntityType<out AbstractSchoolingFish>,
    level: Level,
    fishType: LavaFishType,
) : EntityLavaFish(entityType, level, fishType) {

    override fun init() {
        super.init()
        this.moveControl = MoveControlLavaFish(this)
    }

    override fun handleAirSupply(pAirSupply: Int) {
        super.handleAirSupply(pAirSupply)
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
        this.castToProxy(IMixinProxyAbstractFish::class.java).aiStepFromMob()
    }

    override fun registerGoals() {
        super.registerGoals()
        this.goalSelector.addGoal(4, GoalLavaFishSwim(this))
        this.goalSelector.addGoal(5, FollowTypeSchoolLeaderGoal(this))
    }

    companion object {
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
    }
}
