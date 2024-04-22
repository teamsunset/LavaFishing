package club.redux.sunset.lavafishing

import club.redux.sunset.lavafishing.registry.*
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(BuildConstants.MOD_ID)
class LavaFishing {
    init {
//        val eventBus = FMLJavaModLoadingContext.get().modEventBus
        ModBlocks.REGISTER.register(MOD_BUS)
        ModBlockEntityTypes.REGISTER.register(MOD_BUS)
        ModCreativeModeTabs.REGISTER.register(MOD_BUS)
        ModItems.REGISTER.register(MOD_BUS)
        ModEntityTypes.REGISTER.register(MOD_BUS)
        ModMobEffects.REGISTER.register(MOD_BUS)
        ModParticleTypes.REGISTER.register(MOD_BUS)
        ModPotions.REGISTER.register(MOD_BUS)
    }
}