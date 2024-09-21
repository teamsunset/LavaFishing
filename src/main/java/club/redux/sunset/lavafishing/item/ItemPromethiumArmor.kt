package club.redux.sunset.lavafishing.item

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModMobEffects
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.damagesource.DamageTypes
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.material.FogType
import net.neoforged.neoforge.client.event.ViewportEvent
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
import net.neoforged.neoforge.event.tick.EntityTickEvent

class ItemPromethiumArmor(
    armorMaterial: Holder<ArmorMaterial>,
    type: Type,
) : ArmorItem(armorMaterial, type, Properties().fireResistant()) {

    private var texture: String? = null

    fun setArmorTexture(filename: String): Item = this.apply { this.texture = filename }

    override fun isEnchantable(stack: ItemStack): Boolean = true

    // 这玩意居然是实时的
    override fun getArmorTexture(
        stack: ItemStack,
        entity: Entity,
        slot: EquipmentSlot,
        layer: ArmorMaterial.Layer,
        innerModel: Boolean,
    ): ResourceLocation {
        return LavaFishing.resourceLocation("textures/armor/" + this.texture + ".png")
    }

    override fun getXpRepairRatio(stack: ItemStack): Float = super.getXpRepairRatio(stack)


    companion object {
        fun onEntityTickPost(event: EntityTickEvent.Post) {
            val entity = event.entity
            if (entity !is LivingEntity) return

            val level = entity.level()
            for (itemStack in entity.armorSlots) {
                val item = itemStack.item
                if (item is ItemPromethiumArmor) {
                    val applyEffect = { effect: Holder<MobEffect> ->
                        entity.addEffect(MobEffectInstance(effect, 20, 0, false, false, true))
                    }
                    val futureBlockPos =
                        BlockPos.containing(event.entity.position().add(event.entity.deltaMovement.scale(1.5)))
                    if (event.entity.isOnFire ||
                        level.getBlockState(event.entity.onPos).`is`(Blocks.LAVA) ||
                        level.getBlockState(futureBlockPos).`is`(Blocks.LAVA)
                    ) {
                        if (item.type == Type.LEGGINGS) {
                            applyEffect(MobEffects.MOVEMENT_SPEED)
                        } else if (item.type == Type.BOOTS) {
                            applyEffect(ModMobEffects.LAVA_WALKER)
                        }
                    }
                }
            }
        }

        fun onFogRender(event: ViewportEvent.RenderFog) {
            val player = Minecraft.getInstance().player ?: return
            if (
                event.type == FogType.LAVA &&
                player.getItemBySlot(EquipmentSlot.HEAD).`is`(ModItems.PROMETHIUM_HELMET.get()) &&
                !player.isSpectator
            ) {
                event.nearPlaneDistance = 0.0f
                event.farPlaneDistance = 20.0f
                event.isCanceled = true // 神秘的判断，event post默认情况为事件取消返回true
            }
        }

        fun onLivingDamagePre(event: LivingDamageEvent.Pre) {
            val damage = event.newDamage
            if (
                event.source.`is`(DamageTypes.LAVA) ||
                event.source.`is`(DamageTypes.IN_FIRE) ||
                event.source.`is`(DamageTypes.ON_FIRE)
            ) {
                var promethiumArmorCount = 0
                for (itemStack in event.entity.armorSlots) {
                    val item = itemStack.item
                    if (item is ItemPromethiumArmor) {
                        event.newDamage -= 1f / 4 * damage
                        promethiumArmorCount++
                    }
                }
            }
        }

        fun onLivingIncomingDamage(event: LivingIncomingDamageEvent) {
            val armorItems = event.entity.armorSlots.map { it.item }.filterIsInstance<ItemPromethiumArmor>()

            if (
                event.source.`is`(DamageTypes.LAVA) ||
                event.source.`is`(DamageTypes.IN_FIRE) ||
                event.source.`is`(DamageTypes.ON_FIRE)
            ) {
                if (armorItems.any { it.type == Type.CHESTPLATE }) {
                    event.entity.heal(0.04f)
                }
                if (armorItems.count() == 4) {
                    event.isCanceled = true
                }
            }

            if (event.source.`is`(DamageTypes.HOT_FLOOR) && armorItems.any { it.type == Type.BOOTS }) {
                if (armorItems.any { it.type == Type.CHESTPLATE }) {
                    event.entity.heal(0.04f)
                }
                event.isCanceled = true
            }
        }
    }
}