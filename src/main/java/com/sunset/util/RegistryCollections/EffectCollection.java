package com.sunset.util.RegistryCollections;

import com.sunset.effect.EffectBlessed;
import com.sunset.effect.EffectEndlessFlame;
import com.sunset.effect.EffectLavaWalker;
import net.minecraft.world.effect.MobEffect;

import java.util.ArrayList;
import java.util.List;

public class EffectCollection
{
    public static final List<MobEffect> RegistryEffects = new ArrayList<>();

    public static final EffectLavaWalker EFFECT_LAVA_WALKER = register(new EffectLavaWalker(), "lava_walker");
    public static final EffectBlessed EFFECT_BLESSED = register(new EffectBlessed(), "blessed");
    public static final EffectEndlessFlame EFFECT_ENDLESS_FLAME = register(new EffectEndlessFlame(), "endless_flame");


    public static <T extends MobEffect> T register(T effect, String registryName) {
        effect.setRegistryName(registryName);
        RegistryEffects.add(effect);
        return effect;
    }
}
