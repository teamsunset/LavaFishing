package club.redux.sunset.lavafishing.client.renderlayer

import com.mojang.blaze3d.vertex.PoseStack
import com.teammetallurgy.aquaculture.client.ClientHandler
import com.teammetallurgy.aquaculture.client.renderer.entity.model.JellyfishModel
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.EntityModelSet
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.world.entity.LivingEntity
import java.util.function.Consumer

class RenderLayerJellyfish<T : LivingEntity>(
    renderer: RenderLayerParent<T, EntityModel<T>>,
    modelSet: EntityModelSet,
) : RenderLayer<T, EntityModel<T>>(renderer) {
    private val jellyfishModel = JellyfishModel<T>(modelSet.bakeLayer(ClientHandler.JELLYFISH_MODEL))

    override fun render(
        matrixStack: PoseStack,
        buffer: MultiBufferSource,
        i: Int,
        jellyfish: T,
        p_225628_5_: Float,
        p_225628_6_: Float,
        p_225628_7_: Float,
        p_225628_8_: Float,
        p_225628_9_: Float,
        p_225628_10_: Float,
    ) {
        if (!jellyfish.isInvisible) {
            this.parentModel.copyPropertiesTo(this.jellyfishModel)
            jellyfishModel.prepareMobModel(jellyfish, p_225628_5_, p_225628_6_, p_225628_7_)
            jellyfishModel.setupAnim(jellyfish, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_)
            val lvt_11_1_ = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(jellyfish)))
            jellyfishModel.parts().forEach(Consumer { p_228272_8_: ModelPart ->
                p_228272_8_.render(
                    matrixStack,
                    lvt_11_1_,
                    i,
                    LivingEntityRenderer.getOverlayCoords(jellyfish, 0.0f),
                    1.0f,
                    1.0f,
                    1.0f,
                    1.0f
                )
            })
        }
    }
}