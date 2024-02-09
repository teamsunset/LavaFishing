package club.redux.sunset.lavafishing.item.fishes;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class ItemQuartzFish extends Item
{
    public ItemQuartzFish() {
        super(GetProperties());
    }

    public static Item.Properties GetProperties() {
        FoodProperties foodProperties = new FoodProperties.Builder()
                .nutrition(4)
                .saturationMod(0.8F)
                .build();
        return new Properties()
                .food(foodProperties)
                .fireResistant();
    }
}
