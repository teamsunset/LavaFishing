package club.redux.sunset.lavafishing.ai.goal

import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal
import net.minecraft.world.entity.monster.AbstractSkeleton
import net.minecraftforge.event.entity.EntityJoinLevelEvent

class GoalSlingshot(
    private val skeleton: AbstractSkeleton,
    pSpeedModifier: Double,
    pAttackIntervalMin: Int,
    pAttackRadius: Float,
) : RangedBowAttackGoal<AbstractSkeleton>(skeleton, pSpeedModifier, pAttackIntervalMin, pAttackRadius) {

    override fun isHoldingBow(): Boolean {
        return this.skeleton.isHolding { it.item is ItemSlingshot }
    }

    companion object {
        @JvmStatic
        fun onEntityJoinLevel(event: EntityJoinLevelEvent) {
            val skeleton = event.entity as? AbstractSkeleton ?: return
            skeleton.goalSelector.addGoal(4, GoalSlingshot(skeleton, 1.0, 20, 15.0f))
        }
    }
}
