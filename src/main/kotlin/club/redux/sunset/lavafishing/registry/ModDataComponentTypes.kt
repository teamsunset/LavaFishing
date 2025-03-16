package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.tool.registry.Registrar
import com.mojang.serialization.Codec
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.codec.ByteBufCodecs


object ModDataComponentTypes : Registrar<DataComponentType<*>>(
    BuiltInRegistries.DATA_COMPONENT_TYPE,
    BuiltConstants.MOD_ID
) {
    val BULLET_DIVISION_TIMES by this.registerComponentType {
        it.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT)
    }

    private fun <T> registerComponentType(
        builder: (DataComponentType.Builder<T>) -> DataComponentType.Builder<T>,
    ) = this.register { builder(DataComponentType.builder()).build() }
}