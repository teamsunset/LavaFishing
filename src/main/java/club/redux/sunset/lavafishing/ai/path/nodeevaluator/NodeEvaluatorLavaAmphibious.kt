package club.redux.sunset.lavafishing.ai.path.nodeevaluator

import net.minecraft.core.BlockPos
import net.minecraft.core.BlockPos.MutableBlockPos
import net.minecraft.core.Direction
import net.minecraft.tags.FluidTags
import net.minecraft.util.Mth
import net.minecraft.world.entity.Mob
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.PathNavigationRegion
import net.minecraft.world.level.pathfinder.BlockPathTypes
import net.minecraft.world.level.pathfinder.Node
import net.minecraft.world.level.pathfinder.Target
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator
import kotlin.math.max

/**
 * 两栖生物节点评估器
 *
 * @param prefersShallowSwimming 表示实体是否偏好在浅水中游泳。
 */
class NodeEvaluatorLavaAmphibious(private val prefersShallowSwimming: Boolean) : WalkNodeEvaluator() {
    // 用于在 done() 方法中恢复原始的路径寻找代价。
    private var oldWalkableCost = 0f
    private var oldWaterBorderCost = 0f

    /**
     * 准备路径寻找器，设置实体在不同地形上的路径寻找代价。
     *
     * @param pLevel 路径导航区域。
     * @param pMob 正在路径寻找的实体。
     */
    override fun prepare(pLevel: PathNavigationRegion, pMob: Mob) {
        super.prepare(pLevel, pMob)
        // 设置岩浆路径的寻找代价为 0，表示不避免岩浆。
        pMob.setPathfindingMalus(BlockPathTypes.LAVA, 0.0f)
        // 保存原始的可行走地形路径寻找代价，以便之后恢复。
        this.oldWalkableCost = pMob.getPathfindingMalus(BlockPathTypes.WALKABLE)
        // 设置可行走地形的路径寻找代价为 6，提高行走路径的优先级。
        pMob.setPathfindingMalus(BlockPathTypes.WALKABLE, 6.0f)
        // 保存原始的水边路径寻找代价，以便之后恢复。
        this.oldWaterBorderCost = pMob.getPathfindingMalus(BlockPathTypes.WATER_BORDER)
        // 设置水边路径的寻找代价为 4，调整对水边路径的偏好。
        pMob.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 4.0f)
    }

    /**
     * 当所有节点都被处理完毕且 PathEntity 已创建时，此方法被调用。
     * 恢复在 prepare() 方法中修改的路径寻找代价至原始值。
     */
    override fun done() {
        // 恢复可行走地形的原始路径寻找代价。
        mob.setPathfindingMalus(BlockPathTypes.WALKABLE, this.oldWalkableCost)
        // 恢复水边的原始路径寻找代价。
        mob.setPathfindingMalus(BlockPathTypes.WATER_BORDER, this.oldWaterBorderCost)
        super.done()
    }

    /**
     * 覆盖 getStart 方法以处理实体不在液体中时的起始节点，以及在液体中时的起始节点。
     *
     * @return 根据实体是否在液体中而不同计算得出的起始节点。
     */
    override fun getStart(): Node {
        return if (!this.mob.isInLava)
            super.getStart()
        else this.getStartNode(
            BlockPos(
                Mth.floor(mob.boundingBox.minX),
                Mth.floor(mob.boundingBox.minY + 0.5),
                Mth.floor(mob.boundingBox.minZ)
            )
        )
    }

    /**
     * 覆盖 getGoal 方法以返回经过节点转换的目标点。
     *
     * @param pX 目标 X 坐标。
     * @param pY 目标 Y 坐标。
     * @param pZ 目标 Z 坐标。
     * @return 根据给定坐标计算得出的目标节点。
     */
    override fun getGoal(pX: Double, pY: Double, pZ: Double): Target {
        return this.getTargetFromNode(this.getNode(Mth.floor(pX), Mth.floor(pY + 0.5), Mth.floor(pZ)))
    }

    /**
     * 覆盖 getNeighbors 方法以处理节点邻居的计算，考虑实体的特殊路径寻找需求。
     *
     * @param pOutputArray 输出节点数组。
     * @param pNode 当前节点。
     * @return 填充了邻居节点的数组大小。
     */
    override fun getNeighbors(outputNodes: Array<Node>, currentNode: Node): Int {
        var numNeighbors = super.getNeighbors(outputNodes, currentNode)

        // 获取当前节点上方、当前平面的方块类型
        val blockAbove = getCachedBlockType(mob, currentNode.x, currentNode.y + 1, currentNode.z)
        val blockCurrent = getCachedBlockType(mob, currentNode.x, currentNode.y, currentNode.z)

        // 计算步高
        val stepHeight =
            if (mob.getPathfindingMalus(blockAbove) >= 0.0f && blockCurrent != BlockPathTypes.STICKY_HONEY) {
                Mth.floor(max(1.0, mob.stepHeight.toDouble()))
            } else {
                0
            }

        // 获取当前节点所在平面的地面水平
        val groundLevel = getFloorLevel(BlockPos(currentNode.x, currentNode.y, currentNode.z))

        // 尝试获取当前节点上方的可接受节点
        val aboveNode = findAcceptedNode(
            currentNode.x,
            currentNode.y + 1,
            currentNode.z,
            max(0, stepHeight - 1),
            groundLevel,
            Direction.UP,
            blockCurrent
        )

        // 尝试获取当前节点下方的可接受节点
        val belowNode = findAcceptedNode(
            currentNode.x,
            currentNode.y - 1,
            currentNode.z,
            stepHeight,
            groundLevel,
            Direction.DOWN,
            blockCurrent
        )

        // 如果上方节点有效，则加入到邻居列表
        if (isVerticalNeighborValid(aboveNode, currentNode)) {
            outputNodes[numNeighbors++] = aboveNode!!
        }

        // 如果下方节点有效且当前下方不是活板门，则加入到邻居列表
        if (isVerticalNeighborValid(belowNode, currentNode) && blockCurrent != BlockPathTypes.TRAPDOOR) {
            outputNodes[numNeighbors++] = belowNode!!
        }

        // 调整水路径的成本，如果实体偏好浅水且节点在海平面以下一定深度
        for (index in 0 until numNeighbors) {
            val neighborNode = outputNodes[index]
            if (neighborNode.type == BlockPathTypes.WATER && prefersShallowSwimming && neighborNode.y < mob.level().seaLevel - 10) {
                ++neighborNode.costMalus
            }
        }

        return numNeighbors
    }

    /**
     * 检查垂直方向的邻居节点是否有效。
     *
     * @param pNeighbor 邻居节点。
     * @param pNode 当前节点。
     * @return 如果邻居节点有效且为水域类型，则返回 true。
     */
    private fun isVerticalNeighborValid(pNeighbor: Node?, pNode: Node): Boolean {
        return this.isNeighborValid(pNeighbor, pNode) && pNeighbor!!.type == BlockPathTypes.WATER
    }


    override fun isAmphibious(): Boolean = true


    /**
     * 返回指定位置的节点类型，考虑下方的方块。
     *
     * @param pLevel 方块获取器，用于获取世界状态。
     * @param pX X 坐标。
     * @param pY Y 坐标。
     * @param pZ Z 坐标。
     * @return 根据位置和下方方块类型计算得出的路径类型。
     */
    override fun getBlockPathType(pLevel: BlockGetter, pX: Int, pY: Int, pZ: Int): BlockPathTypes {
        val mutableBlockPos = MutableBlockPos()
        val fluidState = pLevel.getFluidState(mutableBlockPos.set(pX, pY, pZ))
        if (fluidState.`is`(FluidTags.LAVA)) {
            // 遍历方向，检查周围是否有阻碍类型，如果有，则将路径类型更改为水边。
            for (direction in Direction.entries) {
                val blockPathTypeAround = getBlockPathTypeRaw(pLevel, mutableBlockPos.set(pX, pY, pZ).move(direction))
                if (blockPathTypeAround == BlockPathTypes.BLOCKED) {
                    return BlockPathTypes.WATER_BORDER
                }
            }
            // 如果周围没有阻碍，则路径类型保持为岩浆。
            return BlockPathTypes.LAVA
        } else {
            // 如果当前位置不是岩浆，则根据下方方块类型获取路径类型。
            val staticBlockPathType = getBlockPathTypeStatic(pLevel, mutableBlockPos)
            return if (staticBlockPathType == BlockPathTypes.WATER)
                BlockPathTypes.BLOCKED
            else staticBlockPathType
        }
    }
}

