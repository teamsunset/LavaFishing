package club.redux.sunset.lavafishing.event

import club.redux.sunset.lavafishing.BuildConstants
import com.teammetallurgy.aquaculture.entity.AquaFishingBobberEntity
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.FluidTags
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.material.FluidState
import net.minecraftforge.event.TickEvent

object EventFishingHook {
    private const val WARNED_TAG = "${BuildConstants.MOD_ID}.warned_invalid_fluid"
    private const val INVALID_FLUID_KEY = "message.${BuildConstants.MOD_ID}.hook.invalid_fluid"
    private const val INVALID_FLUID_GENERIC_KEY = "message.${BuildConstants.MOD_ID}.hook.invalid_fluid_generic"

    fun onPlayerTick(event: TickEvent.PlayerTickEvent) {
        if (event.phase != TickEvent.Phase.END) return
        val player = event.player as? ServerPlayer ?: return
        val bobber = player.fishing as? AquaFishingBobberEntity ?: return
        if (bobber.isRemoved) return

        val fluidState = bobber.level().getFluidState(bobber.blockPosition())
        val warnedTag = bobber.persistentData
        val isInUnsupportedFluid = !fluidState.isEmpty && bobber.hook.fluids.none { fluidState.`is`(it) }
        val wasUnsupported = warnedTag.getBoolean(WARNED_TAG)

        if (!isInUnsupportedFluid) {
            if (wasUnsupported) {
                warnedTag.remove(WARNED_TAG)
            }
            return
        }

        warnedTag.putBoolean(WARNED_TAG, true)
        if (wasUnsupported) return

        player.displayClientMessage(getInvalidFluidMessage(fluidState), true)
    }

    private fun getInvalidFluidMessage(fluidState: FluidState): Component {
        val fluidName = when {
            fluidState.`is`(FluidTags.LAVA) -> Component.translatable(Blocks.LAVA.descriptionId)
            fluidState.`is`(FluidTags.WATER) -> Component.translatable(Blocks.WATER.descriptionId)
            else -> return Component.translatable(INVALID_FLUID_GENERIC_KEY)
        }

        return Component.translatable(INVALID_FLUID_KEY, fluidName)
    }
}
