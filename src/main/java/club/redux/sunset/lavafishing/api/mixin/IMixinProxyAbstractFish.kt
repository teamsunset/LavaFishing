package club.redux.sunset.lavafishing.api.mixin

import net.minecraft.world.entity.animal.AbstractFish
import net.minecraft.world.phys.Vec3

internal interface IMixinProxyAbstractFish : IMixinProxy<AbstractFish> {
    fun mobAiStep() {}

    fun livingEntityTravel(vec3: Vec3) {}
}