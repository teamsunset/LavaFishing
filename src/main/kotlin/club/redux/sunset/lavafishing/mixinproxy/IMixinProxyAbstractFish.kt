package club.redux.sunset.lavafishing.mixinproxy

import net.minecraft.world.entity.animal.AbstractFish
import net.minecraft.world.phys.Vec3

interface IMixinProxyAbstractFish : IMixinProxy<AbstractFish> {
    fun aiStepFromMob() {}

    fun travelFromLivingEntity(vec3: Vec3) {}
}