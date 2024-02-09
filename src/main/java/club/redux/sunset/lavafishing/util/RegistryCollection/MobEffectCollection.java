package club.redux.sunset.lavafishing.util.RegistryCollection;

import club.redux.sunset.lavafishing.effect.EffectBlessed;
import club.redux.sunset.lavafishing.effect.EffectEndlessFlame;
import club.redux.sunset.lavafishing.effect.EffectLavaWalker;
import club.redux.sunset.lavafishing.util.Reference;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MobEffectCollection
{
    public static final DeferredRegister<MobEffect> MOB_EFFECT_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Reference.MOD_ID);

    public static final RegistryObject<MobEffect> EFFECT_BLESSED = MOB_EFFECT_DEFERRED_REGISTER.register("blessed", EffectBlessed::new);
    public static final RegistryObject<MobEffect> EFFECT_ENDLESS_FLAME = MOB_EFFECT_DEFERRED_REGISTER.register("endless_flame", EffectEndlessFlame::new);
    public static final RegistryObject<MobEffect> EFFECT_LAVA_WALKER = MOB_EFFECT_DEFERRED_REGISTER.register("lava_walker", EffectLavaWalker::new);


}
