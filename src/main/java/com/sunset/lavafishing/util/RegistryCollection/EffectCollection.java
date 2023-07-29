package com.sunset.lavafishing.util.RegistryCollection;

import com.sunset.lavafishing.effect.EffectBlessed;
import com.sunset.lavafishing.effect.EffectEndlessFlame;
import com.sunset.lavafishing.util.Reference;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectCollection
{
    public static final DeferredRegister<MobEffect> MOB_EFFECT_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Reference.MOD_ID);

    public static final RegistryObject<MobEffect> EFFECT_BLESSED = MOB_EFFECT_DEFERRED_REGISTER.register("blessed", EffectBlessed::new);
    public static final RegistryObject<MobEffect> EFFECT_ENDLESS_FLAME = MOB_EFFECT_DEFERRED_REGISTER.register("endless_flame", EffectEndlessFlame::new);
    public static final RegistryObject<MobEffect> EFFECT_LAVA_WALKER = MOB_EFFECT_DEFERRED_REGISTER.register("lava_walker", EffectEndlessFlame::new);


}
