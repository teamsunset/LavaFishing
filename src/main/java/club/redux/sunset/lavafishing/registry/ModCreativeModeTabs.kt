package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters
import net.minecraft.world.item.ItemStack

object ModCreativeModeTabs {
    @JvmField val REGISTER = UtilRegister.create(Registries.CREATIVE_MODE_TAB, BuildConstants.MOD_ID)

    @JvmField val LAVA_FISHING = REGISTER.registerKt(BuildConstants.MOD_ID) {
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + BuildConstants.MOD_ID))
            .icon { ItemStack(ModItems.OBSIDIAN_FISHING_ROD.get()) }
            .displayItems { p: ItemDisplayParameters?, o: CreativeModeTab.Output ->
                ModItems.REGISTER.entries.forEach { o.accept(it.get()) }
            }
            .build()
    }
}
