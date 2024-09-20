package club.redux.sunset.lavafishing

import club.redux.sunset.lavafishing.misc.Hooks
import club.redux.sunset.lavafishing.registry.*
import net.minecraft.resources.ResourceLocation
import net.neoforged.fml.common.Mod
import org.slf4j.LoggerFactory
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(BuildConstants.MOD_ID)
object LavaFishing {
    @JvmStatic
    fun resourceLocation(path: String): ResourceLocation =
        ResourceLocation.fromNamespaceAndPath(BuildConstants.MOD_ID, path)

    init {
        this.javaClass.getResourceAsStream("/ascii-art.txt")?.let {
            it.bufferedReader().use { reader ->
                reader.lines().forEach { line ->
                    LoggerFactory.getLogger(this.javaClass).info(line)
                }
            }
        }
        ModArmorMaterials.REGISTER.register(MOD_BUS)
        ModBlockEntityTypes.REGISTER.register(MOD_BUS)
        ModBlocks.REGISTER.register(MOD_BUS)
        ModCreativeModeTabs.REGISTER.register(MOD_BUS)
        ModEntityTypes.REGISTER.register(MOD_BUS)
        ModItems.REGISTER.register(MOD_BUS)
        ModMobEffects.REGISTER.register(MOD_BUS)
        ModParticleTypes.REGISTER.register(MOD_BUS)
        ModPotions.REGISTER.register(MOD_BUS)
        ModSoundEvents.REGISTER.register(MOD_BUS)
        Hooks // 仅仅是加载一下这个类
    }
}
