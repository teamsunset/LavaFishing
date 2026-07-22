package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvent

object ModSoundEvents : Registrar<SoundEvent>(BuiltInRegistries.SOUND_EVENT, BuiltConstants.MOD_ID) {
    val SLINGSHOT by this.register { SoundEvent.createVariableRangeEvent(LavaFishing.identifier(it)) }
}
