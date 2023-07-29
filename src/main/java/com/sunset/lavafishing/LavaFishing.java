package com.sunset.lavafishing;

import com.mojang.logging.LogUtils;
import com.sunset.lavafishing.util.Reference;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.sunset.lavafishing.util.RegistryCollection.CreativeModeTabCollection.CREATIVE_MODE_TAB_DEFERRED_REGISTER;
import static com.sunset.lavafishing.util.RegistryCollection.EffectCollection.MOB_EFFECT_DEFERRED_REGISTER;
import static com.sunset.lavafishing.util.RegistryCollection.EntityTypeCollection.ENTITY_TYPE_DEFERRED_REGISTER;
import static com.sunset.lavafishing.util.RegistryCollection.ItemCollection.ITEM_DEFERRED_REGISTER;
import static com.sunset.lavafishing.util.RegistryCollection.ParticleTypeCollection.PARTICLE_TYPE_DEFERRED_REGISTER;
import static com.sunset.lavafishing.util.RegistryCollection.PotionCollection.POTION_DEFERRED_REGISTER;

@Mod(Reference.MOD_ID)
public class LavaFishing
{
    public static final Logger SG_LOGGER = LogUtils.getLogger();

    public LavaFishing() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus);
        MOB_EFFECT_DEFERRED_REGISTER.register(eventBus);
        ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus);
        ITEM_DEFERRED_REGISTER.register(eventBus);
        PARTICLE_TYPE_DEFERRED_REGISTER.register(eventBus);
        POTION_DEFERRED_REGISTER.register(eventBus);
    }
}
