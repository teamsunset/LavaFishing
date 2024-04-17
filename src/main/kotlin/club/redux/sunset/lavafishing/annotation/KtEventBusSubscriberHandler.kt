package club.redux.sunset.lavafishing.annotation

import club.asynclab.web.BuildConstants
import com.github.dsx137.jable.extension.log4j
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.Logging
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.fml.loading.moddiscovery.ModAnnotation
import org.objectweb.asm.Type

object KtEventBusSubscriberHandler {
    private val EVENT_BUS_SUBSCRIBER: Type = Type.getType(Mod.EventBusSubscriber::class.java)

    fun inject() {
        log4j.debug(Logging.LOADING, "Injecting @EventBusSubscriber kotlin objects in to the event bus......")

        val data = ModList.get()
            .getModFileById(BuildConstants.MOD_ID)
            .file.scanResult.annotations.filter { annotationData ->
                EVENT_BUS_SUBSCRIBER == annotationData.annotationType
            }

        for (annotationData in data) {
            val annotationMap = annotationData.annotationData
            val sides = getSides(annotationMap)
            val busTarget = getBusTarget(annotationMap)

            if (FMLEnvironment.dist in sides) {
                val kClass = Class.forName(annotationData.clazz.className, true, javaClass.classLoader).kotlin

                val kObject: Any?

                try {
                    kObject = kClass.objectInstance
                } catch (unsupported: UnsupportedOperationException) {
                    if (unsupported.message?.contains("file facades") == false) {
                        throw unsupported
                    } else {
                        log4j.debug(
                            Logging.LOADING,
                            "Auto-subscribing kotlin class {} to {}",
                            annotationData.clazz.className,
                            busTarget
                        )
                        busTarget.bus().get().register(kClass.java)
                        continue
                    }
                }

                if (kObject == null) {
                    continue
                }

                try {
                    log4j.debug(
                        Logging.LOADING,
                        "Auto-subscribing kotlin object {} to {}",
                        annotationData.clazz.className,
                        busTarget
                    )

                    busTarget.bus().get().register(kObject)
                } catch (e: Throwable) {
                    log4j.fatal(
                        Logging.LOADING,
                        "Failed to load mod class ${annotationData.clazz.className} for @EventBusSubscriber annotation",
                        e
                    )
                    throw RuntimeException(e)
                }

            }
        }
    }

    private fun getSides(annotationMap: Map<String, Any>): List<Dist> {
        val sidesHolders = annotationMap["value"]

        return if (sidesHolders != null) {
            (sidesHolders as List<*>).map { data -> Dist.valueOf((data as ModAnnotation.EnumHolder).value) }
        } else {
            listOf(Dist.CLIENT, Dist.DEDICATED_SERVER)
        }
    }

    private fun getBusTarget(annotationMap: Map<String, Any>): Mod.EventBusSubscriber.Bus {
        val busTargetHolder = annotationMap["bus"]

        return if (busTargetHolder != null) {
            Mod.EventBusSubscriber.Bus.valueOf((busTargetHolder as ModAnnotation.EnumHolder).value)
        } else {
            Mod.EventBusSubscriber.Bus.FORGE
        }
    }
}