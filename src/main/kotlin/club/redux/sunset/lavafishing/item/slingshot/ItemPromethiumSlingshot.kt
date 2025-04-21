package club.redux.sunset.lavafishing.item.slingshot

import club.redux.sunset.lavafishing.misc.ModTiers
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemPromethiumSlingshot : ItemSlingshot(
    ModTiers.PROMETHIUM,
    Properties().fireResistant()
) {

    override fun onUseTick(pLevel: Level, pLivingEntity: LivingEntity, pStack: ItemStack, pRemainingUseDuration: Int) {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration)
        if (pLivingEntity !is Player || pLevel.isClientSide) return

        val item = pStack.item as ItemPromethiumSlingshot
        val duration = item.getUseDuration(pStack)
        var overtime = duration - pRemainingUseDuration
        overtime *= item.getChargeMultiplier(pStack)
        if (overtime < 0) return
        val timePower = getPowerForTime(overtime)
        if (timePower == 1.0F) {
            pLivingEntity.releaseUsingItem()
        }
    }
}