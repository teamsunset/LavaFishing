package club.redux.sunset.lavafishing.api.dagagenerator

import club.redux.sunset.lavafishing.util.UtilJson
import club.redux.sunset.lavafishing.util.toJsonArray
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.biome.Biome
import java.util.concurrent.CompletableFuture

open class DataProviderBiomeModifier(
    output: PackOutput,
    private val modId: String,
) : DataProvider {
    private val pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "forge/biome_modifier")
    private val modifiers = mutableMapOf<String, Modifier>()

    fun addModifier(name: String, modifier: Modifier) {
        modifiers[name] = modifier
    }

    override fun run(pOutput: CachedOutput): CompletableFuture<*> {
        this.addModifiers()
        return CompletableFuture.allOf(*this.modifiers.map { (name: String, modifier: Modifier) ->
            return DataProvider.saveStable(pOutput, modifier.toJson(), pathProvider.json(ResourceLocation(modId, name)))
        }.toTypedArray())
    }

    override fun getName(): String = "Biome Modifiers"

    protected open fun addModifiers() {}


    abstract class Modifier(
        val type: String,
    ) : UtilJson.SomeToJson {
        override fun toJson(): JsonElement {
            return JsonObject().apply {
                addProperty("type", type)
            }
        }
    }

    data class ModifierAddSpawns(
        val biomes: JsonElement,
        val spawners: List<Spawner>,
    ) : Modifier("forge:add_spawns") {

        constructor(
            biomeTag: TagKey<Biome>,
            spawners: List<Spawner>,
        ) : this(JsonPrimitive("#${biomeTag.location}"), spawners)

        constructor(
            biomes: List<ResourceKey<Biome>>,
            spawners: List<Spawner>,
        ) : this(JsonArray().apply { biomes.forEach { add(JsonPrimitive(it.location().toString())) } }, spawners)

        override fun toJson(): JsonElement {
            return (super.toJson() as JsonObject).apply {
                add("biomes", biomes)
                add("spawners", spawners.toJsonArray())
            }
        }

        data class Spawner(
            val type: String,
            val weight: Int,
            val minCount: Int,
            val maxCount: Int,
        ) : UtilJson.SomeToJson {

            constructor(
                entityType: EntityType<*>,
                weight: Int,
                minCount: Int,
                maxCount: Int,
            ) : this(EntityType.getKey(entityType).toString(), weight, minCount, maxCount)

            override fun toJson(): JsonElement {
                return JsonObject().apply {
                    addProperty("type", type)
                    addProperty("weight", weight)
                    addProperty("minCount", minCount)
                    addProperty("maxCount", maxCount)
                }
            }
        }
    }
}
