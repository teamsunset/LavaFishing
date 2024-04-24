package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.registries.ForgeRegistries

object ModSoundEvents {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.SOUND_EVENTS, BuildConstants.MOD_ID)

    @JvmField val SLINGSHOT = REGISTER.registerKt("slingshot") {
        SoundEvent.createVariableRangeEvent(ResourceLocation(BuildConstants.MOD_ID, "slingshot"))
    }
}