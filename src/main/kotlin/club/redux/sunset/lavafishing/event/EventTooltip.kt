package club.redux.sunset.lavafishing.event

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.item.bullet.ItemPromethiumBullet
import club.redux.sunset.lavafishing.misc.ModTags
import com.mojang.blaze3d.platform.InputConstants
import com.teammetallurgy.aquaculture.Aquaculture
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.locale.Language
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent
import org.lwjgl.glfw.GLFW

object EventTooltip {
    private fun getTooltips(
        tooltipPath: String,
        style: Style,
        processor: (MutableComponent) -> Unit,
    ): List<Component> {
        val tooltips = mutableListOf<Component>()
        val isShiftDown = InputConstants.isKeyDown(Minecraft.getInstance().window.window, GLFW.GLFW_KEY_LEFT_SHIFT)
        val key = "$tooltipPath.${if (isShiftDown) "desc" else "title"}"

        fun MutableComponent.appendShift() = this.append(" ")
            .append(Component.translatable(Aquaculture.MOD_ID + ".shift").withStyle(ChatFormatting.DARK_GRAY))

        tooltips.add(
            Component.translatable(key).withStyle(style)
                .also(processor)
                .also { if (!isShiftDown) it.appendShift() }
        )

        var index = 1
        val indexKey = { "$key.$index" }
        while (Language.getInstance().has(indexKey())) {
            tooltips.add(
                Component.translatable(indexKey())
                    .withStyle(style)
                    .also(processor)
                    .also { if (!isShiftDown) it.appendShift() }
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
        val isNeptunium = event.itemStack.tags.anyMatch { it == ModTags.Item.NEPTUNIUM }
        val color = if (isNeptunium) ChatFormatting.AQUA else ChatFormatting.DARK_RED
        val tooltips = getTooltips(tooltipPath, Style.EMPTY.applyFormats(color)) {
            listOf(
                ItemPromethiumBullet.rawTooltipProcessorProvider(event)
            ).forEach { processor -> processor(it) }
        }

        event.toolTip.addAll(tooltips)
    }
}