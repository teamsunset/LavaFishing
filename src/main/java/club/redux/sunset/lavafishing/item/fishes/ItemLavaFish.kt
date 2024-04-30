package club.redux.sunset.lavafishing.item.fishes

import com.teammetallurgy.aquaculture.item.FishItem
import net.minecraft.world.food.FoodProperties

open class ItemLavaFish : FishItem {
    constructor() : super()

    constructor(foodProperties: FoodProperties) : super(foodProperties)

    override fun isFireResistant(): Boolean = true
}