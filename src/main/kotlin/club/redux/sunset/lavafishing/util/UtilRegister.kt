package club.redux.sunset.lavafishing.util

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.RegistryObject

object UtilRegister {
    fun <T> create(reg: IForgeRegistry<T>, modId: String): DeferredRegister<T> {
        return DeferredRegister.create(reg, modId)
    }

    fun <T> create(key: ResourceKey<Registry<T>>, modId: String): DeferredRegister<T> {
        return DeferredRegister.create(key, modId)
    }
}

fun <T, R : T> DeferredRegister<T>.registerKt(name: String, supplier: () -> R): RegistryObject<R> {
    return this.register(name, supplier)
}