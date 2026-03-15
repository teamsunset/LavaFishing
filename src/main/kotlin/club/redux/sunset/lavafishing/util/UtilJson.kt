package club.redux.sunset.lavafishing.util

import com.google.gson.JsonArray
import com.google.gson.JsonElement

object UtilJson {
    interface SomeToJson {
        fun toJson(): JsonElement
    }
}

fun Iterable<UtilJson.SomeToJson>.toJsonArray(): JsonArray = JsonArray().also { array ->
    this.forEach { someToJson -> array.add(someToJson.toJson()) }
}

