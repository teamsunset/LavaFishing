package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters
import net.minecraft.world.item.ItemStack

object ModCreativeModeTabs : Registrar<CreativeModeTab>(Registries.CREATIVE_MODE_TAB, BuiltConstants.MOD_ID) {
    val LAVA_FISHING = BuiltConstants.MOD_ID.register {
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + BuiltConstants.MOD_ID))
            .icon { ItemStack(ModItems.OBSIDIAN_FISHING_ROD.get()) }
            .displayItems { _: ItemDisplayParameters, o: CreativeModeTab.Output ->
                ModItems.getEntries().forEach(o::accept)
                ModItemsAqua.getEntries().forEach(o::accept)
            }
            .build()
    }
}
