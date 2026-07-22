package club.redux.sunset.lavafishing.misc

import club.redux.sunset.lavafishing.LavaFishing
import net.minecraft.resources.ResourceKey
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.equipment.ArmorMaterial
import net.minecraft.world.item.equipment.ArmorType
import net.minecraft.world.item.equipment.EquipmentAsset
import net.minecraft.world.item.equipment.EquipmentAssets

object ModArmorMaterials {
    private val PROMETHIUM_ASSET: ResourceKey<EquipmentAsset> =
        ResourceKey.create(EquipmentAssets.ROOT_ID, LavaFishing.identifier("promethium"))

    val PROMETHIUM = ArmorMaterial(
        40,
        mapOf(
            ArmorType.HELMET to 4,
            ArmorType.CHESTPLATE to 9,
            ArmorType.LEGGINGS to 7,
            ArmorType.BOOTS to 4,
        ),
        18,
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        3.5f,
        0.2f,
        ModTags.OreDirectory.PROMETHIUM_INGOT,
        PROMETHIUM_ASSET,
    )
}