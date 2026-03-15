package club.redux.sunset.lavafishing.item.bullet

import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.misc.ModTiers
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import net.minecraft.ChatFormatting
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.contents.TranslatableContents
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemPromethiumBullet : ItemBullet(
    ModTiers.PROMETHIUM,
    Properties().fireResistant(),
    { ModEntityTypes.PROMETHIUM_BULLET.get() }
) {
    override fun createBullet(pLevel: Level, pStack: ItemStack, pShooter: LivingEntity): EntityBullet {
        return super.createBullet(pLevel, pStack, pShooter).apply {
            if (this is EntityPromethiumBullet) {
                this.divisionTimes = getDivisionTimes(pStack)
            }
        }
    }

    companion object {
        private const val DIVISION_TIMES_KEY = "DivisionTimes"

        fun getDivisionTimes(stack: ItemStack): Int {
            val tag = stack.tag ?: return 1
            return if (tag.contains(DIVISION_TIMES_KEY, Tag.TAG_INT.toInt())) {
                tag.getInt(DIVISION_TIMES_KEY).coerceAtLeast(1)
            } else {
                1
            }
        }

        fun setDivisionTimes(stack: ItemStack, divisionTimes: Int): ItemStack {
            if (divisionTimes <= 1) {
                stack.removeTagKey(DIVISION_TIMES_KEY)
            } else {
                stack.orCreateTag.putInt(DIVISION_TIMES_KEY, divisionTimes)
            }
            return stack
        }

        fun appendTooltipCount(component: MutableComponent, stack: ItemStack, tooltipKey: String) {
            val key = (component.contents as? TranslatableContents)?.key ?: return
            if (key != tooltipKey) return

            val divisionTimes = getDivisionTimes(stack)
            if (divisionTimes > 1) {
                component.append(Component.literal(" x$divisionTimes").withStyle(ChatFormatting.WHITE))
            }
        }
    }
}
