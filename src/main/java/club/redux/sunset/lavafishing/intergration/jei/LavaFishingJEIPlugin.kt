package club.redux.sunset.lavafishing.intergration.jei

import club.redux.sunset.lavafishing.LavaFishing
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.resources.ResourceLocation

@JeiPlugin
class LavaFishingJEIPlugin : IModPlugin {
    override fun getPluginUid(): ResourceLocation = LavaFishing.resourceLocation("jei_plugin")

    override fun registerRecipes(registration: IRecipeRegistration) {

    }


}