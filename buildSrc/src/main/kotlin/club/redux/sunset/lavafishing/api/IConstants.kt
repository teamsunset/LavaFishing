package club.redux.sunset.lavafishing.api

import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility

interface IConstants {
    fun getClass() = this::class

    fun toMap() = this.getClass().members
        .filter { it is KProperty<*> && it.visibility == KVisibility.PUBLIC }
        .associate { it.name.lowercase() to it.call().toString() }
}
