package club.redux.sunset.lavafishing.item

import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.registry.ModItems
import club.redux.sunset.lavafishing.registry.ModMobEffects
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.math.Axis
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.resources.model.ModelBakery
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
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
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent
import net.neoforged.neoforge.client.event.ViewportEvent
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
import net.neoforged.neoforge.event.tick.EntityTickEvent
import org.lwjgl.opengl.GL11

class ItemPromethiumArmor(
    armorMaterial: Holder<ArmorMaterial>,
    type: Type,
) : ArmorItem(armorMaterial, type, Properties().fireResistant().stacksTo(1)) {

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

        /**
         * # 戴帽子时减少渲染的火焰效果
         * 抄原版的
         */
        fun onRenderBlockScreen(event: RenderBlockScreenEffectEvent) {
            if (event.overlayType != RenderBlockScreenEffectEvent.OverlayType.FIRE) return
            if (!event.player.getItemBySlot(EquipmentSlot.HEAD).`is`(ModItems.PROMETHIUM_HELMET)) return
            event.isCanceled = true

            val poseStack = event.poseStack

            RenderSystem.setShader { GameRenderer.getPositionTexColorShader() }
            RenderSystem.depthFunc(GL11.GL_ALWAYS)
            RenderSystem.depthMask(false)
            RenderSystem.enableBlend()

            val textureAtlasSprite = ModelBakery.FIRE_1.sprite()
            RenderSystem.setShaderTexture(0, textureAtlasSprite.atlasLocation())

            val uMin = textureAtlasSprite.u0
            val uMax = textureAtlasSprite.u1
            val uMid = (uMin + uMax) / 2.0f
            val vMin = textureAtlasSprite.v0
            val vMax = textureAtlasSprite.v1
            val vMid = (vMin + vMax) / 2.0f
            val shrinkRatio = textureAtlasSprite.uvShrinkRatio()
            val uAdjustedMin = Mth.lerp(shrinkRatio, uMin, uMid)
            val uAdjustedMax = Mth.lerp(shrinkRatio, uMax, uMid)
            val vAdjustedMin = Mth.lerp(shrinkRatio, vMin, vMid)
            val vAdjustedMax = Mth.lerp(shrinkRatio, vMax, vMid)

            for (i in 0..1) {
                poseStack.pushPose()

                poseStack.translate((-(i * 2 - 1)).toFloat() * 0.24f, -0.3f, 0.0f)
                poseStack.mulPose(Axis.YP.rotationDegrees((i * 2 - 1).toFloat() * 10.0f))

                val matrix4f = poseStack.last().pose()
                val bufferBuilder =
                    Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR)

                val alpha = 0.3f

                bufferBuilder.addVertex(matrix4f, -0.5f, -0.5f, -0.5f)
                    .setUv(uAdjustedMax, vAdjustedMax)
                    .setColor(1.0f, 1.0f, 1.0f, alpha)
                bufferBuilder.addVertex(matrix4f, 0.5f, -0.5f, -0.5f)
                    .setUv(uAdjustedMin, vAdjustedMax)
                    .setColor(1.0f, 1.0f, 1.0f, alpha)
                bufferBuilder.addVertex(matrix4f, 0.5f, 0.5f, -0.5f)
                    .setUv(uAdjustedMin, vAdjustedMin)
                    .setColor(1.0f, 1.0f, 1.0f, alpha)
                bufferBuilder.addVertex(matrix4f, -0.5f, 0.5f, -0.5f)
                    .setUv(uAdjustedMax, vAdjustedMin)
                    .setColor(1.0f, 1.0f, 1.0f, alpha)

                BufferUploader.drawWithShader(bufferBuilder.buildOrThrow())
                poseStack.popPose()
            }

            RenderSystem.disableBlend()
            RenderSystem.depthMask(true)
            RenderSystem.depthFunc(GL11.GL_LEQUAL)
        }

        /**
         * # 每一件护甲减少 1/4 的伤害
         */
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

        /**
         * # 有胸甲时回血，全套抵消伤害
         */
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