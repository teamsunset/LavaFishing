package club.redux.sunset.lavafishing.ai.goal

import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal
import net.minecraft.world.entity.monster.Skeleton
import net.minecraftforge.event.entity.EntityJoinLevelEvent

class SlingshotGoal(
    private val skeleton: Skeleton,
    pSpeedModifier: Double,
    pAttackIntervalMin: Int,
    pAttackRadius: Float,
) : RangedBowAttackGoal<Skeleton>(skeleton, pSpeedModifier, pAttackIntervalMin, pAttackRadius) {

    override fun isHoldingBow(): Boolean {
        return this.skeleton.isHolding(ModItems.PROMETHIUM_SLINGSHOT.get())
    }

    companion object {
        @JvmStatic
        fun onEntityJoinLevel(event: EntityJoinLevelEvent) {
            if (event.entity is Skeleton) {
                val skeleton = event.entity as Skeleton
                skeleton.goalSelector.addGoal(4, SlingshotGoal(skeleton, 1.0, 20, 15.0f))
            }
        }
    }
}