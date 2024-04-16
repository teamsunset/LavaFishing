package club.redux.sunset.lavafishing.util.RegistryCollection;

import club.asynclab.web.BuildConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static club.redux.sunset.lavafishing.util.RegistryCollection.ItemCollection.ITEM_DEFERRED_REGISTER;

public class CreativeModeTabCollection {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BuildConstants.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB_LAVA_FISHING = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(BuildConstants.MOD_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + BuildConstants.MOD_ID))
            .icon(() -> new ItemStack(ItemCollection.ITEM_OBSIDIAN_FISHING_ROD.get()))
            .displayItems((p, o) -> ITEM_DEFERRED_REGISTER.getEntries().forEach(i -> o.accept(i.get())))
            .build());
}
