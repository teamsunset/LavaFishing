package club.redux.sunset.lavafishing.item.block

import club.redux.sunset.lavafishing.client.renderer.BlockItemCustomRenderer
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import java.util.function.Consumer

class BlockItemWithoutLevelRenderer(
    pBlock: Block,
    pProperties: Properties,
) : BlockItem(pBlock, pProperties) {
    override fun initializeClient(consumer: Consumer<IClientItemExtensions>) {
        consumer.accept(object : IClientItemExtensions {
            override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
                return BlockItemCustomRenderer(
                    Minecraft.getInstance().blockEntityRenderDispatcher,
                    Minecraft.getInstance().entityModels
                )
            }
        })
    }
}