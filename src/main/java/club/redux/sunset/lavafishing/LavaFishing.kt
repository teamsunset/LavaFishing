package club.redux.sunset.lavafishing;

import club.redux.sunset.lavafishing.util.Reference;
import club.redux.sunset.lavafishing.util.RegistryCollection.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MOD_ID)
public class LavaFishing {

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
