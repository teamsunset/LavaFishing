package club.redux.sunset.lavafishing.ai.path.pathnavigation

import club.redux.sunset.lavafishing.ai.path.nodeevaluator.NodeEvaluatorLavaAmphibious
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation
import net.minecraft.world.level.Level
import net.minecraft.world.level.pathfinder.PathFinder
import net.minecraft.world.phys.Vec3

open class PathNavigationLavaAmphibious(mob: Mob, level: Level) : AmphibiousPathNavigation(mob, level) {
    override fun createPathFinder(pMaxVisitedNodes: Int): PathFinder {
        this.nodeEvaluator = NodeEvaluatorLavaAmphibious(false).apply { setCanPassDoors(true) }
        return PathFinder(this.nodeEvaluator, pMaxVisitedNodes)
    }

    override fun canMoveDirectly(fromPos: Vec3, toPos: Vec3): Boolean {
        return if (this.mob.isInLava)
            isClearForMovementBetween(this.mob, fromPos, toPos, false)
        else false
    }
}
