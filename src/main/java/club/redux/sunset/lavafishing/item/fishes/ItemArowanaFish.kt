package club.redux.sunset.lavafishing.item.fishes

import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item

class ItemArowanaFish : Item(
    Properties()
        .food(
            FoodProperties.Builder()
                .nutrition(3)
                .saturationMod(0.6f)
                .build()
        )
        .fireResistant()
)