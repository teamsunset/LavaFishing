package club.redux.sunset.lavafishing.item

import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModMobEffects
import com.mojang.math.Axis
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.rendertype.RenderTypes
import net.minecraft.client.resources.model.ModelBakery
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.tags.FluidTags
import net.minecraft.world.damagesource.DamageTypes
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.equipment.ArmorMaterial
import net.minecraft.world.item.equipment.ArmorType
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.material.FogType
import net.neoforged.neoforge.client.event.ViewportEvent
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
import net.neoforged.neoforge.event.tick.EntityTickEvent

class ItemPromethiumArmor(
    armorMaterial: ArmorMaterial,
    val armorType: ArmorType,
    properties: Properties,
) : Item(properties.humanoidArmor(armorMaterial, armorType).fireResistant().stacksTo(1)) {


    companion object {
        private val ARMOR_SLOTS = listOf(
            EquipmentSlot.FEET,
            EquipmentSlot.LEGS,
            EquipmentSlot.CHEST,
            EquipmentSlot.HEAD,
        )

        private fun LivingEntity.promethiumArmorItems(): List<ItemPromethiumArmor> =
            ARMOR_SLOTS.map { getItemBySlot(it).item }.filterIsInstance<ItemPromethiumArmor>()

        fun onEntityTickPost(event: EntityTickEvent.Post) {
            val entity = event.entity
            if (entity !is LivingEntity) return

            val level = entity.level()
            val futureBlockPos = BlockPos.containing(entity.position().add(entity.deltaMovement.scale(1.5)))
            val applyEffect = { effect: Holder<MobEffect> ->
                entity.addEffect(MobEffectInstance(effect, 20, 0, false, false, true))
            }

            val isOnFire = entity.isOnFire
            val isOnLava = listOf(entity.onPos, futureBlockPos).any { level.getBlockState(it).`is`(Blocks.LAVA) }
            val isOnHotFloor = level.getBlockState(entity.onPos).`is`(Blocks.MAGMA_BLOCK)

            val types = entity.promethiumArmorItems().map { it.armorType }

            if (types.contains(ArmorType.LEGGINGS) && (isOnLava || isOnFire || isOnHotFloor)) applyEffect(MobEffects.SPEED)
            if (types.contains(ArmorType.BOOTS) && isOnLava) applyEffect(ModMobEffects.LAVA_WALKER)
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
            }
        }

        fun onRenderBlockScreen(event: RenderBlockScreenEffectEvent) {
            if (event.overlayType != RenderBlockScreenEffectEvent.OverlayType.FIRE) return
            if (!event.player.getItemBySlot(EquipmentSlot.HEAD).`is`(ModItems.PROMETHIUM_HELMET.get())) return

            val poseStack = event.poseStack
            val sprite = event.sprites.get(ModelBakery.FIRE_1)
            val builder = event.bufferSource.getBuffer(RenderTypes.fireScreenEffect(sprite.atlasLocation()))
            val alpha = if (event.player.isEyeInFluid(FluidTags.LAVA)) 0.0f else 0.3f

            repeat(2) { index ->
                poseStack.pushPose()
                poseStack.translate((-(index * 2 - 1)).toFloat() * 0.24f, -0.3f, 0.0f)
                poseStack.mulPose(Axis.YP.rotationDegrees((index * 2 - 1).toFloat() * 10.0f))

                val pose = poseStack.last().pose()
                builder.addVertex(pose, -0.5f, -0.5f, -0.5f)
                    .setUv(sprite.u1, sprite.v1)
                    .setColor(1.0f, 1.0f, 1.0f, alpha)
                builder.addVertex(pose, 0.5f, -0.5f, -0.5f)
                    .setUv(sprite.u0, sprite.v1)
                    .setColor(1.0f, 1.0f, 1.0f, alpha)
                builder.addVertex(pose, 0.5f, 0.5f, -0.5f)
                    .setUv(sprite.u0, sprite.v0)
                    .setColor(1.0f, 1.0f, 1.0f, alpha)
                builder.addVertex(pose, -0.5f, 0.5f, -0.5f)
                    .setUv(sprite.u1, sprite.v0)
                    .setColor(1.0f, 1.0f, 1.0f, alpha)
                poseStack.popPose()
            }

            event.setCanceled(true)
        }

        /**
         * # 每一件护甲减少 1/4 的火焰伤害
         */
        fun onLivingDamagePre(event: LivingDamageEvent.Pre) {
            val damage = event.newDamage
            if (listOf(DamageTypes.LAVA, DamageTypes.IN_FIRE, DamageTypes.ON_FIRE).any { event.source.`is`(it) }) {
                val count = event.entity.promethiumArmorItems().size
                event.newDamage -= count / 4f * damage
            }
        }

        /**
         * # 有胸甲时回血，全套抵消伤害
         */
        fun onLivingIncomingDamage(event: LivingIncomingDamageEvent) {
            val armorItems = event.entity.promethiumArmorItems()

            if (listOf(DamageTypes.LAVA, DamageTypes.IN_FIRE, DamageTypes.ON_FIRE).any { event.source.`is`(it) }) {
                if (armorItems.any { it.armorType == ArmorType.CHESTPLATE }) event.entity.heal(0.04f)
                if (armorItems.size == ARMOR_SLOTS.size) event.setCanceled(true)
            }

            if (event.source.`is`(DamageTypes.HOT_FLOOR) && armorItems.any { it.armorType == ArmorType.BOOTS }) {
                event.setCanceled(true)
            }
        }
    }
}
