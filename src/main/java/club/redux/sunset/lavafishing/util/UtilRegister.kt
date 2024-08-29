package club.redux.sunset.lavafishing.util

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object UtilRegister {
    fun <T> create(reg: Registry<T>, modId: String): DeferredRegister<T> {
        return DeferredRegister.create(reg, modId)
    }

    fun <T> create(key: ResourceKey<Registry<T>>, modId: String): DeferredRegister<T> {
        return DeferredRegister.create(key, modId)
    }
}

fun <T, R : T> DeferredRegister<T>.registerKt(name: String, supplier: () -> R): DeferredHolder<T, R> {
    return this.register(name, supplier)
}