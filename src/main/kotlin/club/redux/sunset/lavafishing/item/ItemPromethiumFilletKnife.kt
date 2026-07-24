package club.redux.sunset.lavafishing.item

import club.redux.sunset.lavafishing.misc.ModToolMaterials
import com.teammetallurgy.aquaculture.item.ItemFilletKnife
import net.minecraft.world.item.ItemStack

class ItemPromethiumFilletKnife(properties: Properties) :
    ItemFilletKnife(ModToolMaterials.PROMETHIUM, properties.fireResistant()) {
    override fun getMaxDamage(stack: ItemStack): Int = -1
}
