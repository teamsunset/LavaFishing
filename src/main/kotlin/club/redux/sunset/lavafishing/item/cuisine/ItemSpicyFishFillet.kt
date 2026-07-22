package club.redux.sunset.lavafishing.item.cuisine

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level

class ItemSpicyFishFillet(properties: Properties) : ItemSimpleFood(
    properties.usingConvertsTo(Items.BOWL).stacksTo(1),
    { nutrition(7).saturationModifier(0.8f) }
) {
    override fun finishUsingItem(pStack: ItemStack, pLevel: Level, pLivingEntity: LivingEntity): ItemStack {
        pLivingEntity.remainingFireTicks = 20
        return super.finishUsingItem(pStack, pLevel, pLivingEntity)
    }
}
