package club.redux.sunset.lavafishing.item

import club.asynclab.web.BuildConstants
import club.redux.sunset.lavafishing.registry.RegistryMobEffect
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.event.TickEvent.PlayerTickEvent

class PromethiumArmor(
    armorMaterial: ArmorMaterial,
    type: Type,
) : ArmorItem(armorMaterial, type, Properties().fireResistant()) {

    private var texture: String? = null

    fun setArmorTexture(string: String?): Item {
        this.texture = string
        return this
    }

    override fun getArmorTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlot, layer: String): String? {
        return BuildConstants.MOD_ID + ":textures/armor/" + this.texture + ".png"
    }

    companion object {
        fun onPlayerTick(event: PlayerTickEvent) {
            val player = event.player
            val level = player.level()
            for (itemStack in player.armorSlots) {
                val item = itemStack.item
                if (item is PromethiumArmor) {
                    if (player.isOnFire) {
                        if (item.type == Type.CHESTPLATE) {
                            player.heal(0.1f)
                        }
                    }
                    if (player.isInLava || level.getBlockState(player.onPos).`is`(Blocks.LAVA)) {
                        if (item.type == Type.LEGGINGS) {
                            player.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 0, false, false, false))
                        } else if (item.type == Type.BOOTS) {
                            val lavaWalker: MobEffect = RegistryMobEffect.LAVA_WALKER.get()
                            player.addEffect(
                                MobEffectInstance(
                                    RegistryMobEffect.LAVA_WALKER.get(),
                                    20,
                                    0,
                                    false,
                                    false,
                                    false
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
