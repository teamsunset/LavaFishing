package club.redux.sunset.lavafishing.misc

import com.teammetallurgy.aquaculture.api.AquacultureAPI
import com.teammetallurgy.aquaculture.api.fishing.Hook.HookBuilder
import net.minecraft.ChatFormatting
import net.minecraft.tags.FluidTags
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem

object Hooks {
    @JvmField val OBSIDIAN: DeferredItem<Item> = AquacultureAPI.registerHook(
        HookBuilder("obsidian")
            .setColor(ChatFormatting.DARK_PURPLE)
            .setFluid(FluidTags.LAVA)
            .build()
    )
    @JvmField val DOUBLE_OBSIDIAN: DeferredItem<Item> = AquacultureAPI.registerHook(
        HookBuilder("double_obsidian")
            .setFluid(FluidTags.LAVA)
            .setDoubleCatchChance(0.15)
            .build()
    )
    @JvmField val GLOWSTONE: DeferredItem<Item> = AquacultureAPI.registerHook(
        HookBuilder("glowstone")
            .setColor(ChatFormatting.YELLOW)
            .setFluid(FluidTags.LAVA)
            .setLuckModifier(1)
            .build()
    )
    @JvmField val QUARTZ: DeferredItem<Item> = AquacultureAPI.registerHook(
        HookBuilder("quartz")
            .setFluid(FluidTags.LAVA)
            .setDurabilityChance(0.30)
            .build()
    )
    @JvmField val SOUL_SAND: DeferredItem<Item> = AquacultureAPI.registerHook(
        HookBuilder("soul_sand")
            .setColor(ChatFormatting.DARK_GRAY)
            .setFluid(FluidTags.LAVA)
            .setCatchableWindow(40, 70)
            .build()
    )
    @JvmField val OBSIDIAN_NOTE: DeferredItem<Item> = AquacultureAPI.registerHook(
        HookBuilder("obsidian_note")
            .setColor(ChatFormatting.LIGHT_PURPLE)
            .setFluid(FluidTags.LAVA)
            .build()
    )
}
