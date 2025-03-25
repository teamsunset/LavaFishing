package club.redux.sunset.lavafishing.item

import com.teammetallurgy.aquaculture.item.AquaFishingRodItem
import net.minecraft.world.item.Tier

class ItemFishingRod(
    tier: Tier,
    propertiesModifier: Properties.() -> Properties = { this },
) : AquaFishingRodItem(tier, propertiesModifier(Properties().durability((tier.uses * 0.64).toInt())))