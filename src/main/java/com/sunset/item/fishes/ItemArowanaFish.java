package com.sunset.item.fishes;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class ItemArowanaFish extends Item
{
    public ItemArowanaFish() {
        super(GetProperties());
    }

    public static Properties GetProperties() {
        FoodProperties foodProperties = new FoodProperties.Builder()
                .nutrition(3)
                .saturationMod(0.6F)
                .build();
        return new Properties()
                .food(foodProperties)
                .fireResistant();
    }
}
