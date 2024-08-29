package club.redux.sunset.lavafishing.item.cuisine

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level

class ItemSpicyFishFillet : Item(
    Properties()
        .food(
            FoodProperties.Builder()
                .nutrition(7)
                .saturationModifier(0.8f)
                .build()
        )
) {
    override fun finishUsingItem(pStack: ItemStack, pLevel: Level, pLivingEntity: LivingEntity): ItemStack {
        pLivingEntity.remainingFireTicks = 20
        Items.MUSHROOM_STEW
        return super.finishUsingItem(pStack, pLevel, pLivingEntity)
    }
}