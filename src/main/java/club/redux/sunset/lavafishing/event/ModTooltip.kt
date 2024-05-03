package club.redux.sunset.lavafishing.event

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.registry.ModTags
import com.mojang.blaze3d.platform.InputConstants
import com.teammetallurgy.aquaculture.Aquaculture
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.registries.ForgeRegistries
import org.lwjgl.glfw.GLFW

object ModTooltip {
    fun onItemTooltip(event: ItemTooltipEvent) {
        if (event.itemStack.isEmpty) return
        if (!event.itemStack.`is`(ModTags.Items.TOOLTIP)) return
        val id = ForgeRegistries.ITEMS.getKey(event.itemStack.item) ?: return
        val tooltipPath = id.path + ".tooltip"
        if (InputConstants.isKeyDown(Minecraft.getInstance().window.window, GLFW.GLFW_KEY_LEFT_SHIFT)) {
            event.toolTip.add(
                Component.translatable("${BuildConstants.MOD_ID}.$tooltipPath.desc").withStyle(ChatFormatting.DARK_RED)
            )
        } else {
            event.toolTip.add(
                Component.translatable("${BuildConstants.MOD_ID}.$tooltipPath.title").withStyle(ChatFormatting.DARK_RED)
                    .append(" ")
                    .append(Component.translatable(Aquaculture.MOD_ID + ".shift").withStyle(ChatFormatting.DARK_GRAY))
            )
        }
    }
}