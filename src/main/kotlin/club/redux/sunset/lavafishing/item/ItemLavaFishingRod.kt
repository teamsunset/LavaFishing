package club.redux.sunset.lavafishing.item

import com.teammetallurgy.aquaculture.item.AquaFishingRodItem
import net.minecraft.world.item.ToolMaterial

class ItemLavaFishingRod(
    tier: ToolMaterial,
    properties: Properties,
) : AquaFishingRodItem(tier, properties.fireResistant().durability((tier.durability * 0.64).toInt()))
