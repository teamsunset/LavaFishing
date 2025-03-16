package club.redux.sunset.lavafishing.util

import club.redux.sunset.lavafishing.mixinproxy.IMixinProxy
import kotlin.reflect.KClass
import kotlin.reflect.cast

object UtilMixin {
    fun <T1, T2 : IMixinProxy<T1>> T1.castToProxy(clazz: KClass<T2>): T2 {
        if (!clazz.isInstance(this)) {
            throw IllegalArgumentException("Object is not an instance of $clazz")
        }

        return clazz.cast(this)
    }
}
