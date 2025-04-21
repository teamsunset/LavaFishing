package club.redux.sunset.lavafishing.util

import net.minecraft.world.level.Level

fun Level.isServerSide(): Boolean = !this.isClientSide