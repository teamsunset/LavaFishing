package club.redux.sunset.lavafishing

import club.redux.sunset.lavafishing.registry.*
import club.redux.sunset.lavafishing.util.Utils
import club.redux.sunset.lavafishing.util.Utils.resourceLocation
import net.neoforged.fml.common.Mod
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(BuiltConstants.MOD_ID)
object LavaFishing {
    fun resourceLocation(path: String) = BuiltConstants.MOD_ID.resourceLocation(path)

    init {
        Utils.asciiArt()

        listOf(
            ModArmorMaterials,
            ModBlockEntityTypes,
            ModBlocks,
            ModCreativeModeTabs,
            ModDataComponentTypes,
            ModEntityTypes,
            ModItems,
            ModItemsAqua,
            ModMobEffects,
            ModParticleTypes,
            ModPotions,
            ModRecipeSerializers,
            ModSoundEvents
        ).forEach { it.attach(MOD_BUS) }
    }
}
