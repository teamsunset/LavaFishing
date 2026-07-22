package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.tool.registry.Registrar
import com.teammetallurgy.aquaculture.Aquaculture
import com.teammetallurgy.aquaculture.api.fishing.Hook
import com.teammetallurgy.aquaculture.api.fishing.Hook.HookBuilder
import com.teammetallurgy.aquaculture.item.HookItem
import net.minecraft.ChatFormatting
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.tags.FluidTags
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem

object ModItemsAqua : Registrar<Item>(BuiltInRegistries.ITEM, Aquaculture.MOD_ID) {
    // Hooks
    val OBSIDIAN_HOOK by this.registerHook(
        HookBuilder("obsidian")
            .setFluid(FluidTags.LAVA)
            .setColor(ChatFormatting.DARK_PURPLE)
            .build()
    )
    val DOUBLE_OBSIDIAN_HOOK by this.registerHook(
        HookBuilder("double_obsidian")
            .setFluid(FluidTags.LAVA)
            .setDoubleCatchChance(0.15)
            .build()
    )
    val GLOWSTONE_HOOK by this.registerHook(
        HookBuilder("glowstone")
            .setFluid(FluidTags.LAVA)
            .setColor(ChatFormatting.YELLOW)
            .setLuckModifier(1)
            .build()
    )
    val QUARTZ_HOOK by this.registerHook(
        HookBuilder("quartz")
            .setFluid(FluidTags.LAVA)
            .setDurabilityChance(0.30)
            .build()
    )
    val SOUL_SAND_HOOK by this.registerHook(
        HookBuilder("soul_sand")
            .setFluid(FluidTags.LAVA)
            .setColor(ChatFormatting.DARK_GRAY)
            .setCatchableWindow(40, 70)
            .build()
    )
    val OBSIDIAN_NOTE_HOOK by this.registerHook(
        HookBuilder("obsidian_note")
            .setColor(ChatFormatting.LIGHT_PURPLE)
            .setFluid(FluidTags.LAVA)
            .build()
    )

    private fun registerHook(hook: Hook) = this.register { name -> HookItem(hook, properties(name)) }
        .post { Hook.HOOKS[hook.name] = DeferredItem.createItem(it.key) }

    private fun properties(name: String) = Item.Properties().setId(this.createResourceKey(name))
}
