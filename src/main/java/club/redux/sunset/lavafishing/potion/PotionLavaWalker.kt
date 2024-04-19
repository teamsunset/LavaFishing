package club.redux.sunset.lavafishing.potion

import club.redux.sunset.lavafishing.registry.RegistryMobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion

class PotionLavaWalker : Potion(
    MobEffectInstance(
        RegistryMobEffect.LAVA_WALKER.get(),
        4800
    )
)
