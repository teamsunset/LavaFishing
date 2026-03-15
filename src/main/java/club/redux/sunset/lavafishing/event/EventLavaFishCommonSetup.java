package club.redux.sunset.lavafishing.event;

import club.redux.sunset.lavafishing.BuildConstants;
import club.redux.sunset.lavafishing.entity.EntityLavaFish;
import club.redux.sunset.lavafishing.mixin.accessor.AccessorCat;
import club.redux.sunset.lavafishing.mixin.accessor.AccessorOcelot;
import club.redux.sunset.lavafishing.registry.ModEntityTypes;
import com.teammetallurgy.aquaculture.init.AquaItems;
import com.teammetallurgy.aquaculture.misc.StackHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = BuildConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class EventLavaFishCommonSetup {
    private EventLavaFishCommonSetup() {
    }

    @SubscribeEvent
    public static void onSetup(FMLCommonSetupEvent event) {
        try {
            Ingredient catBreedingItems = Ingredient.of(Items.COD, Items.SALMON);
            List<ItemStack> lavaFish = new ArrayList<>();

            ModEntityTypes.INSTANCE.getEntriesByEntityParentClass(EntityLavaFish.class)
                    .forEach(entry -> {
                        var location = ForgeRegistries.ENTITY_TYPES.getKey(entry.get());
                        if (location != null) {
                            var item = ForgeRegistries.ITEMS.getValue(location);
                            if (item != null) {
                                lavaFish.add(new ItemStack(item));
                            }
                        }
                    });

            lavaFish.removeIf(stack -> stack.getItem() == AquaItems.JELLYFISH.get());
            Ingredient mergedTemptIngredient = StackHelper.mergeIngredient(catBreedingItems, StackHelper.ingredientFromStackList(lavaFish));

            event.enqueueWork(() -> {
                AccessorCat.setTemptIngredient(mergedTemptIngredient);
                AccessorOcelot.setTemptIngredient(mergedTemptIngredient);
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
