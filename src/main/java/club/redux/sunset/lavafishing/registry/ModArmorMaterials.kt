package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient
import java.util.*

object ModArmorMaterials {
    @JvmField val REGISTER = UtilRegister.create(BuiltInRegistries.ARMOR_MATERIAL, BuildConstants.MOD_ID)

    @JvmField val PROMETHIUM: Holder<ArmorMaterial> = REGISTER.registerKt("promethium", {
        ArmorMaterial(
            EnumMap<ArmorItem.Type, Int>(ArmorItem.Type::class.java).apply
            {
                put(ArmorItem.Type.HELMET, 4)
                put(ArmorItem.Type.CHESTPLATE, 9)
                put(ArmorItem.Type.LEGGINGS, 7)
                put(ArmorItem.Type.BOOTS, 4)
            },
            14,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            { Ingredient.of(ModItems.PROMETHIUM_INGOT.get()) },
            listOf(ArmorMaterial.Layer(LavaFishing.resourceLocation("promethium"))),
            2.5f,
            0.1f
        )
    })
}