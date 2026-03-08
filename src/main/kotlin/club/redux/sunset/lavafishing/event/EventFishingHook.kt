package club.redux.sunset.lavafishing.event

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.registry.ModAttachmentTypes
import com.teammetallurgy.aquaculture.entity.AquaFishingBobberEntity
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.FluidTags
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.material.FluidState
import net.neoforged.neoforge.event.tick.EntityTickEvent

object EventFishingHook {
    private const val INVALID_FLUID_KEY = "message.${BuiltConstants.MOD_ID}.hook.invalid_fluid"
    private const val INVALID_FLUID_GENERIC_KEY = "message.${BuiltConstants.MOD_ID}.hook.invalid_fluid_generic"

    @JvmStatic
    fun onEntityTickPost(event: EntityTickEvent.Post) {
        val bobber = event.entity as? AquaFishingBobberEntity ?: return
        val level = bobber.level()

        if (level.isClientSide || bobber.isRemoved) {
            return
        }

        val warningAttachment = ModAttachmentTypes.BOBBER_INVALID_FLUID_WARNED.get()
        val fluidState = level.getFluidState(bobber.blockPosition())
        val isInUnsupportedFluid = !fluidState.isEmpty && bobber.hook.fluids.none { fluidState.`is`(it) }
        val wasUnsupported = bobber.getExistingDataOrNull(warningAttachment) == true

        if (!isInUnsupportedFluid) {
            if (wasUnsupported) {
                bobber.removeData(warningAttachment)
            }
            return
        }

        bobber.setData(warningAttachment, true)

        if (wasUnsupported) {
            return
        }

        val player = bobber.playerOwner as? ServerPlayer ?: return

        player.displayClientMessage(this.getInvalidFluidMessage(fluidState), true)
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
