package club.redux.sunset.lavafishing

import club.redux.sunset.lavafishing.registry.*
import club.redux.sunset.lavafishing.util.Utils
import club.redux.sunset.lavafishing.util.Utils.resourceLocation
import com.mojang.logging.LogUtils
import net.neoforged.fml.common.Mod
import org.slf4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(BuiltConstants.MOD_ID)
object LavaFishing {
    val Any.logger: Logger by lazy { LogUtils.getLogger() }

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
