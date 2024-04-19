package club.redux.sunset.lavafishing.util

import club.redux.sunset.lavafishing.util.RegistryCollection.ItemCollection
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.StringRepresentable
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient
import java.util.*

enum class LavaArmorMaterials(
    private val materialName: String,
    private val durabilityMultiplier: Int,
    private val defenseForType: EnumMap<ArmorItem.Type, Int>,
    private val durabilityForType: EnumMap<ArmorItem.Type, Int>,
    private val enchantmentValue: Int,
    private val sound: SoundEvent,
    private val toughness: Float,
    private val knockbackResistance: Float,
    private val repairIngredient: Ingredient,
) : StringRepresentable, ArmorMaterial {

    PROMETHIUM(
        "promethium",
        38,
        EnumMap<ArmorItem.Type, Int>(ArmorItem.Type::class.java).apply {
            put(ArmorItem.Type.BOOTS, 4)
            put(ArmorItem.Type.LEGGINGS, 7)
            put(ArmorItem.Type.CHESTPLATE, 9)
            put(ArmorItem.Type.HELMET, 4)
        },
        EnumMap<ArmorItem.Type, Int>(ArmorItem.Type::class.java).apply {
            put(ArmorItem.Type.BOOTS, 4)
            put(ArmorItem.Type.LEGGINGS, 7)
            put(ArmorItem.Type.CHESTPLATE, 9)
            put(ArmorItem.Type.HELMET, 4)
        },
        14,
        SoundEvents.ARMOR_EQUIP_DIAMOND,
        2.5f,
        0.1f,
        Ingredient.of(ItemCollection.PROMETHIUM_INGOT.get())
    );

    override fun getDurabilityForType(type: ArmorItem.Type): Int = durabilityForType[type]!! * this.durabilityMultiplier
    override fun getDefenseForType(type: ArmorItem.Type): Int = defenseForType[type]!!
    override fun getEnchantmentValue(): Int = this.enchantmentValue
    override fun getEquipSound(): SoundEvent = this.sound
    override fun getRepairIngredient(): Ingredient = repairIngredient
    override fun getName(): String = this.materialName
    override fun getToughness(): Float = this.toughness
    override fun getKnockbackResistance(): Float = this.knockbackResistance
    override fun getSerializedName(): String = this.materialName
}