package club.redux.sunset.lavafishing

import club.asynclab.web.BuildConstants
import club.redux.sunset.lavafishing.annotation.KtObjectEventBusSubscriberHandler
import club.redux.sunset.lavafishing.util.RegistryCollection.*
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

@Mod(BuildConstants.MOD_ID)
class LavaFishing {
    init {
        val eventBus = FMLJavaModLoadingContext.get().modEventBus
        BlockCollection.BLOCK_DEFERRED_REGISTER.register(eventBus)
        BlockEntityTypeCollection.BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus)
        CreativeModeTabCollection.CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(eventBus)
        EntityTypeCollection.ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus)
        ItemCollection.ITEM_DEFERRED_REGISTER.register(eventBus)
        MobEffectCollection.MOB_EFFECT_DEFERRED_REGISTER.register(eventBus)
        ParticleTypeCollection.PARTICLE_TYPE_DEFERRED_REGISTER.register(eventBus)
        PotionCollection.POTION_DEFERRED_REGISTER.register(eventBus)

        KtObjectEventBusSubscriberHandler.inject(BuildConstants.MOD_ID)
    }
}