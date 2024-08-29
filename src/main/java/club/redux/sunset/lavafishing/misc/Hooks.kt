package club.redux.sunset.lavafishing.misc

import club.redux.sunset.lavafishing.BuildConstants
import com.teammetallurgy.aquaculture.api.fishing.Hook
import com.teammetallurgy.aquaculture.api.fishing.Hook.HookBuilder
import net.minecraft.ChatFormatting
import net.minecraft.tags.FluidTags
import net.neoforged.fml.common.EventBusSubscriber

@EventBusSubscriber(modid = BuildConstants.MOD_ID) // 使Forge加载这个类
object Hooks {
    @JvmField val OBSIDIAN: Hook = HookBuilder("obsidian")
        .setColor(ChatFormatting.DARK_PURPLE)
        .setFluid(FluidTags.LAVA)
        .build()
    @JvmField val DOUBLE_OBSIDIAN: Hook = HookBuilder("double_obsidian")
        .setFluid(FluidTags.LAVA)
        .setDoubleCatchChance(0.15)
        .build()
    @JvmField val GLOWSTONE: Hook = HookBuilder("glowstone")
        .setColor(ChatFormatting.YELLOW)
        .setFluid(FluidTags.LAVA)
        .setLuckModifier(1)
        .build()
    @JvmField val QUARTZ: Hook = HookBuilder("quartz")
        .setFluid(FluidTags.LAVA)
        .setDurabilityChance(0.30)
        .build()
    @JvmField val SOUL_SAND: Hook = HookBuilder("soul_sand")
        .setColor(ChatFormatting.DARK_GRAY)
        .setFluid(FluidTags.LAVA)
        .setCatchableWindow(40, 70)
        .build()
    @JvmField val OBSIDIAN_NOTE: Hook = HookBuilder("obsidian_note")
        .setColor(ChatFormatting.LIGHT_PURPLE)
        .setFluid(FluidTags.LAVA)
        .build()
}
