package club.redux.sunset.lavafishing.event

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.misc.ModTags
import com.mojang.blaze3d.platform.InputConstants
import com.teammetallurgy.aquaculture.Aquaculture
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent
import org.lwjgl.glfw.GLFW

object EventTooltip {
    fun onItemTooltip(event: ItemTooltipEvent) {
        if (event.itemStack.isEmpty) return
        if (!event.itemStack.`is`(ModTags.Items.TOOLTIP)) return
        val id = BuiltInRegistries.ITEM.getKey(event.itemStack.item)
        if (id == BuiltInRegistries.ITEM.defaultKey) return
        val tooltipPath = "${BuildConstants.MOD_ID}.${id.path}.tooltip"
        val key = if (InputConstants.isKeyDown(Minecraft.getInstance().window.window, GLFW.GLFW_KEY_LEFT_SHIFT)) {
            "$tooltipPath.desc"
        } else {
            "$tooltipPath.title"
        }

        event.toolTip.add(
            Component.translatable(key).withStyle(ChatFormatting.DARK_RED)
                .append(" ")
                .append(Component.translatable(Aquaculture.MOD_ID + ".shift").withStyle(ChatFormatting.DARK_GRAY))
        )

        var index = 1
        val indexKey = { "$key.$index" }
        val indexComponent = { Component.translatable(indexKey()).withStyle(ChatFormatting.DARK_RED) }
        while (indexComponent().string != indexKey()) {
            event.toolTip.add(
                indexComponent()
                    .append(" ")
                    .append(Component.translatable(Aquaculture.MOD_ID + ".shift").withStyle(ChatFormatting.DARK_GRAY))
            )

            index++
        }
    }
}