package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.tool.registry.Registrar
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient
import java.util.*

object ModArmorMaterials : Registrar<ArmorMaterial>(BuiltInRegistries.ARMOR_MATERIAL, BuiltConstants.MOD_ID) {

    val PROMETHIUM by this.register {
        ArmorMaterial(
            EnumMap<ArmorItem.Type, Int>(ArmorItem.Type::class.java).apply
            {
                put(ArmorItem.Type.HELMET, 4)
                put(ArmorItem.Type.CHESTPLATE, 9)
                put(ArmorItem.Type.LEGGINGS, 7)
                put(ArmorItem.Type.BOOTS, 4)
            },
            18,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            { Ingredient.of(ModItems.PROMETHIUM_INGOT.get()) },
            listOf(ArmorMaterial.Layer(LavaFishing.resourceLocation("promethium"))),
            3.5f,
            0.2f
        )
    }
}