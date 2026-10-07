package club.redux.sunset.lavafishing.mixinproxy

import net.minecraft.world.entity.animal.fish.AbstractFish

interface IMixinProxyAbstractFish : IMixinProxy<AbstractFish> {
    fun aiStepFromMob() {}
}
