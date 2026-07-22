package club.redux.sunset.lavafishing.item.fish

import club.redux.sunset.lavafishing.registry.ModItems
import com.teammetallurgy.aquaculture.api.AquacultureAPI
import com.teammetallurgy.aquaculture.item.FishItem
import net.minecraft.world.food.FoodProperties
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent

open class ItemLavaFish : FishItem {
    var filletAmount = 0

    constructor(properties: Properties) : super(properties.fireResistant()) {
        this.filletAmount = 4
    }

    constructor(
        properties: Properties,
        foodProperties: FoodProperties,
    ) : super(properties.fireResistant(), foodProperties) {
        this.filletAmount = foodProperties.nutrition * 2
    }

    companion object {
        // FISH_DATA
        // TODO
        fun onSetup(event: FMLCommonSetupEvent) {
            ModItems.getEntriesIsInstance<ItemLavaFish>().forEach {
                AquacultureAPI.FISH_DATA.add(it, 100.0, 200.0, it.filletAmount)
            }
        }
    }
}
