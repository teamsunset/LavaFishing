package club.redux.sunset.lavafishing.ai.path.pathnavigation

import club.redux.sunset.lavafishing.ai.path.nodeevaluator.NodeEvaluatorLavaAmphibious
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.level.Level
import net.minecraft.world.level.pathfinder.PathFinder
import net.minecraft.world.phys.Vec3

open class PathNavigationLavaAmphibious(mob: Mob, level: Level) : PathNavigation(mob, level) {
    override fun createPathFinder(pMaxVisitedNodes: Int): PathFinder {
        this.nodeEvaluator = NodeEvaluatorLavaAmphibious(false)
        return PathFinder(this.nodeEvaluator, pMaxVisitedNodes)
    }

    /**
     * 当角色（或实体）在地面上或者在游泳，并且能够游泳时，该函数返回 true，表示可以更新路径。
     */
    override fun canUpdatePath(): Boolean = true
    override fun getTempMobPos(): Vec3 = Vec3(mob.x, mob.getY(0.5), mob.z)
    override fun getGroundY(vec3: Vec3): Double = vec3.y

    /**
     * 检查指定的实体是否可以安全地走到指定位置。
     */
    override fun canMoveDirectly(fromPos: Vec3, toPos: Vec3): Boolean {
        return if (this.mob.isInLava)
            isClearForMovementBetween(this.mob, fromPos, toPos, false)
        else false
    }

    override fun isStableDestination(blockPos: BlockPos): Boolean {
        return !level.getBlockState(blockPos.below()).isAir
    }

    override fun setCanFloat(canSwim: Boolean): Unit = Unit
}