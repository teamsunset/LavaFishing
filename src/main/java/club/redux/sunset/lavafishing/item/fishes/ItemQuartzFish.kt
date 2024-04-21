package club.redux.sunset.lavafishing.item.fishes;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemQuartzFish extends Item {
    public ItemQuartzFish() {
        super(new Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(4)
                        .saturationMod(0.8F)
                        .build())
                .fireResistant()
        );
    }


}
