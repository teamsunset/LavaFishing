package club.redux.sunset.lavafishing.annotation

import com.github.dsx137.jable.extension.log4j
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.Logging
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.fml.loading.moddiscovery.ModAnnotation
import net.minecraftforge.forgespi.language.ModFileScanData
import org.objectweb.asm.Type

object KtObjectEventBusSubscriberHandler {
    fun inject(modId: String) {
        log4j.debug(Logging.LOADING, "Injecting @EventBusSubscriber in to the event bus......")

        ModList.get()
            .getModFileById(modId)
            .file.scanResult.annotations.filter { annotationData ->
                Type.getType(Mod.EventBusSubscriber::class.java) == annotationData.annotationType
            }.forEach { annotationData ->
                val sides = getSides(annotationData)
                val busTarget = getBusTarget(annotationData)
                val clazzName = annotationData.clazz.className

                if (FMLEnvironment.dist in sides) {
                    val kClass = Class.forName(clazzName, true, this::class.java.classLoader).kotlin
                    val kObject = kClass.objectInstance ?: return

                    log4j.debug(Logging.LOADING, "Auto-subscribing injecting object {} to {}", clazzName, busTarget)

                    try {
                        busTarget.bus().get().register(kObject)
                    } catch (e: Throwable) {
                        log4j.fatal(Logging.LOADING, "Failed to load $clazzName for @EventBusSubscriber injection.", e)
                        throw RuntimeException(e)
                    }
                }
            }
    }

    private fun getSides(annotationData: ModFileScanData.AnnotationData): List<Dist> =
        (annotationData.annotationData["value"] as? List<*>)
            ?.filterIsInstance<ModAnnotation.EnumHolder>()
            ?.map { Dist.valueOf(it.value) }
            ?: listOf(Dist.CLIENT, Dist.DEDICATED_SERVER)

    private fun getBusTarget(annotationData: ModFileScanData.AnnotationData): Mod.EventBusSubscriber.Bus =
        (annotationData.annotationData["bus"] as? ModAnnotation.EnumHolder)
            ?.let { Mod.EventBusSubscriber.Bus.valueOf(it.value) }
            ?: Mod.EventBusSubscriber.Bus.FORGE
}