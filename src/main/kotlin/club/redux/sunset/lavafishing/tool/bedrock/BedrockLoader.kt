package club.redux.sunset.lavafishing.tool.bedrock

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.util.UtilLogger.logger
import com.google.gson.Gson
import com.google.gson.JsonObject
import net.minecraft.client.Minecraft
import net.minecraft.client.animation.AnimationDefinition
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimplePreparableReloadListener
import net.minecraft.util.profiling.ProfilerFiller
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent
import net.neoforged.neoforge.client.resources.VanillaClientListeners

object BedrockLoader {
    private val gson = Gson()

    @Volatile
    private var snapshot = Snapshot(emptyMap())

    private val listener = object : SimplePreparableReloadListener<Snapshot>() {
        override fun prepare(resourceManager: ResourceManager, profiler: ProfilerFiller): Snapshot {
            val animations = resourceManager.listResources("animation") { id ->
                id.namespace == BuiltConstants.MOD_ID && id.path.endsWith(".animation.json")
            }.mapValues { (id, resource) ->
                try {
                    resource.openAsReader().use { reader ->
                        AnimationManager(BedrockAnimationParser.parse(gson.fromJson(reader, JsonObject::class.java)))
                    }
                } catch (exception: Exception) {
                    throw IllegalArgumentException("Failed to load Bedrock animation $id", exception)
                }
            }
            return Snapshot(animations.toMap())
        }

        override fun apply(preparations: Snapshot, resourceManager: ResourceManager, profiler: ProfilerFiller) {
            snapshot = preparations
            logger.info("Loaded ${preparations.animations.size} Bedrock animation files")
        }
    }

    fun geometry(id: Identifier): LayerDefinition {
        try {
            return Minecraft.getInstance().resourceManager.openAsReader(id).use { reader ->
                BedrockGeometryParser.parse(gson.fromJson(reader, JsonObject::class.java))
            }
        } catch (exception: Exception) {
            throw IllegalArgumentException("Failed to load Bedrock geometry $id", exception)
        }
    }

    fun animations(id: Identifier): AnimationManager = snapshot.animations[id]
        ?: throw IllegalStateException("Bedrock animation '$id' is not loaded")

    class AnimationManager internal constructor(private val animations: Map<String, AnimationDefinition>) {
        operator fun get(name: String): AnimationDefinition = animations[name]
            ?: throw IllegalArgumentException("Bedrock animation '$name' is not defined")
    }

    private data class Snapshot(
        val animations: Map<Identifier, AnimationManager>,
    )

    fun onAddClientReloadListeners(event: AddClientReloadListenersEvent) {
        val listenerId = LavaFishing.identifier("bedrock_resources")
        event.addListener(listenerId, this.listener)
        event.addDependency(listenerId, VanillaClientListeners.ENTITY_RENDERER)
    }
}
