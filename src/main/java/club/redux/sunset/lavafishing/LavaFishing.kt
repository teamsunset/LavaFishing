package club.redux.sunset.lavafishing

import club.asynclab.web.BuildConstants
import club.redux.sunset.lavafishing.util.RegistryCollection.*
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(BuildConstants.MOD_ID)
class LavaFishing {
    init {
//        val eventBus = FMLJavaModLoadingContext.get().modEventBus
        BlockCollection.BLOCK_DEFERRED_REGISTER.register(MOD_BUS)
        CreativeModeTabCollection.CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(MOD_BUS)
        BlockEntityTypeCollection.BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register(MOD_BUS)
        MobEffectCollection.MOB_EFFECT_DEFERRED_REGISTER.register(MOD_BUS)
        EntityTypeCollection.ENTITY_TYPE_DEFERRED_REGISTER.register(MOD_BUS)
        ItemCollection.ITEM_DEFERRED_REGISTER.register(MOD_BUS)
        ParticleTypeCollection.PARTICLE_TYPE_DEFERRED_REGISTER.register(MOD_BUS)
        PotionCollection.POTION_DEFERRED_REGISTER.register(MOD_BUS)
    }
}