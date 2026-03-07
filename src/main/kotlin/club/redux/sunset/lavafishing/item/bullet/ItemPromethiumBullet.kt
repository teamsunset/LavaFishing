package club.redux.sunset.lavafishing.item.bullet

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.entity.bullet.EntityBullet
import club.redux.sunset.lavafishing.entity.bullet.EntityPromethiumBullet
import club.redux.sunset.lavafishing.registry.ModDataComponentTypes
import club.redux.sunset.lavafishing.registry.ModEntityTypes
import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.contents.TranslatableContents
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent

class ItemPromethiumBullet : ItemBullet(
    Properties().fireResistant().component(ModDataComponentTypes.BULLET_DIVISION_TIMES, 1),
    { ModEntityTypes.PROMETHIUM_BULLET.get() }
) {
    override fun createBullet(pLevel: Level, pAmmo: ItemStack, pShooter: Entity?, pWeapon: ItemStack?): EntityBullet {
        return super.createBullet(pLevel, pAmmo, pShooter, pWeapon).apply {
            if (this !is EntityPromethiumBullet) return@apply
            divisionTimes = pAmmo.components.get(ModDataComponentTypes.BULLET_DIVISION_TIMES.get()) ?: 1
        }
    }

    companion object {
        fun rawTooltipProcessorProvider(event: ItemTooltipEvent): (MutableComponent) -> Unit = { component ->
            (component.contents as? TranslatableContents)?.key?.let { key ->
                if (key == "${BuiltConstants.MOD_ID}.${ModItems.PROMETHIUM_BULLET.key!!.location().path}.tooltip.title") {
                    val times = event.itemStack.get(ModDataComponentTypes.BULLET_DIVISION_TIMES) ?: 1
                    if (times > 1)
                        component.append(
                            Component.literal(" x${event.itemStack.get(ModDataComponentTypes.BULLET_DIVISION_TIMES)}")
                                .withStyle(ChatFormatting.WHITE)
                        )
                }
            }
        }
    }
}