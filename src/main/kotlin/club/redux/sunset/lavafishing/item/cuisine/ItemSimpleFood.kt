package club.redux.sunset.lavafishing.item.cuisine

import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item

open class ItemSimpleFood(
    propertiesModifier: Properties.() -> Properties = { this },
    foodPropertiesBuilderModifier: FoodProperties.Builder.() -> FoodProperties.Builder,
) : Item(Properties().run(propertiesModifier).food(FoodProperties.Builder().run(foodPropertiesBuilderModifier).build()))