package club.redux.sunset.lavafishing.util

import net.minecraft.core.Holder
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level
import java.util.*

fun Level.isServerSide(): Boolean = !this.isClientSide

fun <T> Level.getHolder(resourceKey: ResourceKey<T>): Optional<Holder.Reference<T>> =
    this.registryAccess().holder(resourceKey)