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
        RegistryBlock.REGISTER.register(eventBus)
        RegistryBlockEntityType.REGISTER.register(eventBus)
        RegistryCreativeModeTab.REGISTER.register(eventBus)
        RegistryItem.REGISTER.register(eventBus)
        RegistryMobEffect.REGISTER.register(eventBus)
        RegistryParticleType.REGISTER.register(eventBus)
        RegistryPotion.REGISTER.register(eventBus)

        KtObjectEventBusSubscriberHandler.inject(BuildConstants.MOD_ID)
    }
}