package club.redux.sunset.lavafishing.util

import club.redux.sunset.lavafishing.api.mixin.IMixinProxy

object UtilMixin {
    @JvmStatic
    fun <T1, T2 : IMixinProxy<T1>> castToProxy(obj: T1, clazz: Class<T2>): T2 {
        if (!clazz.isInstance(obj)) {
            throw IllegalArgumentException("Object is not an instance of $clazz")
        }

        return clazz.cast(obj)
    }
}

fun <T1, T2 : IMixinProxy<T1>> T1.castToProxy(clazz: Class<T2>): T2 {
    return UtilMixin.castToProxy(this, clazz)
}