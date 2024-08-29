package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvent

object ModSoundEvents {
    @JvmField val REGISTER = UtilRegister.create(BuiltInRegistries.SOUND_EVENT, BuildConstants.MOD_ID)

    @JvmField val SLINGSHOT = REGISTER.registerKt("slingshot") {
        SoundEvent.createVariableRangeEvent(LavaFishing.resourceLocation("slingshot"))
    }
}