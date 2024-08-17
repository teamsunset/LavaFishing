package club.redux.sunset.lavafishing.util

import club.redux.sunset.lavafishing.util.UtilJson.SomeToJson
import com.google.gson.JsonArray
import com.google.gson.JsonElement

object UtilJson {
    interface SomeToJson {
        fun toJson(): JsonElement
    }

    @JvmStatic
    fun toJsonArray(elements: Iterable<SomeToJson>): JsonArray {
        return JsonArray().apply { elements.forEach { add(it.toJson()) } }
    }
}

fun Iterable<SomeToJson>.toJsonArray(): JsonArray = UtilJson.toJsonArray(this)
