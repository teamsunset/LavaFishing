package club.redux.sunset.lavafishing

import club.redux.sunset.lavafishing.registry.*
import club.redux.sunset.lavafishing.util.Utils
import club.redux.sunset.lavafishing.util.Utils.resourceLocation
import com.mojang.logging.LogUtils
import net.minecraftforge.fml.common.Mod
import org.slf4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(BuiltConstants.MOD_ID)
object LavaFishing {
    val Any.logger: Logger by lazy { LogUtils.getLogger() }

    fun resourceLocation(path: String) = BuiltConstants.MOD_ID.resourceLocation(path)

    init {
        Utils.asciiArt()

        listOf(
            ModBlockEntityTypes,
            ModBlocks,
            ModCreativeModeTabs,
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
