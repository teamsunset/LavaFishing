package club.redux.sunset.lavafishing.ai.goal

import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal
import net.minecraft.world.entity.monster.Skeleton
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent

class GoalSlingshot(
    private val skeleton: Skeleton,
    pSpeedModifier: Double,
    pAttackIntervalMin: Int,
    pAttackRadius: Float,
) : RangedBowAttackGoal<Skeleton>(skeleton, pSpeedModifier, pAttackIntervalMin, pAttackRadius) {

    override fun isHoldingBow(): Boolean {
        return this.skeleton.isHolding { it.item is ItemSlingshot }
    }

    companion object {
        @JvmStatic
        fun onEntityJoinLevel(event: EntityJoinLevelEvent) {
            if (event.entity is Skeleton) {
                val skeleton = event.entity as Skeleton
                skeleton.goalSelector.addGoal(4, GoalSlingshot(skeleton, 1.0, 20, 15.0f))
            }
        }
    }
}