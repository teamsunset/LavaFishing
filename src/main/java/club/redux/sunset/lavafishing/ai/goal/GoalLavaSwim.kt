package club.redux.sunset.lavafishing.ai.goal

import net.minecraft.core.BlockPos
import net.minecraft.tags.FluidTags
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal
import net.minecraft.world.entity.ai.util.DefaultRandomPos
import net.minecraft.world.entity.animal.AbstractFish
import net.minecraft.world.phys.Vec3

class GoalLavaFishSwim(private val fish: AbstractFish) : RandomSwimmingGoal(fish, 1.0, 40) {
    override fun getPosition(): Vec3? {
        val randomPos = { DefaultRandomPos.getPos(this.fish, 10, 7) }

        var vec3 = randomPos()
        var i = 0
        while (
            vec3 != null &&
            !this.fish.level().getFluidState(BlockPos.containing(vec3)).`is`(FluidTags.LAVA) &&
            i++ < 10
        ) {
            vec3 = randomPos()
        }

        return vec3
    }
}