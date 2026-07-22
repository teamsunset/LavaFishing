package club.redux.sunset.lavafishing.item.cuisine

import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item

open class ItemSimpleFood(
    properties: Properties,
    foodPropertiesBuilderModifier: FoodProperties.Builder.() -> FoodProperties.Builder,
) : Item(properties.food(FoodProperties.Builder().run(foodPropertiesBuilderModifier).build()))
