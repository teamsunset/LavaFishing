package club.redux.sunset.lavafishing.item.fish

import club.redux.sunset.lavafishing.registry.ModItems.REGISTER
import com.teammetallurgy.aquaculture.api.AquacultureAPI
import com.teammetallurgy.aquaculture.item.FishItem
import net.minecraft.world.food.FoodProperties
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent

open class ItemLavaFish : FishItem {
    var filletAmount = 0

    constructor() : super() {
        this.filletAmount = 4
    }

    constructor(foodProperties: FoodProperties) : super(foodProperties) {
        this.filletAmount = foodProperties.nutrition * 2
    }

    override fun isFireResistant(): Boolean = true


    companion object {
        // FISH_DATA
        // TODO
        @JvmStatic
        fun onSetup(event: FMLCommonSetupEvent) {
            REGISTER.entries.map { it.get() }.filterIsInstance<ItemLavaFish>().forEach {
                AquacultureAPI.FISH_DATA.add(it, 100.0, 200.0, it.filletAmount)
            }
        }
    }
}