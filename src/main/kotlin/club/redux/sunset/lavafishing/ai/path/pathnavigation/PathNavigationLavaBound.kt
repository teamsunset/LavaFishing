package club.redux.sunset.lavafishing.ai.path.pathnavigation

import club.redux.sunset.lavafishing.ai.path.nodeevaluator.NodeEvaluatorLavaSwim
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation
import net.minecraft.world.level.Level
import net.minecraft.world.level.pathfinder.PathFinder

class PathNavigationLavaBound(mob: Mob, level: Level) : WaterBoundPathNavigation(mob, level) {
    private var allowBreaching = false

    override fun createPathFinder(maxVisitedNodes: Int): PathFinder {
//        this.allowBreaching = mob.type === EntityType.DOLPHIN
        this.nodeEvaluator = NodeEvaluatorLavaSwim(this.allowBreaching)
        return PathFinder(this.nodeEvaluator, maxVisitedNodes)
    }

    override fun canUpdatePath(): Boolean = this.allowBreaching || this.isInLiquid
}
