package club.redux.sunset.lavafishing.registry

import club.asynclab.web.BuildConstants
import club.redux.sunset.lavafishing.entity.EntityObsidianHook
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraftforge.registries.ForgeRegistries

object RegistryEntityType {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.ENTITY_TYPES, BuildConstants.MOD_ID)

    @JvmField val OBSIDIAN_HOOK = REGISTER.registerKt("obsidian_hook") { EntityObsidianHook.BuildEntityType() }
}
