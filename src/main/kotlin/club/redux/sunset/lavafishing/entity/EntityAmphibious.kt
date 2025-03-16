package club.redux.sunset.lavafishing.entity

import club.redux.sunset.lavafishing.ai.path.pathnavigation.PathNavigationLavaAmphibious
import club.redux.sunset.lavafishing.misc.LavaFishType
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.goal.RandomStrollGoal
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.entity.animal.AbstractSchoolingFish
import net.minecraft.world.level.Level
import net.minecraft.world.level.pathfinder.PathType

class EntityAmphibious(
    entityType: EntityType<out AbstractSchoolingFish>,
    level: Level,
    fishType: LavaFishType,
) : EntityLavaFish(entityType, level, fishType) {

    init {
        // 设置岩浆路径的寻找代价为 0，表示不避免岩浆。
        this.setPathfindingMalus(PathType.LAVA, 0.0f)
        // 设置可行走地形的路径寻找代价为 6，提高行走路径的优先级。
        this.setPathfindingMalus(PathType.WALKABLE, 6.0f)
        // 设置水边路径的寻找代价为 4，调整对水边路径的偏好。
        this.setPathfindingMalus(PathType.WATER_BORDER, 4.0f)
    }

    override fun aiStep() {
        super.aiStep()
        this.speed = if (this.isInLava) 1F else 0.1F
    }

    override fun registerGoals() {
        super.registerGoals()
        this.goalSelector.addGoal(6, RandomStrollGoal(this, 0.1))
    }

    override fun createNavigation(pLevel: Level): PathNavigation = PathNavigationLavaAmphibious(this, pLevel)
}