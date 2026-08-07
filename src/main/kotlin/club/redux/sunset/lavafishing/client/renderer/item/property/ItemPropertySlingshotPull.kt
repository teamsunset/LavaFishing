package club.redux.sunset.lavafishing.client.renderer.item.property

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import com.mojang.serialization.MapCodec
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
import net.minecraft.client.renderer.item.properties.numeric.UseDuration
import net.minecraft.world.entity.ItemOwner
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent

class ItemPropertySlingshotPull : RangeSelectItemModelProperty {
    override fun get(itemStack: ItemStack, level: ClientLevel?, owner: ItemOwner?, seed: Int): Float {
        val entity = owner?.asLivingEntity() ?: return 0.0f
        val slingshot = itemStack.item as? ItemSlingshot ?: return 0.0f
        if (entity.useItem !== itemStack) return 0.0f

        return UseDuration.useDuration(itemStack, entity).toFloat() * slingshot.getChargeMultiplier(itemStack)
    }

    override fun type() = MAP_CODEC

    companion object {
        val MAP_CODEC: MapCodec<ItemPropertySlingshotPull> = MapCodec.unit(ItemPropertySlingshotPull())

        fun onRegisterRangeSelectItemModelProperties(event: RegisterRangeSelectItemModelPropertyEvent) {
            event.register(LavaFishing.identifier("slingshot_pull"), MAP_CODEC)
        }
    }
}
