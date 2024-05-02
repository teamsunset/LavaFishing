package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.block.BlockPrometheusBounty
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor
import net.minecraftforge.registries.ForgeRegistries

object ModBlocks {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.BLOCKS, BuildConstants.MOD_ID)

    @JvmField val PROMETHIUM_BLOCK = REGISTER.registerKt("promethium_block") {
        Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .requiresCorrectToolForDrops()
                .strength(50.0F, 1200.0F)
                .sound(SoundType.NETHERITE_BLOCK)
        )
    }

    @JvmField val PROMETHEUS_BOUNTY = REGISTER.registerKt("prometheus_bounty") { BlockPrometheusBounty() }
}
