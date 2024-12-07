package club.redux.sunset.lavafishing.misc

import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor

object ModBlockProperties {
    val PROMETHIUM: BlockBehaviour.Properties = BlockBehaviour.Properties.copy(Blocks.STONE)
        .mapColor(MapColor.COLOR_BLACK)
        .requiresCorrectToolForDrops()
        .strength(50.0F, 1200.0F)
        .sound(SoundType.NETHERITE_BLOCK)
}