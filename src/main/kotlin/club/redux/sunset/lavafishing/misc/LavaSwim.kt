package club.redux.sunset.lavafishing.misc

import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player

object LavaSwim {
    @JvmStatic
    fun hasLavaSwimArmor(entity: LivingEntity) =
        entity.getItemBySlot(EquipmentSlot.LEGS).`is`(ModItems.PROMETHIUM_LEGGINGS.get())

    @JvmStatic
    fun isDirectionalSwimming(entity: LivingEntity) =
        entity.isSwimming && entity.isInLava && !entity.isInWater && hasLavaSwimArmor(entity)// Directional swimming requires an already-established swim state; water travel does not.


    @JvmStatic
    fun canUseLavaWaterTravel(entity: LivingEntity) =
        (entity !is Player || (!entity.isSpectator && !entity.isPassenger)) &&
                entity.isInLava &&
                !entity.isInWater &&
                hasLavaSwimArmor(entity)
}
