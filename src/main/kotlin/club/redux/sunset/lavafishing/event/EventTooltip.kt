package club.redux.sunset.lavafishing.event

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.misc.ModTags
import club.redux.sunset.lavafishing.registry.ModDataComponentTypes
import club.redux.sunset.lavafishing.registry.ModItems
import com.mojang.blaze3d.platform.InputConstants
import com.teammetallurgy.aquaculture.Aquaculture
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.locale.Language
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.contents.TranslatableContents
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent
import org.lwjgl.glfw.GLFW

object EventTooltip {
    private fun getTooltips(
        tooltipPath: String,
        style: Style,
        processor: (MutableComponent) -> MutableComponent,
    ): List<Component> {
        val tooltips = mutableListOf<Component>()
        val isShiftDown = InputConstants.isKeyDown(Minecraft.getInstance().window, GLFW.GLFW_KEY_LEFT_SHIFT)
        val key = "$tooltipPath.${if (isShiftDown) "desc" else "title"}"

        fun MutableComponent.appendShift() = this.append(" ")
            .append(Component.translatable(Aquaculture.MOD_ID + ".shift").withStyle(ChatFormatting.DARK_GRAY))

        tooltips.add(
            Component.translatable(key).withStyle(style)
                .let(processor)
                .let { if (isShiftDown) it else it.appendShift() }
        )

        var index = 1
        val indexKey = { "$key.$index" }
        val indexComponent = { Component.translatable(indexKey()).withStyle(style) }
        while (Language.getInstance().has(indexKey())) {
            tooltips.add(
                indexComponent()
                    .let(processor)
                    .let { if (isShiftDown) it else it.appendShift() }
            )

            index++
        }

        return tooltips
    }

    fun onItemTooltip(event: ItemTooltipEvent) {
        if (event.itemStack.isEmpty) return
        if (!event.itemStack.`is`(ModTags.Item.TOOLTIP)) return
        val itemLocation = BuiltInRegistries.ITEM.getKey(event.itemStack.item)
        if (itemLocation == BuiltInRegistries.ITEM.defaultKey) return

        val tooltipPath = "${BuiltConstants.MOD_ID}.${itemLocation.path}.tooltip"
        val color =
            if (event.itemStack.tags().toList().contains(ModTags.Item.NEPTUNIUM)) ChatFormatting.AQUA
            else ChatFormatting.DARK_RED
        val tooltips = getTooltips(tooltipPath, Style.EMPTY.applyFormats(color)) {
            (it.contents as? TranslatableContents)?.key?.let { key ->
                if (key == "${BuiltConstants.MOD_ID}.${ModItems.PROMETHIUM_BULLET.key!!.identifier().path}.tooltip.title") {
                    val times = event.itemStack.get(ModDataComponentTypes.BULLET_DIVISION_TIMES) ?: 1
                    if (times > 1)
                        it.append(
                            Component.literal(" x${event.itemStack.get(ModDataComponentTypes.BULLET_DIVISION_TIMES)}")
                                .withStyle(ChatFormatting.WHITE)
                        )
                }
            }
            it
        }

        event.toolTip.addAll(tooltips)
    }
}
