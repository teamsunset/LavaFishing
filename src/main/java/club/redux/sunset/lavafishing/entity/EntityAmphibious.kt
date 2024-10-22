package club.redux.sunset.lavafishing.entity

import club.redux.sunset.lavafishing.ai.path.pathnavigation.PathNavigationLavaAmphibious
import club.redux.sunset.lavafishing.misc.LavaFishType
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.goal.RandomStrollGoal
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.entity.animal.AbstractSchoolingFish
import net.minecraft.world.level.Level

class EntityAmphibious(
    entityType: EntityType<out AbstractSchoolingFish>,
    level: Level,
    fishType: LavaFishType,
) : EntityLavaFish(entityType, level, fishType) {

    override fun aiStep() {
        super.aiStep()
        this.speed =
            if (this.isInLava) 1F
            else 0.1F
    }

    override fun registerGoals() {
        super.registerGoals()
        this.goalSelector.addGoal(6, RandomStrollGoal(this, 0.1))
    }

    override fun createNavigation(pLevel: Level): PathNavigation = PathNavigationLavaAmphibious(this, pLevel)
}