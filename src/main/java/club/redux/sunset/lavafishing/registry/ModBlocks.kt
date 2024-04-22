package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.block.BlockPrometheusBounty
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import net.minecraftforge.registries.ForgeRegistries

object ModBlocks {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.BLOCKS, BuildConstants.MOD_ID)

    @JvmField val PROMETHEUS_BOUNTY = REGISTER.registerKt("prometheus_bounty") { BlockPrometheusBounty() }
}
