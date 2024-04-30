package club.redux.sunset.lavafishing.item

import club.redux.sunset.lavafishing.misc.ModTiers
import com.teammetallurgy.aquaculture.item.ItemFilletKnife
import net.minecraft.world.item.ItemStack

class ItemPromethiumFilletKnife : ItemFilletKnife(ModTiers.PROMETHIUM) {
    override fun isFireResistant(): Boolean = true
    override fun getMaxDamage(stack: ItemStack?): Int = -1
}