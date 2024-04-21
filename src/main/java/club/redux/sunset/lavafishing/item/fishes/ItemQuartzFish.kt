package club.redux.sunset.lavafishing.item.fishes

import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item

class ItemQuartzFish : Item(
    Properties()
        .food(
            FoodProperties.Builder()
                .nutrition(4)
                .saturationMod(0.8f)
                .build()
        )
        .fireResistant()
)