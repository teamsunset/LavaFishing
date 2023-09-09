package com.sunset.lavafishing;

import com.mojang.logging.LogUtils;
import com.sunset.lavafishing.util.Reference;
import com.sunset.lavafishing.util.RegistryCollection.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Reference.MOD_ID)
public class LavaFishing {
    public static final Logger DEBUG_LOGGER = LogUtils.getLogger();

    public LavaFishing() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockCollection.BLOCK_DEFERRED_REGISTER.register(eventBus);
        CreativeModeTabCollection.CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus);
        BlockEntityTypeCollection.BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus);
        MobEffectCollection.MOB_EFFECT_DEFERRED_REGISTER.register(eventBus);
        EntityTypeCollection.ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus);
        ItemCollection.ITEM_DEFERRED_REGISTER.register(eventBus);
        ParticleTypeCollection.PARTICLE_TYPE_DEFERRED_REGISTER.register(eventBus);
        PotionCollection.POTION_DEFERRED_REGISTER.register(eventBus);
    }
}
