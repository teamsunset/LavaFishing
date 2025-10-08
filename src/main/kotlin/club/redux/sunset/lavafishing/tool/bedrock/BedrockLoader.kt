package club.redux.sunset.lavafishing.tool.bedrock

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.client.animation.AnimationChannel
import net.minecraft.client.animation.AnimationDefinition
import net.minecraft.client.animation.Keyframe
import net.minecraft.client.animation.KeyframeAnimations
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.*
import java.io.InputStream
import kotlin.math.PI

object BedrockLoader {
    private val gson = Gson()

    /**
     * 辅助函数，将角度转换为弧度用于模型旋转。
     * @param degrees 角度值。
     * @return 弧度值，返回类型为 Float。
     */
    private fun toRadians(degrees: Float): Float {
        return (degrees * PI / 180.0).toFloat()
    }

    fun loadGeometry(inputStream: InputStream): LayerDefinition {
        val geo = inputStream.reader().use {
            gson.fromJson(it, JsonObject::class.java).getAsJsonArray("minecraft:geometry")[0].asJsonObject
        }

        val desc = geo["description"].asJsonObject
        val texW = desc["texture_width"]?.asInt ?: 64
        val texH = desc["texture_height"]?.asInt ?: 64

        val mesh = MeshDefinition()
        val rootPart = mesh.root
        val partMap = mutableMapOf<String, PartDefinition>()

        val bonesArray = geo.getAsJsonArray("bones").map { it.asJsonObject }
        val boneJsonMap = bonesArray.associateBy { it["name"].asString }

        bonesArray.forEach { boneJson ->
            val name = boneJson["name"].asString
            val parentName = boneJson["parent"]?.asString

            val targetParent = partMap[parentName] ?: rootPart

            val pivot = boneJson["pivot"]?.asJsonArray?.map { it.asFloat } ?: listOf(0f, 0f, 0f)
            val rotationDeg = boneJson["rotation"]?.asJsonArray?.map { it.asFloat } ?: listOf(0f, 0f, 0f)

            val offsetX: Float
            val offsetY: Float
            val offsetZ: Float

            if (parentName == null) {
                // 对于根骨骼，姿势相对于模型原点，
                // Y轴反转并偏移24（适用于类玩家实体的标准设置）。
                offsetX = pivot[0]
                offsetY = 24f - pivot[1]
                offsetZ = pivot[2]
            } else {
                // 对于子骨骼，姿势相对于父骨骼的枢轴点。
                val parentJson = boneJsonMap[parentName]
                    ?: throw IllegalStateException("Parent bone '$parentName' not found for child '$name'. Ensure JSON is sorted.")

                val parentPivot = parentJson["pivot"]?.asJsonArray?.map { it.asFloat } ?: listOf(0f, 0f, 0f)

                offsetX = pivot[0] - parentPivot[0]
                offsetY = parentPivot[1] - pivot[1] // Y轴反转通过减法顺序处理
                offsetZ = pivot[2] - parentPivot[2]
            }

            val pose = PartPose.offsetAndRotation(
                offsetX,
                offsetY,
                offsetZ,
                toRadians(rotationDeg[0]),
                toRadians(rotationDeg[1]),
                toRadians(rotationDeg[2])
            )

            val boneCubeBuilder = CubeListBuilder.create()
            var cubeIndex = 0

            // 添加没有自身变换的立方体到主骨骼构建器中
            boneJson["cubes"]?.asJsonArray?.map { it.asJsonObject }
                ?.filter { !it.has("rotation") && !it.has("pivot") }
                ?.forEach { cube ->
                    val origin = cube["origin"].asJsonArray.map { it.asFloat }
                    val size = cube["size"].asJsonArray.map { it.asFloat }
                    val uv = cube["uv"].asJsonArray.map { it.asInt }
                    val inflate = cube["inflate"]?.asFloat ?: 0f

                    // 箱体原点相对于骨骼的枢轴点，并且Y轴反转。
                    val boxX = origin[0] - pivot[0]
                    val boxY = pivot[1] - origin[1] - size[1]
                    val boxZ = origin[2] - pivot[2]

                    boneCubeBuilder.texOffs(uv[0], uv[1])
                        .addBox(boxX, boxY, boxZ, size[0], size[1], size[2], CubeDeformation(inflate))
                }

            val currentPart = targetParent.addOrReplaceChild(name, boneCubeBuilder, pose)

            // 添加具有自身变换的立方体作为新的子部件
            boneJson["cubes"]?.asJsonArray?.map { it.asJsonObject }
                ?.filter { it.has("rotation") || it.has("pivot") }
                ?.forEach { cube ->
                    val origin = cube["origin"].asJsonArray.map { it.asFloat }
                    val size = cube["size"].asJsonArray.map { it.asFloat }
                    val uv = cube["uv"].asJsonArray.map { it.asInt }
                    val inflate = cube["inflate"]?.asFloat ?: 0f

                    val cubePivot = cube["pivot"]?.asJsonArray?.map { it.asFloat } ?: pivot
                    val cubeRotationDeg = cube["rotation"]?.asJsonArray?.map { it.asFloat } ?: listOf(0f, 0f, 0f)

                    val cubePartBuilder = CubeListBuilder.create()
                    val boxX = origin[0] - cubePivot[0]
                    val boxY = cubePivot[1] - origin[1] - size[1]
                    val boxZ = origin[2] - cubePivot[2]
                    cubePartBuilder.texOffs(uv[0], uv[1])
                        .addBox(boxX, boxY, boxZ, size[0], size[1], size[2], CubeDeformation(inflate))

                    val cubeOffsetX = cubePivot[0] - pivot[0]
                    val cubeOffsetY = pivot[1] - cubePivot[1]
                    val cubeOffsetZ = cubePivot[2] - pivot[2]

                    val cubePose = PartPose.offsetAndRotation(
                        cubeOffsetX, cubeOffsetY, cubeOffsetZ,
                        toRadians(cubeRotationDeg[0]), toRadians(cubeRotationDeg[1]), toRadians(cubeRotationDeg[2])
                    )

                    val cubePartName = "${name}_cube_${cubeIndex++}"
                    currentPart.addOrReplaceChild(cubePartName, cubePartBuilder, cubePose)
                }

            partMap[name] = currentPart
        }

        return LayerDefinition.create(mesh, texW, texH)
    }


