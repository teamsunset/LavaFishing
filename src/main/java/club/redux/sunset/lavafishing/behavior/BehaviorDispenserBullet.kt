package club.redux.sunset.lavafishing.behavior

import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.core.Position
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.DispenserBlock
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

class BehaviorDispenserBullet : AbstractProjectileDispenseBehavior() {
    override fun getProjectile(pLevel: Level, pPosition: Position, pStack: ItemStack): Projectile {
        val item = (pStack.item as ItemBullet)
        return item.entityTypeProvider().spawn(
            pLevel as ServerLevel, BlockPos(
                pPosition.x().toInt(), pPosition.y().toInt(),
                pPosition.z().toInt()
            ), MobSpawnType.DISPENSER
        )!!
    }

    companion object {
        @JvmStatic
        fun onSetup(event: FMLCommonSetupEvent) {
            ModItems.REGISTER.entries.map { it.get() }.filterIsInstance<ItemBullet>().forEach {
                DispenserBlock.registerBehavior(it, BehaviorDispenserBullet())
            }
        }
    }
}