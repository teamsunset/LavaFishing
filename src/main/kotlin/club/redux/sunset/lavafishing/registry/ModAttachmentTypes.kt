package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.NeoForgeRegistries
import java.util.function.Supplier

object ModAttachmentTypes : Registrar<AttachmentType<*>>(NeoForgeRegistries.ATTACHMENT_TYPES, BuiltConstants.MOD_ID) {
    val BOBBER_INVALID_FLUID_WARNED = this.register("bobber_invalid_fluid_warned") {
        AttachmentType.builder(Supplier<Boolean> { false }).build()
    }
}
