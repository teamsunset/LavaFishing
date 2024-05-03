package club.redux.sunset.lavafishing.item.fishes

import com.teammetallurgy.aquaculture.item.FishItem
import net.minecraft.world.food.FoodProperties

open class ItemLavaFish : FishItem {
    var filletAmount = 0

    constructor() : super() {
        this.filletAmount = 4
    }

    constructor(foodProperties: FoodProperties) : super(foodProperties) {
        this.filletAmount = foodProperties.nutrition * 2
    }

    override fun isFireResistant(): Boolean = true
}