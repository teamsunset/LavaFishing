package club.redux.sunset.lavafishing.item

import club.redux.sunset.lavafishing.BuildConstants
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
import net.minecraft.tags.FluidTags
import net.minecraft.util.Mth
import net.minecraft.world.damagesource.DamageTypes
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
import net.minecraft.world.level.material.FogType
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent
import net.minecraftforge.client.event.ViewportEvent
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14
import org.lwjgl.opengl.GL20

class ItemPromethiumArmor(
    armorMaterial: ArmorMaterial,
    type: Type,
) : ArmorItem(armorMaterial, type, Properties().fireResistant()) {

    private var texture: String? = null

    fun setArmorTexture(filename: String?): Item {
        this.texture = filename
        return this
    }

    // 这玩意居然是实时的
    override fun getArmorTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlot, type: String?): String {
        return BuildConstants.MOD_ID + ":textures/armor/" + this.texture + ".png"
    }

    override fun getXpRepairRatio(stack: ItemStack?): Float {
        return super.getXpRepairRatio(stack)
    }

    companion object {
        fun onLivingTick(event: LivingTickEvent) {
            val level = event.entity.level()
            for (itemStack in event.entity.armorSlots) {
                val item = itemStack.item
                if (item is ItemPromethiumArmor) {
                    val applyEffect = { effect: MobEffect ->
                        event.entity.addEffect(MobEffectInstance(effect, 20, 0, false, false, true))
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
                            applyEffect(ModMobEffects.LAVA_WALKER.get())
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

        fun onRenderBlockScreen(event: RenderBlockScreenEffectEvent) {
            if (event.overlayType != RenderBlockScreenEffectEvent.OverlayType.FIRE) return
            if (!event.player.getItemBySlot(EquipmentSlot.HEAD).`is`(ModItems.PROMETHIUM_HELMET.get())) return

            event.isCanceled = true
            if (event.player.isEyeInFluid(FluidTags.LAVA)) return

            val poseStack = event.poseStack
            val sprite = ModelBakery.FIRE_1.sprite()
            val centerU = (sprite.u0 + sprite.u1) / 2f
            val centerV = (sprite.v0 + sprite.v1) / 2f
            val shrink = sprite.uvShrinkRatio()
            val minU = Mth.lerp(shrink, sprite.u0, centerU)
            val maxU = Mth.lerp(shrink, sprite.u1, centerU)
            val minV = Mth.lerp(shrink, sprite.v0, centerV)
            val maxV = Mth.lerp(shrink, sprite.v1, centerV)
            val builder = Tesselator.getInstance().builder
            val shader = RenderSystem.getShader()
            val texture = RenderSystem.getShaderTexture(0)
            val color = RenderSystem.getShaderColor().copyOf()
            val depthFunc = GL11.glGetInteger(GL11.GL_DEPTH_FUNC)
            val depthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK)
            val blend = GL11.glIsEnabled(GL11.GL_BLEND)
            val blendSrcRgb = GL11.glGetInteger(GL14.GL_BLEND_SRC_RGB)
            val blendDstRgb = GL11.glGetInteger(GL14.GL_BLEND_DST_RGB)
            val blendSrcAlpha = GL11.glGetInteger(GL14.GL_BLEND_SRC_ALPHA)
            val blendDstAlpha = GL11.glGetInteger(GL14.GL_BLEND_DST_ALPHA)
            val blendEquationRgb = GL11.glGetInteger(GL20.GL_BLEND_EQUATION_RGB)
            val blendEquationAlpha = GL11.glGetInteger(GL20.GL_BLEND_EQUATION_ALPHA)
            try {
                RenderSystem.setShader(GameRenderer::getPositionColorTexShader)
                RenderSystem.setShaderTexture(0, sprite.atlasLocation())
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
                RenderSystem.depthFunc(GL11.GL_ALWAYS)
                RenderSystem.depthMask(false)
                RenderSystem.enableBlend()
                RenderSystem.defaultBlendFunc()
                repeat(2) { index ->
                    poseStack.pushPose()
                    try {
                        poseStack.translate(-(index * 2 - 1) * 0.24f, -0.3f, 0f)
                        poseStack.mulPose(Axis.YP.rotationDegrees((index * 2 - 1) * 10f))
                        val pose = poseStack.last().pose()
                        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX)
                        builder.vertex(pose, -0.5f, -0.5f, -0.5f).color(1f, 1f, 1f, 0.3f).uv(maxU, maxV).endVertex()
                        builder.vertex(pose, 0.5f, -0.5f, -0.5f).color(1f, 1f, 1f, 0.3f).uv(minU, maxV).endVertex()
                        builder.vertex(pose, 0.5f, 0.5f, -0.5f).color(1f, 1f, 1f, 0.3f).uv(minU, minV).endVertex()
                        builder.vertex(pose, -0.5f, 0.5f, -0.5f).color(1f, 1f, 1f, 0.3f).uv(maxU, minV).endVertex()
                        BufferUploader.drawWithShader(builder.end())
                    } finally {
                        poseStack.popPose()
                    }
                }
            } finally {
                RenderSystem.depthFunc(depthFunc)
                RenderSystem.depthMask(depthMask)
                RenderSystem.blendFuncSeparate(blendSrcRgb, blendDstRgb, blendSrcAlpha, blendDstAlpha)
                GL20.glBlendEquationSeparate(blendEquationRgb, blendEquationAlpha)
                if (blend) RenderSystem.enableBlend() else RenderSystem.disableBlend()
                RenderSystem.setShaderColor(color[0], color[1], color[2], color[3])
                RenderSystem.setShaderTexture(0, texture)
                RenderSystem.setShader { shader }
            }
        }

        fun onEntityDamage(event: LivingDamageEvent) {
            val damage = event.amount
            if (
                event.source.`is`(DamageTypes.LAVA) ||
                event.source.`is`(DamageTypes.IN_FIRE) ||
                event.source.`is`(DamageTypes.ON_FIRE)
            ) {
                var promethiumArmorCount = 0
                for (itemStack in event.entity.armorSlots) {
                    val item = itemStack.item
                    if (item is ItemPromethiumArmor) {
                        event.amount -= 1f / 4 * damage
                        promethiumArmorCount++
                    }
                }
            }
        }

        fun onEntityAttack(event: LivingAttackEvent) {
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
