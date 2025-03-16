package club.redux.sunset.lavafishing.ai.path.pathnavigation

import club.redux.sunset.lavafishing.ai.path.nodeevaluator.NodeEvaluatorLavaSwim
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.level.Level
import net.minecraft.world.level.pathfinder.PathFinder
import net.minecraft.world.phys.Vec3

class PathNavigationLavaBound(mob: Mob, level: Level) : PathNavigation(mob, level) {
    private var allowBreaching = false

    override fun createPathFinder(maxVisitedNodes: Int): PathFinder {
//        this.allowBreaching = mob.type === EntityType.DOLPHIN
        this.nodeEvaluator = NodeEvaluatorLavaSwim(this.allowBreaching)
        return PathFinder(this.nodeEvaluator, maxVisitedNodes)
    }

    /**
     * If on ground or swimming and can swim
     */
    override fun canUpdatePath(): Boolean = this.allowBreaching || this.mob.isInLiquid
    override fun getTempMobPos(): Vec3 = Vec3(this.mob.x, this.mob.getY(0.5), this.mob.z)
    override fun getGroundY(pVec: Vec3): Double = pVec.y

    /**
     * # 检查生物是否能从pos1到pos2
     *
     * 这里isClearForMovementBetween的最后一个参数有误导，实际上这里是要不要考虑水体碰撞的参数
     * 水生生物就不用考虑水体碰撞
     */
    override fun canMoveDirectly(fromPos: Vec3, toPos: Vec3): Boolean {
        return isClearForMovementBetween(this.mob, fromPos, toPos, false)
    }

    override fun isStableDestination(pos: BlockPos): Boolean {
        return !this.level.getBlockState(pos).isSolidRender(this.level, pos)
    }

    /**
     * 鱼不能在水上漂，直接把这个方法覆盖掉
     */
    override fun setCanFloat(canSwim: Boolean): Unit = Unit
}