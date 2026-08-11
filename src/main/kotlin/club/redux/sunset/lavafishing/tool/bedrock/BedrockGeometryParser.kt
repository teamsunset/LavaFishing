package club.redux.sunset.lavafishing.tool.bedrock

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.model.geom.builders.PartDefinition
import kotlin.math.PI

internal object BedrockGeometryParser {
    fun parse(root: JsonObject): LayerDefinition {
        val geometries = root.getAsJsonArray("minecraft:geometry")
            ?: throw IllegalArgumentException("Missing minecraft:geometry array")
        if (geometries.size() != 1) {
            throw IllegalArgumentException("Expected exactly one geometry, found ${geometries.size()}")
        }

        val geometry = geometries[0].asJsonObject
        val description = geometry.getAsJsonObject("description")
            ?: throw IllegalArgumentException("Missing geometry description")
        val mesh = MeshDefinition()
        val rootPart = mesh.root
        val bones = geometry.getAsJsonArray("bones")?.map(JsonElement::getAsJsonObject)
            ?: throw IllegalArgumentException("Missing geometry bones")
        val bonesByName = linkedMapOf<String, JsonObject>()
        bones.forEach { bone ->
            val name = bone["name"]?.asString ?: throw IllegalArgumentException("Bone is missing a name")
            if (bonesByName.put(name, bone) != null) {
                throw IllegalArgumentException("Duplicate bone '$name'")
            }
        }

        val builtParts = mutableMapOf<String, PartDefinition>()
        val visiting = mutableSetOf<String>()

        fun buildBone(name: String): PartDefinition {
            builtParts[name]?.let { return it }
            val bone = bonesByName[name] ?: throw IllegalArgumentException("Unknown bone '$name'")
            if (!visiting.add(name)) {
                throw IllegalArgumentException("Bone hierarchy contains a cycle at '$name'")
            }

            val parentName = bone["parent"]?.asString
            val parentPart = if (parentName == null) {
                rootPart
            } else {
                if (parentName !in bonesByName) {
                    throw IllegalArgumentException("Parent bone '$parentName' not found for child '$name'")
                }
                buildBone(parentName)
            }
            val pivot = vector(bone["pivot"], "bone '$name' pivot", ZERO_VECTOR)
            val rotation = vector(bone["rotation"], "bone '$name' rotation", ZERO_VECTOR)
            val parentPivot = parentName?.let { parent ->
                vector(bonesByName.getValue(parent)["pivot"], "bone '$parent' pivot", ZERO_VECTOR)
            }

            val pose = if (parentPivot == null) {
                PartPose.offsetAndRotation(
                    pivot[0],
                    24f - pivot[1],
                    pivot[2],
                    radians(rotation[0]),
                    radians(rotation[1]),
                    radians(rotation[2]),
                )
            } else {
                PartPose.offsetAndRotation(
                    pivot[0] - parentPivot[0],
                    parentPivot[1] - pivot[1],
                    pivot[2] - parentPivot[2],
                    radians(rotation[0]),
                    radians(rotation[1]),
                    radians(rotation[2]),
                )
            }

            val cubes = bone["cubes"]?.asJsonArray?.map(JsonElement::getAsJsonObject).orEmpty()
            val boneCubes = CubeListBuilder.create()
            cubes.filter { !it.has("rotation") && !it.has("pivot") }
                .forEach { cube -> addCube(boneCubes, cube, pivot, "bone '$name'") }
            val part = parentPart.addOrReplaceChild(name, boneCubes, pose)

            cubes.filter { it.has("rotation") || it.has("pivot") }
                .forEachIndexed { index, cube -> addTransformedCube(part, cube, pivot, name, index) }

            visiting.remove(name)
            builtParts[name] = part
            return part
        }

        bonesByName.keys.forEach(::buildBone)
        return LayerDefinition.create(
            mesh,
            description["texture_width"]?.asInt ?: 64,
            description["texture_height"]?.asInt ?: 64,
        )
    }

    private fun addCube(
        builder: CubeListBuilder,
        cube: JsonObject,
        pivot: List<Float>,
        context: String,
    ) {
        val origin = vector(cube["origin"], "$context cube origin")
        val size = vector(cube["size"], "$context cube size")
        val uv = uv(cube["uv"], context)
        builder.texOffs(uv[0], uv[1]).addBox(
            origin[0] - pivot[0],
            pivot[1] - origin[1] - size[1],
            origin[2] - pivot[2],
            size[0],
            size[1],
            size[2],
            CubeDeformation(cube["inflate"]?.asFloat ?: 0f),
        )
    }

    private fun addTransformedCube(
        parent: PartDefinition,
        cube: JsonObject,
        bonePivot: List<Float>,
        boneName: String,
        index: Int,
    ) {
        val cubePivot = vector(cube["pivot"], "bone '$boneName' cube pivot", bonePivot)
        val cubeRotation = vector(cube["rotation"], "bone '$boneName' cube rotation", ZERO_VECTOR)
        val builder = CubeListBuilder.create()
        addCube(builder, cube, cubePivot, "bone '$boneName'")
        parent.addOrReplaceChild(
            "${boneName}_cube_$index",
            builder,
            PartPose.offsetAndRotation(
                cubePivot[0] - bonePivot[0],
                bonePivot[1] - cubePivot[1],
                cubePivot[2] - bonePivot[2],
                radians(cubeRotation[0]),
                radians(cubeRotation[1]),
                radians(cubeRotation[2]),
            ),
        )
    }

    private fun vector(element: JsonElement?, context: String, default: List<Float>? = null): List<Float> {
        if (element == null) return default ?: throw IllegalArgumentException("Missing $context")
        if (!element.isJsonArray || element.asJsonArray.size() != 3) {
            throw IllegalArgumentException("$context must contain exactly three numbers")
        }
        return element.asJsonArray.map { value ->
            if (!value.isJsonPrimitive || !value.asJsonPrimitive.isNumber) {
                throw IllegalArgumentException("$context must contain only numbers")
            }
            value.asFloat
        }
    }

    private fun uv(element: JsonElement?, context: String): List<Int> {
        if (element == null || !element.isJsonArray || element.asJsonArray.size() != 2) {
            throw IllegalArgumentException("$context cube UV must be a two-number array")
        }
        return element.asJsonArray.map { value ->
            if (!value.isJsonPrimitive || !value.asJsonPrimitive.isNumber) {
                throw IllegalArgumentException("$context cube UV must contain only numbers")
            }
            value.asInt
        }
    }

    private fun radians(degrees: Float): Float = (degrees * PI / 180.0).toFloat()

    private val ZERO_VECTOR = listOf(0f, 0f, 0f)
}
