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
    fun inject() {
        log4j.debug(Logging.LOADING, "Injecting @EventBusSubscriber kotlin objects in to the event bus......")

        ModList.get()
            .getModFileById(BuildConstants.MOD_ID)
            .file.scanResult.annotations.filter { annotationData ->
                Type.getType(Mod.EventBusSubscriber::class.java) == annotationData.annotationType
            }.forEach { annotationData ->
                val annotationMap = annotationData.annotationData
                val sides = getSides(annotationMap)
                val busTarget = getBusTarget(annotationMap)
                val clazzName = annotationData.clazz.className

                if (FMLEnvironment.dist in sides) {
                    val kClass = Class.forName(clazzName, true, javaClass.classLoader).kotlin

                    val kObject = try {
                        kClass.objectInstance
                    } catch (e: UnsupportedOperationException) {
                        if (e.message?.contains("file facades") == false) {
                            throw e
                        } else {
                            log4j.debug(Logging.LOADING, "Auto-subscribing kotlin class {} to {}", clazzName, busTarget)
                            busTarget.bus().get().register(kClass.java) // 注册类
                            return
                        }
                    }

                    if (kObject == null) {
                        return
                    }

                    log4j.debug(Logging.LOADING, "Auto-subscribing kotlin object {} to {}", clazzName, busTarget)

                    try {
                        busTarget.bus().get().register(kObject) // 注册对象
                    } catch (e: Throwable) {
                        log4j.fatal(Logging.LOADING, "Failed to load $clazzName for @EventBusSubscriber annotation", e)
                        throw RuntimeException(e)
                    }
                }
            }
    }

    private fun getSides(annotationMap: Map<String, Any>): List<Dist> =
        (annotationMap["value"] as? List<*>)
            ?.filterIsInstance<ModAnnotation.EnumHolder>()
            ?.map { Dist.valueOf(it.value) }
            ?: listOf(Dist.CLIENT, Dist.DEDICATED_SERVER)

    private fun getBusTarget(annotationMap: Map<String, Any>): Mod.EventBusSubscriber.Bus =
        (annotationMap["bus"] as? ModAnnotation.EnumHolder)
            ?.let { Mod.EventBusSubscriber.Bus.valueOf(it.value) }
            ?: Mod.EventBusSubscriber.Bus.FORGE
}