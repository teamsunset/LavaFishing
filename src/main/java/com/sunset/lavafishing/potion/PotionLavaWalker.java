package com.sunset.lavafishing.potion;

import com.sunset.lavafishing.util.RegistryCollections.EffectCollection;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class PotionLavaWalker extends Potion
{
    public PotionLavaWalker() {
        super(new MobEffectInstance(EffectCollection.EFFECT_LAVA_WALKER.get(), 4800));
    }
}
