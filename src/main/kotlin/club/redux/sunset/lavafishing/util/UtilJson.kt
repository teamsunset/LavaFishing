package club.redux.sunset.lavafishing.util

import com.google.gson.JsonArray
import com.google.gson.JsonElement

object UtilJson {
    interface SomeToJson {
        fun toJson(): JsonElement
    }

    fun Iterable<SomeToJson>.toJsonArray(): JsonArray = JsonArray().also { this.forEach { sj -> it.add(sj.toJson()) } }
}