    /** 解析 animation 文件为 AnimationDefinition 对象列表 */
    fun loadAnimations(inputStream: InputStream): Map<String, AnimationDefinition> {
        val animations = inputStream.reader().use {
            gson.fromJson(it, JsonObject::class.java).getAsJsonObject("animations")
        }

        return animations.entrySet().associate { (animName, animElem) ->
            val anim = animElem.asJsonObject
            val builder = AnimationDefinition.Builder
                .withLength(anim["animation_length"]?.asFloat ?: 1f)
                .apply { if (anim["loop"]?.asBoolean == true) looping() }

            anim["bones"]?.asJsonObject?.entrySet()?.forEach { (boneName, boneElem) ->
                boneElem.asJsonObject.entrySet().forEach { (target, timelineElem) ->
                    if (!timelineElem.isJsonObject) return@forEach
                    val targetEnum = when (target.lowercase()) {
                        "rotation" -> AnimationChannel.Targets.ROTATION
                        "position" -> AnimationChannel.Targets.POSITION
                        "scale" -> AnimationChannel.Targets.SCALE
                        else -> AnimationChannel.Targets.POSITION
                    }

                    val keyframes = timelineElem.asJsonObject.entrySet()
                        .sortedBy { it.key.toFloat() }
                        .map { (timeStr, vecElem) ->
                            val time = timeStr.toFloat()
                            val (vec, interpolation) = if (vecElem.isJsonArray) {
                                vecElem.asJsonArray.map(JsonElement::getAsFloat) to AnimationChannel.Interpolations.LINEAR
                            } else {
                                vecElem.asJsonObject["post"].asJsonArray.map(JsonElement::getAsFloat) to AnimationChannel.Interpolations.CATMULLROM
                            }

                            val keyVec = when (targetEnum) {
                                AnimationChannel.Targets.ROTATION -> KeyframeAnimations.degreeVec(
                                    vec[0],
                                    vec[1],
                                    vec[2]
                                )

                                AnimationChannel.Targets.POSITION -> KeyframeAnimations.posVec(vec[0], vec[1], vec[2])
                                AnimationChannel.Targets.SCALE -> KeyframeAnimations.scaleVec(
                                    vec[0].toDouble(),
                                    vec[1].toDouble(), vec[2].toDouble()
                                )

                                else -> KeyframeAnimations.posVec(vec[0], vec[1], vec[2])
                            }

                            Keyframe(time, keyVec, interpolation)
                        }

                    builder.addAnimation(boneName, AnimationChannel(targetEnum, *keyframes.toTypedArray()))
                }
            }

            animName to builder.build()
        }
    }
}
