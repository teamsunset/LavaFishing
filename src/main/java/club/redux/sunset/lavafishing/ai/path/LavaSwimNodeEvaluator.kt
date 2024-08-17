package club.redux.sunset.lavafishing.ai.path

import club.redux.sunset.lavafishing.entity.EntityLavaFish
import com.google.common.collect.Maps
import it.unimi.dsi.fastutil.longs.Long2ObjectFunction
import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import net.minecraft.core.BlockPos
import net.minecraft.core.BlockPos.MutableBlockPos
import net.minecraft.core.Direction
import net.minecraft.util.Mth
import net.minecraft.world.entity.Mob
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.PathNavigationRegion
import net.minecraft.world.level.pathfinder.BlockPathTypes
import net.minecraft.world.level.pathfinder.Node
import net.minecraft.world.level.pathfinder.NodeEvaluator
import net.minecraft.world.level.pathfinder.Target
import kotlin.math.max

class LavaSwimNodeEvaluator(private val allowBreaching: Boolean) : NodeEvaluator() {
    private val pathTypesByPosCache: Long2ObjectMap<BlockPathTypes> = Long2ObjectOpenHashMap()

    override fun prepare(pLevel: PathNavigationRegion, pMob: Mob) {
        super.prepare(pLevel, pMob)
        pathTypesByPosCache.clear()
    }

    /**
     * 当处理完所有节点并创建 PathEntity 时，将调用此方法
     */
    override fun done() {
        super.done()
        pathTypesByPosCache.clear()
    }

    override fun getStart(): Node {
        return this.getNode(
            Mth.floor(this.mob.boundingBox.minX),
            Mth.floor(this.mob.boundingBox.minY + 0.5),
            Mth.floor(this.mob.boundingBox.minZ)
        )
    }

    /**
     * 这个Target类是启发式搜索的目标，之后要重复利用（不断更新）
     */
    override fun getGoal(pX: Double, pY: Double, pZ: Double): Target {
        return this.getTargetFromNode(this.getNode(Mth.floor(pX), Mth.floor(pY), Mth.floor(pZ)))
    }

    override fun getNeighbors(pOutputArray: Array<Node>, pNode: Node): Int {
        var i = 0
        val map: MutableMap<Direction, Node?> = Maps.newEnumMap(Direction::class.java)

        // 上下左右前后
        for (direction in Direction.entries) {
            val node =
                this.findAcceptedNode(pNode.x + direction.stepX, pNode.y + direction.stepY, pNode.z + direction.stepZ)
            map[direction] = node
            if (this.isNodeValid(node)) {
                pOutputArray[i++] = node!!
            }
        }

        // 当前平面的斜角
        for (direction1 in Direction.Plane.HORIZONTAL) {
            val direction2 = direction1.clockWise
            val node1 = this.findAcceptedNode(
                pNode.x + direction1.stepX + direction2.stepX,
                pNode.y,
                pNode.z + direction1.stepZ + direction2.stepZ
            )
            if (this.isDiagonalNodeValid(node1, map[direction1], map[direction2])) {
                pOutputArray[i++] = node1!!
            }
        }

        return i
    }

    private fun isNodeValid(pNode: Node?): Boolean = pNode != null && !pNode.closed

    private fun isDiagonalNodeValid(pRoot: Node?, pHorizontal: Node?, pClockwise: Node?): Boolean {
        return this.isNodeValid(pRoot) &&
                pHorizontal != null && pHorizontal.costMalus >= 0.0f &&
                pClockwise != null && pClockwise.costMalus >= 0.0f
    }

    private fun findAcceptedNode(pX: Int, pY: Int, pZ: Int): Node? {
        val blockPathTypes = this.getCachedBlockType(pX, pY, pZ)
        if (this.allowBreaching && blockPathTypes == BlockPathTypes.BREACH || blockPathTypes == BlockPathTypes.LAVA) {
            if (mob.getPathfindingMalus(blockPathTypes) >= 0.0f) {
                return this.getNode(pX, pY, pZ).apply {
                    type = blockPathTypes
                    costMalus = max(costMalus.toDouble(), f.toDouble()).toFloat()
                    if (level.getFluidState(BlockPos(pX, pY, pZ)).isEmpty) {
                        costMalus += 8.0f
                    }
                }
            }
        }

        return null
    }

    private fun getCachedBlockType(pX: Int, pY: Int, pZ: Int): BlockPathTypes {
        return pathTypesByPosCache.computeIfAbsent(
            BlockPos.asLong(pX, pY, pZ),
            Long2ObjectFunction { this.getBlockPathType(this.level, pX, pY, pZ) }
        )
    }

    /**
     * 将下面的块考虑在内，返回指定位置的节点类型
     */
    override fun getBlockPathType(pLevel: BlockGetter, pX: Int, pY: Int, pZ: Int): BlockPathTypes {
        return this.getBlockPathType(pLevel, pX, pY, pZ, this.mob)
    }

    override fun getBlockPathType(pLevel: BlockGetter, pX: Int, pY: Int, pZ: Int, pMob: Mob): BlockPathTypes {
        val blockPos = MutableBlockPos()

        val isAcceptedFluids = { p: BlockPos -> EntityLavaFish.acceptedFluids.any { pLevel.getFluidState(p).`is`(it) } }

        for (i in pX until pX + this.entityWidth) {
            for (j in pY until pY + this.entityHeight) {
                for (k in pZ until pZ + this.entityDepth) {
                    blockPos.set(i, j, k)
                    if (
                        pLevel.getFluidState(blockPos).isEmpty &&
                        isAcceptedFluids(blockPos.below()) &&
                        pLevel.getBlockState(blockPos).isAir
                    ) {
                        return BlockPathTypes.BREACH
                    }

                    if (!isAcceptedFluids(blockPos)) {
                        return BlockPathTypes.BLOCKED
                    }
                }
            }
        }

        return if (isAcceptedFluids(blockPos)) BlockPathTypes.LAVA else BlockPathTypes.BLOCKED
    }
}