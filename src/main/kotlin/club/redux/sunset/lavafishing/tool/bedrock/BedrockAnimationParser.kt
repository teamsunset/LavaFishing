package club.redux.sunset.lavafishing.tool.bedrock

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.client.animation.AnimationChannel
import net.minecraft.client.animation.AnimationDefinition
import net.minecraft.client.animation.Keyframe
import net.minecraft.client.animation.KeyframeAnimations
import org.joml.Vector3fc

internal object BedrockAnimationParser {
    fun parse(root: JsonObject): Map<String, AnimationDefinition> {
        val animations = root.getAsJsonObject("animations")
            ?: throw IllegalArgumentException("Missing animations object")

        return animations.entrySet().associate { (animationName, animationElement) ->
            animationName to parseAnimation(animationName, animationElement.asJsonObject)
        }
    }

    private fun parseAnimation(name: String, animation: JsonObject): AnimationDefinition {
        val channels = mutableListOf<Pair<String, AnimationChannel>>()
        animation["bones"]?.asJsonObject?.entrySet()?.forEach { (boneName, boneElement) ->
            boneElement.asJsonObject.entrySet().forEach { (targetName, channelElement) ->
                val target = parseTarget(name, boneName, targetName)
                channels += boneName to parseChannel(name, boneName, targetName, target, channelElement)
            }
        }

        val inferredLength = channels.maxOfOrNull { (_, channel) ->
            channel.keyframes().maxOf(Keyframe::timestamp)
        } ?: 0f
        val builder = AnimationDefinition.Builder
            .withLength(animation["animation_length"]?.asFloat ?: inferredLength)
            .apply { if (animation["loop"]?.asBoolean == true) looping() }
        channels.forEach { (boneName, channel) -> builder.addAnimation(boneName, channel) }

        return builder.build()
    }

    private fun parseTarget(
        animationName: String,
        boneName: String,
        targetName: String,
    ): AnimationChannel.Target = when (targetName.lowercase()) {
        "rotation" -> AnimationChannel.Targets.ROTATION
        "position" -> AnimationChannel.Targets.POSITION
        "scale" -> AnimationChannel.Targets.SCALE
        else -> throw IllegalArgumentException(
            "Unknown animation target '$targetName' in animation '$animationName', bone '$boneName'",
        )
    }

    private fun parseChannel(
        animationName: String,
        boneName: String,
        targetName: String,
        target: AnimationChannel.Target,
        element: JsonElement,
    ): AnimationChannel {
        val parsedKeyframes = if (element.isJsonObject) {
            element.asJsonObject.entrySet()
                .map { (timestamp, value) ->
                    val time = timestamp.toFloatOrNull()
                        ?: throw IllegalArgumentException(
                            "Invalid timestamp '$timestamp' in animation '$animationName', bone '$boneName', target '$targetName'",
                        )
                    parseKeyframe(time, target, value, animationName, boneName, targetName)
                }
                .sortedBy(ParsedKeyframe::timestamp)
        } else {
            val vector = convertVector(target, parseVector(element, animationName, boneName, targetName))
            listOf(ParsedKeyframe(0f, vector, vector, AnimationChannel.Interpolations.LINEAR))
        }

        if (parsedKeyframes.isEmpty()) {
            throw IllegalArgumentException(
                "Empty animation channel in animation '$animationName', bone '$boneName', target '$targetName'",
            )
        }
        val keyframes = parsedKeyframes.mapIndexed { index, keyframe ->
            Keyframe(
                keyframe.timestamp,
                keyframe.preTarget,
                keyframe.postTarget,
                if (index == 0) AnimationChannel.Interpolations.LINEAR else parsedKeyframes[index - 1].outgoingInterpolation,
            )
        }
        return AnimationChannel(target, *keyframes.toTypedArray())
    }

    private fun parseKeyframe(
        timestamp: Float,
        target: AnimationChannel.Target,
        element: JsonElement,
        animationName: String,
        boneName: String,
        targetName: String,
    ): ParsedKeyframe {
        if (!element.isJsonObject) {
            val vector = convertVector(target, parseVector(element, animationName, boneName, targetName))
            return ParsedKeyframe(timestamp, vector, vector, AnimationChannel.Interpolations.LINEAR)
        }

        val keyframe = element.asJsonObject
        if (!keyframe.has("pre") && !keyframe.has("post")) {
            throw IllegalArgumentException(
                "Keyframe at $timestamp has neither pre nor post in animation '$animationName', bone '$boneName', target '$targetName'",
            )
        }

        val pre = convertVector(
            target,
            keyframe["pre"]?.let { parseVector(it, animationName, boneName, targetName) } ?: ZERO_VECTOR,
        )
        val post = convertVector(
            target,
            keyframe["post"]?.let { parseVector(it, animationName, boneName, targetName) } ?: ZERO_VECTOR,
        )
        val interpolation = when (keyframe["lerp_mode"]?.asString?.lowercase() ?: "linear") {
            "linear" -> AnimationChannel.Interpolations.LINEAR
            "catmullrom" -> AnimationChannel.Interpolations.CATMULLROM
            else -> throw IllegalArgumentException(
                "Unknown lerp_mode '${keyframe["lerp_mode"].asString}' in animation '$animationName', bone '$boneName', target '$targetName'",
            )
        }
        return ParsedKeyframe(timestamp, pre, post, interpolation)
    }

    private fun parseVector(
        element: JsonElement,
        animationName: String,
        boneName: String,
        targetName: String,
    ): List<Float> {
        if (element.isJsonPrimitive && element.asJsonPrimitive.isNumber) {
            return List(3) { element.asFloat }
        }
        if (!element.isJsonArray) {
            throw IllegalArgumentException(
                "Expected a numeric scalar or vector in animation '$animationName', bone '$boneName', target '$targetName'",
            )
        }

        val values = element.asJsonArray.map { value ->
            if (!value.isJsonPrimitive || !value.asJsonPrimitive.isNumber) {
                throw IllegalArgumentException(
                    "Animation vectors must contain only numbers in animation '$animationName', bone '$boneName', target '$targetName'",
                )
            }
            value.asFloat
        }
        return when (values.size) {
            1 -> List(3) { values.single() }
            3 -> values
            else -> throw IllegalArgumentException(
                "Animation vectors must contain one or three numbers in animation '$animationName', bone '$boneName', target '$targetName'",
            )
        }
    }

    private fun convertVector(target: AnimationChannel.Target, vector: List<Float>): Vector3fc = when (target) {
        AnimationChannel.Targets.ROTATION -> KeyframeAnimations.degreeVec(vector[0], vector[1], vector[2])
        AnimationChannel.Targets.POSITION -> KeyframeAnimations.posVec(vector[0], vector[1], vector[2])
        AnimationChannel.Targets.SCALE -> KeyframeAnimations.scaleVec(
            vector[0].toDouble(),
            vector[1].toDouble(),
            vector[2].toDouble(),
        )

        else -> throw IllegalArgumentException("Unsupported animation target")
    }

    private data class ParsedKeyframe(
        val timestamp: Float,
        val preTarget: Vector3fc,
        val postTarget: Vector3fc,
        val outgoingInterpolation: AnimationChannel.Interpolation,
    )

    private val ZERO_VECTOR = listOf(0f, 0f, 0f)
}
