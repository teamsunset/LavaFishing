package club.redux.sunset.lavafishing.behavior

import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.registry.ModItems
import net.minecraft.core.Position
import net.minecraft.core.dispenser.ProjectileDispenseBehavior
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.DispenserBlock
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent

class BehaviorDispenserBullet(item: Item) : ProjectileDispenseBehavior(item) {
    override fun getProjectile(pLevel: Level, pPosition: Position, pStack: ItemStack): Projectile {
        val item = (pStack.item as ItemBullet)
        return item.createBullet(pLevel).apply {
            setPos(pPosition.x(), pPosition.y(), pPosition.z())
            pickup = AbstractArrow.Pickup.ALLOWED
        }
    }

    companion object {
        @JvmStatic
        fun onSetup(event: FMLCommonSetupEvent) {
            ModItems.REGISTER.entries.map { it.get() }.filterIsInstance<ItemBullet>().forEach {
                DispenserBlock.registerBehavior(it, BehaviorDispenserBullet(it))
            }
        }
    }
}