package club.redux.sunset.lavafishing.ai.goal

import club.redux.sunset.lavafishing.entity.EntityLavaFish.Companion.acceptedFluids
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal
import net.minecraft.world.entity.ai.util.DefaultRandomPos
import net.minecraft.world.entity.animal.AbstractFish
import net.minecraft.world.phys.Vec3

class GoalLavaFishSwim(private val fish: AbstractFish) : RandomSwimmingGoal(fish, 1.0, 40) {
    override fun getPosition(): Vec3? {
        val isAcceptedFluids = { p: BlockPos -> acceptedFluids.any { fish.level().getFluidState(p).`is`(it) } }
        val randomPos = { DefaultRandomPos.getPos(fish, 10, 7) }

        var vec3 = randomPos()
        var i = 0
        while (vec3 != null && !isAcceptedFluids(BlockPos.containing(vec3)) && i++ < 10) {
            vec3 = randomPos()
        }

        return vec3
    }
}