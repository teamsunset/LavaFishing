package club.redux.sunset.lavafishing.potion

import club.redux.sunset.lavafishing.registry.ModMobEffects
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion

class PotionLavaWalker : Potion(
    MobEffectInstance(ModMobEffects.LAVA_WALKER.get(), 4800)
)
