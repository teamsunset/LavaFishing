package club.redux.sunset.lavafishing

import club.asynclab.web.BuildConstants
import club.redux.sunset.lavafishing.annotation.KtObjectEventBusSubscriberHandler
import club.redux.sunset.lavafishing.registry.*
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

@Mod(BuildConstants.MOD_ID)
class LavaFishing {
    init {
        val eventBus = FMLJavaModLoadingContext.get().modEventBus
        ModBlocks.REGISTER.register(eventBus)
        ModBlockEntityTypes.REGISTER.register(eventBus)
        ModCreativeModeTabs.REGISTER.register(eventBus)
        ModItems.REGISTER.register(eventBus)
        ModEntityTypes.REGISTER.register(eventBus)
        ModMobEffects.REGISTER.register(eventBus)
        ModParticleTypes.REGISTER.register(eventBus)
        ModPotions.REGISTER.register(eventBus)

        KtObjectEventBusSubscriberHandler.inject(BuildConstants.MOD_ID)
    }
}